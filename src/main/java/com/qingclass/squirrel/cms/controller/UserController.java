package com.qingclass.squirrel.cms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.github.pagehelper.util.StringUtil;
import com.qingclass.squirrel.cms.utils.PoiHelper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.qingclass.squirrel.cms.Service.UserService;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLesson;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import com.qingclass.squirrel.cms.entity.user.MongoUser;
import com.qingclass.squirrel.cms.entity.user.SquirrelUser;
import com.qingclass.squirrel.cms.entity.user.User;
import com.qingclass.squirrel.cms.entity.user.UserLevel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import com.qingclass.squirrel.cms.mapper.user.PurchaseMapper;
import com.qingclass.squirrel.cms.mapper.user.SquirrelUserMapper;
import com.qingclass.squirrel.cms.mapper.user.UserMapper;
import com.qingclass.squirrel.cms.utils.Tools;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	PurchaseMapper purchaseMapper;
	@Autowired
	SquirrelLevelMapper squirrelLevelMapper;
	@Autowired
	SquirrelLessonMapper squirrelLessonMapper;
	@Autowired
	SquirrelUserMapper squirrelUserMapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@RequestMapping(value = "/user-record-list", method = RequestMethod.POST)
	public Map<String,Object> userRecordList(
			@RequestParam(value = "pageNo",required = false)int pageNo,
			@RequestParam(value = "pageSize",required = false)int pageSize,
			@RequestParam(value = "nickName",required = false)String nickName,
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime){

		User user = new User();
		user.setPageNo(pageNo);
		user.setPageSize(pageSize);
		user.setNickName(nickName);
		if(levelId!=null){
			user.setLevelId(levelId);
			logger.info("levelId1:{}",user.getLevelId());
		}else{
			user.setLevelId(0);
			logger.info("levelId2:{}",user.getLevelId());
		}
		if(startTime != null && startTime.length() > 0 && endTime != null && endTime.length() > 0){
			user.setVipBeginTime(startTime);
			user.setVipEndTime(endTime);
		}
		List<User> users = userService.UserRecordList(user,null);

		HashMap<String, Object> map = new HashMap<>();
		map.put("list", users);
		map.put("pageTotal", userMapper.count(user));

		return Tools.s(map);
	}

	//用户学习记录导出excel
	@ResponseBody
	@RequestMapping(value = "/study-record-excel", method = RequestMethod.GET)
	public Map<String,Object> studyRecordExcel(
			HttpServletResponse response,
			@RequestParam(value = "nickName",required = false)String nickName, 
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime){
		//查询数据
		User user = new User();
		List<SquirrelLevel> levelList = new ArrayList<SquirrelLevel>();
		if(levelId!=null){
			logger.info("levelId1:{}",user.getLevelId());
			levelList = squirrelLevelMapper.selectById(levelId);
		}else{
			logger.info("levelId2:{}",user.getLevelId());
			levelList = squirrelLevelMapper.selectAll();
		}
		if(nickName != null && !nickName.equals("")){
			user.setNickName(nickName);
		}
		if(startTime != null && startTime.length() > 0 && endTime != null && endTime.length() > 0){
			user.setVipBeginTime(startTime);
			user.setVipEndTime(endTime);
		}
		//表头
		Map<String, String> headNameMap = new LinkedHashMap<>();
		//表格数据
		List<Map<String, Object>> list = new ArrayList<>();
		
		for(int j=0; j<levelList.size(); j++){
			
			SquirrelLevel squirrelLevel = levelList.get(j);
			if(squirrelLevel!=null){
				user.setLevelId(squirrelLevel.getId());
				Integer minOrderInteger = squirrelLessonMapper.selectMinOrder(squirrelLevel.getId());
				if(minOrderInteger!=null){
					int minOrder = squirrelLessonMapper.selectMinOrder(squirrelLevel.getId()).intValue();
					int maxOrder = squirrelLessonMapper.selectMaxOrder(squirrelLevel.getId()).intValue();
					
					List<User> users = userService.studyRecordList(user, "excel", minOrder, maxOrder);
					
					//表头
					headNameMap.put("levelName", "频道");
					headNameMap.put("nickName", "微信昵称");
					headNameMap.put("openId", "openId");
					headNameMap.put("beginDate", "开课日期");
					for(int i=minOrder; i<=maxOrder; i++){
						headNameMap.put("studyRecordMap"+i, "第"+i+"天");
					}
					//表格数据
					if (users != null && users.size() > 0) {
						for (User u : users) {
							Map<String, Object> map = new HashMap<>();
							map.put("levelName", squirrelLevel.getName());
							map.put("nickName", u.getNickName());
							map.put("openId", u.getOpenId());
							map.put("beginDate", u.getBeginAt());
							for(int i=minOrder; i<=maxOrder; i++){
								if(u.getStudyRecordMap()!=null){
									map.put("studyRecordMap"+i, (String)u.getStudyRecordMap().get(i+""));
								}else{
									map.put("studyRecordMap"+i, "未学习");
								}
							}
							list.add(map);
						}
					}
				}
			}
		}
		PoiHelper.exportXlsx(response,"学习记录导出表", headNameMap, list);
		return Tools.s();
	}

	//用户学习记录导出excel
	@ResponseBody
	@RequestMapping(value = "/user-record-excel", method = RequestMethod.GET)
	public Map<String,Object> userRecordExcel(
			HttpServletResponse response,
			@RequestParam(value = "nickName",required = false)String nickName, 
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime){
		//查询数据
		User user = new User();

		if(levelId!=null){
			user.setLevelId(levelId);
			logger.info("levelId1:{}",user.getLevelId());
		}else{
			user.setLevelId(0);
			logger.info("levelId2:{}",user.getLevelId());
		}
		if(nickName != null && !nickName.equals("")){
			user.setNickName(nickName);
		}
		if(startTime != null && startTime.length() > 0 && endTime != null && endTime.length() > 0){
			user.setVipBeginTime(startTime);
			user.setVipEndTime(endTime);
		}
		List<User> users = userService.UserRecordList(user,"excel");
		//表头
		Map<String, String> headNameMap = new LinkedHashMap<>();
		headNameMap.put("nickName", "微信昵称");
		headNameMap.put("beginDate", "开课日期");
		headNameMap.put("openId", "openId");
		headNameMap.put("alreadyStudyDays", "已学习天数");
		headNameMap.put("alreadyFinishDays", "已学完天数");
		headNameMap.put("alreadyCardDays", "已补学天数");
		headNameMap.put("alreadySendDays", "已推送天数");
		headNameMap.put("alreadyShareDays", "已打卡天数");

		//表格数据
		List<Map<String, Object>> list = new ArrayList<>();
		if (users != null && users.size() > 0) {
			for (User u : users) {
				Map<String, Object> map = new HashMap<>();
				map.put("nickName", u.getNickName());
				map.put("beginDate", u.getBeginAt());
				map.put("openId", u.getOpenId());
				map.put("alreadyStudyDays", u.getAlreadyStudyDays());
				map.put("alreadyFinishDays", u.getAlreadyFinishDays());
				map.put("alreadyCardDays", u.getAlreadyCardDays());
				map.put("alreadySendDays", u.getAlreadySendDays());
				map.put("alreadyShareDays", u.getAlreadyShareDays());

				list.add(map);
			}
		}

		PoiHelper.exportXlsx(response,"记录表", headNameMap, list);
		return Tools.s();
	}	

	@ResponseBody
	@RequestMapping(value = "/user-list", method = RequestMethod.POST)
	public Map<String,Object> userList(
			@RequestParam(value = "pageNo",required = false)Integer pageNo,
			@RequestParam(value = "pageSize",required = false)Integer pageSize,
			@RequestParam(value = "nickName",required = false)String nickName,
			@RequestParam(value = "subscribe",required = false)Integer subscribe,
			@RequestParam(value = "isVip",required = false)Integer isVip,
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "beginAt",required = false)String createdAt,
			@RequestParam(value = "vipBeginTime",required = false)String vipBeginTime,
			@RequestParam(value = "vipEndTime",required = false)String vipEndTime){

		User user = new User();
		user.setPageNo(pageNo);
		user.setPageSize(pageSize);
		if(nickName != null && !nickName.equals("")){
			user.setNickName(nickName);
		}
		if(subscribe != null){
			user.setSubscribe(subscribe);
		}
		if(isVip != null){
			user.setIsVip(isVip);
		}


		if(levelId!=null){
			user.setLevelId(levelId);
			logger.info("levelId1:{}",user.getLevelId());
		}else{
			user.setLevelId(0);
			logger.info("levelId2:{}",user.getLevelId());
		}
		if(createdAt != null && !createdAt.equals("")){
			String[] split = createdAt.split(",");
			user.setCreatedAt(createdAt);
			user.setCreatedAtA(split[0] + " 00:00:00");
			user.setCreatedAtB(split[1] + " 23:59:59");
		}
		if(vipBeginTime != null && !vipBeginTime.equals("")){
			String[] split = vipBeginTime.split(",");
			user.setVipBeginTime(vipBeginTime);
			user.setVipBeginTimeA(split[0] + " 00:00:00");
			user.setVipBeginTimeB(split[1] + " 23:59:59");
		}
		if(vipEndTime != null && !vipEndTime.equals("")){
			String[] split = vipEndTime.split(",");
			user.setVipEndTime(vipEndTime);
			user.setVipEndTimeA(split[0] + " 00:00:00");
			user.setVipEndTimeB(split[1] + " 23:59:59");
		}

		List<User> users = userService.UserList(user);
		HashMap<String, Object> map = new HashMap<>();
		map.put("list",users);
		map.put("pageTotal",userMapper.userListCount(user));
		return Tools.s(map);

	}

	@ResponseBody
	@PostMapping(value = "/purchase-record")
	public Map<String, Object> purchaseRecord(
			@RequestParam(value = "findParam",required = false)String findParam,
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "pageNo",required = false)Integer pageNo,
			@RequestParam(value = "pageSize",required = false)Integer pageSize){
		logger.info("levelId:{}, findParam:{}, pageNo:{}, pageSize:{}", levelId, findParam, pageNo, pageSize);
		pageNo = (pageNo - 1) * pageSize;
		UserLevel userLevel = new UserLevel();
		userLevel.setPageNo(pageNo);
		userLevel.setPageSize(pageSize);
		userLevel.setFindParam(findParam);
		if(levelId!=null){
			userLevel.setLevelId(levelId);
			logger.info("levelId1:{}",userLevel.getLevelId());
		}else{
			userLevel.setLevelId(0);
			logger.info("levelId2:{}",userLevel.getLevelId());
		}
		List<UserLevel> userLevels = purchaseMapper.selectAllPurchaseRecord(userLevel);

		Map<String, Object> map = new HashMap<>();
		map.put("list", userLevels);
		map.put("pageTotal", purchaseMapper.selectAllPurchaseRecordCount(userLevel));

		return Tools.s(map);
	}

	/**
	 * 
	 * @param date
	 * @param userId
	 * @param flag    1.设置打卡 2.设置学习完 3.设置补学
	 * @param levelId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/set-study")
	public Map<String, Object> setStudy(@RequestParam(value = "date", required = true) String date,
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "flag", required = true) Integer flag,
			@RequestParam(value = "levelId", required = true) Integer levelId) throws Exception {
		// 根据date 获取对应的lesson-id
		SquirrelLesson squirrelLesson = userService.getLessonIdByDate(date, userId, levelId);
		logger.info("lessonId is :" + squirrelLesson.getId());
		// 更新lesson信息
		SquirrelLevel squirrelLevel = squirrelLevelMapper.selectByPrimaryKey(levelId);
		String subjectId = squirrelLevel.getSubjectId() + "";
		StringBuffer key = new StringBuffer();
		key.append("learnHistory.");
		key.append("subjects.subject-").append(subjectId).append(".");
		key.append("levels.level-").append(levelId).append(".");
		key.append("lessons.lesson-").append(squirrelLesson.getId());
		Query query = new Query(Criteria.where("userId").is(userId));
		query.fields().include(key.toString() + ".record");
		MongoUser mongoUser = mongoTemplate.findOne(query, MongoUser.class);
		
		if(mongoUser!=null){
			
		}
		
		Map<String, Object> map = (Map<String, Object>) mongoUser.getLearnHistory().get("subjects");
		Map<String, Object> map1 = (Map<String, Object>) ((Map<String, Object>) map.get("subject-" + subjectId))
				.get("levels");
		Map<String, Object> map2 = (Map<String, Object>) ((Map<String, Object>) map1.get("level-" + levelId))
				.get("lessons");
		Map<String, Object> recordMap = new HashMap<>();
		if (map2 != null) {
			Map<String, Object> map3 = (Map<String, Object>) ((Map<String, Object>) map2
					.get("lesson-" + squirrelLesson.getId()));
			if (map3 != null) {
				recordMap = (Map<String, Object>) map3.get("record");
			}
		}

		Map<String, Object> history = new HashMap<>();
		if (flag == 1) {
			history.put("isShare", true);
		} else if (flag == 2) {
			history.put("isFinish", true);
		}else if (flag == 3){
			history.put("isCard", true);
		}
		history.put("order", squirrelLesson.getOrder());
		if (recordMap == null)
			recordMap = new HashMap<>();
		recordMap.putAll(history);
		Update update = new Update().set(key.toString() + ".record", recordMap);
		try {
			mongoTemplate.upsert(query, update, MongoUser.class);
		} catch (DuplicateKeyException e) {
			logger.error("mongo用户主键冲突，userId=" + userId);
		}
		return Tools.s();
	}

	@PostMapping(value = "/user-detail")
	public Map<String, Object> detail(
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "levelId", required = true) Integer levelId,
			@RequestParam(value = "pageNo", required = true) Integer pageNo,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "endTime", required = false) String endTime) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> user = userMapper.selectById(userId);
		Query query = new Query(Criteria.where("userId").is(userId));
		// .andOperator(Criteria.where("_id").is(squirrelUser.getId())));
		logger.info("user-detail start====mongoTemplate====="+(mongoTemplate==null));
		MongoUser mongoUser = mongoTemplate.findOne(query, MongoUser.class);// userList的对应学习记录
		Map<String, Integer> recordMap = userService.lessonRecord(mongoUser, levelId);
		user.putAll(recordMap);
		map.put("user", user);
		Map<String, Object> returnMap = userService.getDetailStatistics(levelId, mongoUser, startTime, endTime, pageNo,
				pageSize);
		map.putAll(returnMap);
		
//		List<Map<String,Object>> lessonList = getStudyRecord(levelId, userId);
//		map.put("lessonList",lessonList);
		return Tools.s(map);
	}


	public List<Map<String,Object>> getStudyRecord(
			Integer levelId, Integer userId){
		SquirrelLevel squirrelLevel = squirrelLevelMapper.selectByPrimaryKey(levelId);
		String subjectId = squirrelLevel.getSubjectId() + "";
		Query query = new Query(Criteria.where("userId").is(userId));
		query.fields().include("learnHistory.subjects.subject-"+subjectId+".levels.level-"+levelId+".lessons");
		MongoUser mongoUser = mongoTemplate.findOne(query, MongoUser.class);

		Map<String, Object> returnMap = new HashMap<>();
		
		Map<String, Object> subjectsMap = new HashMap<>();
		Map<String, Object> levelMap = new HashMap<>();
		Map<String, Object> lessonMap = new HashMap<>();
		try{
			subjectsMap = (Map<String, Object>) mongoUser.getLearnHistory().get("subjects");
			levelMap = (Map<String, Object>) ((Map<String, Object>)subjectsMap.get("subject-"+subjectId)).get("levels");
			lessonMap = (Map<String, Object>) ((Map<String, Object>)levelMap.get("level-"+levelId)).get("lessons");
		}catch(NullPointerException e){
			logger.warn("history is null");
		}
		

		List<Map<String,Object>> lessonList= new ArrayList<>();
		List<Integer> lessonIdsList = new ArrayList<>();
		//
		SimpleDateFormat sdfHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map.Entry<String, Object> entry : lessonMap.entrySet()) {
			Map<String, Object> temp = (Map<String, Object>) entry.getValue();
			if(temp.get("record")!=null){
				temp = (Map<String, Object>)temp.get("record");
				Map<String, Object> lmap = new HashMap<>();

				lmap.put("lessonId",entry.getKey().substring(entry.getKey().lastIndexOf("-")+1));
				lmap.put("status",(boolean)temp.get("isFinish"));
				lmap.put("optTime",temp.get("optTime"));
				lessonList.add(lmap);
				lessonIdsList.add(Integer.parseInt(entry.getKey().substring(entry.getKey().lastIndexOf("-")+1)));
			}
		}
		//字符串转时间戳
		SquirrelUser squirrelUser = squirrelUserMapper.selectBeginAtByOpenId(mongoUser.getId(), levelId).get(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		//这部分代码是确保返回lessonIds的数量和推课数量一致
		int days = lessonList.size();
		try {
			date = sdf.parse(squirrelUser.getBeginAt());
			Date dd = new Date();
			if(date.before(dd)){
				int dayss = (int)((dd.getTime() - date.getTime())/(1000 * 60 * 60 * 24));
				if(dayss < days){
					days = dayss;
					days += 1;
				}
			}
		} catch (ParseException e) {
			logger.error("parse error.");
			e.printStackTrace();
		}

		//多余lesson删除
		List<Map<String,Object>> lessonIdss= new ArrayList<>();

		for(int i = 0 ; i < days ; i ++){
			lessonIdss.add(lessonList.get(i));
		}

		lessonList = lessonIdss;

		return lessonList;
	}
	
	@ResponseBody
	@PostMapping(value = "/create-purchase-record")
	public Map<String, Object> createPurchaseRecord(@RequestParam(value = "openIds", required = false) String openIds,
			@RequestParam(value = "vipDays", required = false) Integer vipDays,
			@RequestParam(value = "sendLessonCount") Integer sendLessonCount,
			@RequestParam(value = "beginAt", required = false) String beginAt,
			@RequestParam(value = "levelId", required = false) Integer levelId) {
				openIds = openIds + ",";
		try {
			userService.insertPurchaseRecord(openIds, vipDays, sendLessonCount, beginAt, levelId);
		} catch (ParseException e) {
			logger.error("创建购买记录失败...");
			e.printStackTrace();
		}



		return Tools.s();
	}

	@ResponseBody
	@PostMapping(value = "/purchase-record-info")
	public Map<String, Object> purchaseRecordInfo(@RequestParam(value = "openId",required = false)String openId,@RequestParam(value = "levelId",required = false)Integer levelId){

		UserLevel userLevel = purchaseMapper.selectPurchaseRecordByOpenId(openId,levelId);
		if (userLevel == null){
			return Tools.f();
		}
		//算一下已经推了多少天
		String beginAt = userLevel.getBeginAt();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		Date now = new Date();
		try {
			parse = sdf.parse(beginAt);
		} catch (ParseException e) {
			logger.error("date parse error.");
		}
		if(parse == null){
			return Tools.f("beginAt can't be null ");
		}
		int days = (int)((now.getTime() - parse.getTime())/(1000 * 60 * 60 * 24));

		if(days <= 0){
			userLevel.setAlreadySendLessonDays(days-1);
		}else{
			userLevel.setAlreadySendLessonDays(days+1);
		}

		return Tools.s(userLevel);
	}

	@ResponseBody
	@PostMapping(value = "/edit-purchase-record")
	public Map<String, Object> editPurchaseRecord(
			@RequestParam(value = "userId",required = false)Integer userId,
			@RequestParam(value = "expireDate",required = false)String expireDate,
			@RequestParam(value = "beginAt",required = false)String beginAt,
			@RequestParam(value = "sendLessonDays",required = false)Integer sendLessonDays,
			@RequestParam(value = "vipEndTime",required = false)String vipEndTime,
			@RequestParam(value = "levelId",required = false)Integer levelId){

		try {
			userService.editPurchaseRecord(userId, expireDate, beginAt, sendLessonDays, vipEndTime, levelId);
		} catch (ParseException e) {
			logger.error("date parse error.");
		}

		return Tools.s();
	}

	@ResponseBody
	@PostMapping(value = "/edit-purchase-record-send-lesson-days")
	public Map<String, Object> editPurchaseRecordSendLessonDays(
			@RequestParam(value = "userId",required = false)Integer userId,
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "sendLessonDays",required = false)Integer sendLessonDays){
		
		try {
			userService.editPurchaseRecord(userId, sendLessonDays, levelId);
		} catch (ParseException e) {
			logger.error("date parse error.");
		}

		return Tools.s();
	}

}
