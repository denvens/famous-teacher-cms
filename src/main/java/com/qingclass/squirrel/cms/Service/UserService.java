package com.qingclass.squirrel.cms.Service;


import com.alibaba.fastjson.JSON;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLessonRemind;
import com.qingclass.squirrel.cms.entity.study.StudyRecord;
import com.qingclass.squirrel.cms.entity.user.MongoUser;
import com.qingclass.squirrel.cms.entity.user.User;
import com.qingclass.squirrel.cms.entity.user.UserLevel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxTemplateMapper;
import com.qingclass.squirrel.cms.mapper.user.UserMapper;
import com.qingclass.squirrel.cms.mapper.user.UserRemindMapper;
import com.qingclass.squirrel.cms.utils.RecordUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qingclass.squirrel.cms.entity.cms.SquirrelLesson;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import com.qingclass.squirrel.cms.utils.DateFormatHelper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserService {
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
    UserMapper userMapper;
	@Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private SquirrelLessonMapper squirrelLessonMapper;
    @Autowired
    private SquirrelLevelMapper squirrelLevelMapper;
    @Autowired
	RecordUtil recordUtil;
    @Autowired
	private SquirrelWxTemplateMapper squirrelWxTemplateMapper;
    @Autowired
	UserRemindMapper userRemindMapper;


	public RequestInfo insertUserLevel(String content){//
        RequestInfo info;
        info = new RequestInfo();
        return info;
    }

    public List<User> UserRecordList(User user, String type){

		List<User> users = new ArrayList<>();
		if(StringUtils.isNotEmpty(type) && type.equals("excel")){
			users = userMapper.selectForExcel(user);
		}else{
			user.setPageNo((user.getPageNo()-1)*user.getPageSize());

			users = userMapper.selectByPage(user);//mysql的user表查询
		}


        List<Integer> userIds = new ArrayList<>();
        users.forEach(e -> userIds.add(e.getId()));

        Query query = new Query(Criteria.where("userId").in(userIds));
        List<MongoUser> mongoUsers = mongoTemplate.find(query, MongoUser.class);//userList的对应学习记录
        Map<String, Integer> recordMap =null;
        MongoUser mongoUser =null;
        for(User u : users){
            for(MongoUser mu : mongoUsers){//取到对应的学习记录
                if(mu.getUserId() == u.getId()){
                    mongoUser = mu;
                    break;
                }
            }

            recordMap = this.lessonRecord(mongoUser, user.getLevelId());
            mongoUser=null;
            try{
                u.setAlreadyStudyDays(recordMap.get("alreadyStudyDaysAll"));
                u.setAlreadyFinishDays(recordMap.get("alreadyFinishAll"));
                u.setAlreadyShareDays(recordMap.get("alreadyShareDaysAll"));
                u.setAlreadySendDays(recordMap.get("sendCourseDays"));
                u.setAlreadyCardDays(recordMap.get("alreadyCardDaysAll"));
            }catch(NullPointerException e){
            	log.warn("可忽略空指针");
            }
        }

        return users;
    }
    
    public List<User> studyRecordList(User user, String type,
    		int minOrder, int maxOrder){

		List<User> users = new ArrayList<>();
		if(StringUtils.isNotEmpty(type) && type.equals("excel")){
			users = userMapper.selectForExcel(user);
			log.info("users====>:{}",users.size());
		}else{
//			user.setPageNo((user.getPageNo()-1)*user.getPageSize());
//			users = userMapper.selectByPage(user);//mysql的user表查询
		}

        List<Integer> userIds = new ArrayList<>();
        users.forEach(e -> userIds.add(e.getId()));

        Query query = new Query(Criteria.where("userId").in(userIds));
        List<MongoUser> mongoUsers = mongoTemplate.find(query, MongoUser.class);//userList的对应学习记录
        Map<String, String> studyRecordMap =null;
        MongoUser mongoUser =null;
        for(User u : users){
            for(MongoUser mu : mongoUsers){//取到对应的学习记录
                if(mu.getUserId() == u.getId()){
                	log.info("userId:{}, id:{}, userId==id:{}",mu.getUserId(), u.getId(), mu.getUserId() == u.getId());
                    mongoUser = mu;
                    break;
                }
            }

            studyRecordMap = this.studyDayLessonRecord(mongoUser, user.getLevelId(), minOrder, maxOrder);
            u.setStudyRecordMap(studyRecordMap);
//            try{
//                u.setAlreadyStudyDays(recordMap.get("alreadyStudyDaysAll"));
//            }catch(NullPointerException e){
//            	log.warn("可忽略空指针");
//            }
            mongoUser=null;
        }

        return users;
    }

    public List<User> UserList(User user){
        user.setPageNo((user.getPageNo()-1)*user.getPageSize());

        List<User> users = userMapper.selectUserListByPage(user);//mysql的user表查询
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(new Date());
		for (User u : users){

			if(u.getLevelId() == null || u.getVipEndTime().compareTo(now)<0){
				u.setIsVip(0);
			}else{
				u.setIsVip(1);
			}
		}

        return users;
    }


    /**
     * 批量插入购买记录
     * */
    public int insertPurchaseRecord(String openIds, Integer vipDays, Integer sendLessonCount, String beginAt, Integer levelId) throws ParseException {
        String[] openIdArr = openIds.split(",");

        int result = 0;
        String vipBeginAt;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(beginAt.equals("即刻")){
            beginAt = sdf.format(new Date());
        }
        vipBeginAt = beginAt;
        Date parse = sdf.parse(vipBeginAt);
        Calendar cal = Calendar.getInstance();
        if(parse != null){
            cal.setTime(parse);
        }
        cal.add(Calendar.DATE, vipDays);
        String vipEndTime = sdf.format(cal.getTime());

		SquirrelLessonRemind aDefault = squirrelWxTemplateMapper.selectRemindTimeDefault("default");


		for(String openId : openIdArr){
            List<UserLevel> userLevels = userMapper.selectUserPurchaseRecordByOpenIdAndLevelId(openId, levelId);
            if(userLevels.size() > 0){
                UserLevel userLevel = userLevels.get(0);
                Date vet = sdf.parse(userLevel.getVipEndTime());
                cal.setTime(vet);
                cal.add(Calendar.DATE, vipDays);
                vipEndTime = sdf.format(cal.getTime());
                result = userMapper.updateUserLevel(null,null,vipEndTime,userLevel.getId(),sendLessonCount);
            }else{
                int id = userMapper.selectByOpenId(openId).getId();

                result = userMapper.insertUserLevels(id,levelId,0,beginAt,vipBeginAt,vipEndTime,sendLessonCount);

                //插入课程提醒
				userRemindMapper.insertRemindRecord(id,openId,levelId,aDefault.getFirstRemind(),aDefault.getRemindRate(),1);
				userRemindMapper.insertRemindRecord(id,openId,levelId,aDefault.getSecondRemind(),aDefault.getRemindRate(),1);
            }
        }

        return result;
    }
    

    /**
     * 学习记录详情
     * @param levelId 
     * @param mongoUser
     * @param startTime
     * @param endTime
     * @param pageSize 
     * @param pageNo
     * @return
     * @throws ParseException 
     */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDetailStatistics(Integer levelId, MongoUser mongoUser, String startTime, String endTime, Integer pageNo, Integer pageSize) {
		
		/*startTime大于beginAt startTime开始
		 * startTime小于beginAt beginAt开始
		 * endTime大于vipEndTime sendDays == null ? (vipEndTime>now ? now : vipEndTime)  : (beginAt+sendDays>now ? now : vipEndTime)
		 * endTime小于vipEndTime sendDays == null ? (endTime>now ? now : endTime)  : (beginAt+sendDays>now ? now : endTime)
		 */
		Map<String,Object> resultMap = new HashMap<>();
		if(pageNo==null)pageNo=1;
		if(pageSize==null)pageSize=999999999;
		List<Map<String, Object>>  list = new ArrayList<>();
		if(mongoUser==null) {
			resultMap.put("totalNum", 0);
			resultMap.put("list", list);
			return resultMap;
		}
		UserLevel userLevel = userMapper.selectUserLevel(mongoUser.getUserId(),levelId);
		String beginAt=userLevel.getBeginAt();
		String vipEndTime=userLevel.getVipEndTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateStr = dateFormat.format(new Date());
		Integer order = squirrelLessonMapper.selectMaxOrder(levelId);
		try {
			Date beginDate = dateFormat.parse(beginAt);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(beginDate);
			calendar.add(Calendar.DATE, order);
			String maxOrderTime = dateFormat.format(calendar.getTime());
			if(StringUtils.isEmpty(startTime)) { 
				startTime=beginAt;
			}else {
				if(startTime.compareTo(beginAt)<0) {
					startTime=getEndTime(vipEndTime, nowDateStr,maxOrderTime);
				}
			}
			if(StringUtils.isEmpty(endTime)) { 
				endTime = getEndTime(vipEndTime, nowDateStr,maxOrderTime);
			}else {
				if(endTime.compareTo(vipEndTime)>0) {
					endTime = vipEndTime;
				}
			}
			Date startDate = dateFormat.parse(startTime);
		    calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, -1);
			startDate=calendar.getTime();
			Date endDate  = dateFormat.parse(endTime);
			if(endTime.equals(maxOrderTime)) {
				calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.DATE, -1);
				endDate=calendar.getTime();
			}
			long totalNum=(endDate.getTime()-startDate.getTime())/(1000 * 60 * 60 * 24);
			Map<String,Map<String, Object>> map = getLearnMap(levelId,mongoUser,beginDate);
			Map<String,Object> returnMap =null;
			Map<String,Object> shareMap =map.get("shareMap");
			Map<String,Object> finishMap =map.get("finishMap");
			Map<String,Object> learnMap =map.get("learnMap");
			Map<String,Object> cardMap =map.get("cardMap");
			long start=endDate.getTime()-pageNo * pageSize * 1000 * 60 * 60 * 24l;
			long end=endDate.getTime()-(pageNo-1) * pageSize * 1000 * 60 * 60 * 24l;
			if(start<startDate.getTime())start=startDate.getTime();
			long show=(end-start)/(1000 * 60 * 60 * 24);
			if(show>pageSize) {
				start+=1000 * 60 * 60 * 24;
			}
			if(start==end) start-=1000 * 60 * 60 * 24;
			log.info("isFinish----xxxx>:{}",map);
			while(start<end) {
				StudyRecord studyRecord = (StudyRecord)finishMap.get(end+"");
				log.info("start:{}, end:{}",start,end);
				log.info("studyRecord:{}",studyRecord,end);
				returnMap =new HashMap<>();
				returnMap.put("sendDate", DateFormatHelper.stampToDate(end));
//				returnMap.put("study", "未学习");
//				returnMap.put("share", "未打卡");
				if(learnMap.get(end+"")!=null) {
					returnMap.put("study", "已学习");
				}
				log.info("isFinish----xxxx>:{}",finishMap.get("isFinish"));
				if(studyRecord!=null) {
					if(studyRecord.getIsFinish()==1) {
						returnMap.put("study", "已学完");
					}else if(studyRecord.getIsFinish()==2){
							returnMap.put("study", "已补学");
					}else{
						returnMap.put("study", "未学习");
					}
					returnMap.put("firstUseTime", studyRecord.getFirstUseTime());
				}else{
					returnMap.put("study", "未学习");
					returnMap.put("firstUseTime", 0);
				}
				
				
//				if(finishMap.get(end+"")!=null) {
//					returnMap.put("study", "已学完");
//				}
//				if(shareMap.get(end+"")!=null) {
//					returnMap.put("share", "已打卡");
//				}
//				if(cardMap.get(end+"")!=null) {
//					returnMap.put("card", "已补学");
//				}
				list.add(returnMap);
				end-=1000 * 60 * 60 * 24;
			}
			if(totalNum<0)totalNum=0;
			resultMap.put("totalNum", totalNum);
			resultMap.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}

	/**
	 * 
	 * @param vipEndTime
	 * @param nowDateStr
	 * @param date= beginAt+order
	 * @return
	 */
	private String getEndTime(String vipEndTime, String nowDateStr, String date) {
		String endTime=findMin(vipEndTime, nowDateStr, date);
		return endTime;
	}
	

	@SuppressWarnings("unchecked")
	private Map<String, Map<String, Object>> getLearnMap(Integer levelId, MongoUser mongoUser, Date beginDate) throws Exception {
		Map<String, Map<String, Object>>  returnMap = new HashMap<>();
		SquirrelLevel squirrelLevel = squirrelLevelMapper.selectByPrimaryKey(levelId);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String subjectId = squirrelLevel.getSubjectId()+"";
		Query query = new Query(Criteria.where("userId").is(mongoUser.getUserId()));
		query.fields().include("learnHistory.subjects.subject-"+subjectId+".levels.level-"+levelId+".lessons");
	    mongoUser = mongoTemplate.findOne(query, MongoUser.class);
	    Map<String, Object> shareMap=new HashMap<>();
		Map<String, Object> cardMap = new HashMap<>();
		Map<String, Object> finishMap=new HashMap<>();
		Map<String, Object> learnMap=new HashMap<>();
		try {
			Map<String, Object> subjectMap = (Map<String, Object>) mongoUser.getLearnHistory().get("subjects");
			Map<String, Object> levelMap = (Map<String, Object>) ((Map<String, Object>)subjectMap.get("subject-"+subjectId)).get("levels");
			Map<String, Object>  lessonMap = (Map<String, Object>) ((Map<String, Object>)levelMap.get("level-"+levelId)).get("lessons");
			log.info("lessonMap:{}",lessonMap);
			Calendar calendar = null;
			for (Map.Entry<String, Object> entry : lessonMap.entrySet()) {
				Map<String, Object> temp = (Map<String, Object>) entry.getValue();
				StudyRecord studyRecord=new StudyRecord();
				if(temp!=null && temp.get("record")!=null){
					temp = (Map<String, Object>)temp.get("record");
					if(temp!=null && temp.get("order")!=null){
						calendar = Calendar.getInstance();
						calendar.setTime(beginDate);
						int order = Integer.parseInt(temp.get("order").toString());
						calendar.add(Calendar.DATE, (order-1));
						log.info("isFinish--111-->:{}",temp.get("isFinish"));
//						finishMap.put("isFinish",temp.get("isFinish"));
						if(temp.get("startedAt")!=null) {
							learnMap.put(calendar.getTimeInMillis()+"", dateFormat.format(calendar.getTime()));
						}
						
						if(temp.get("isFinish")!=null) {
							if((boolean)temp.get("isFinish")==true) {
//								finishMap.put(calendar.getTimeInMillis()+"", 1);
								studyRecord.setIsFinish(1);
							}
							if((boolean)temp.get("isFinish")==false) {
//								finishMap.put(calendar.getTimeInMillis()+"", 2);
								studyRecord.setIsFinish(2);
							}
						}else{
//							finishMap.put(calendar.getTimeInMillis()+"", 0);
							studyRecord.setIsFinish(0);
						}
						
//						finishMap.put("firstUseTime",temp.get("firstUseTime"));
						studyRecord.setTimeInMillis(calendar.getTimeInMillis());
						if(temp.get("firstUseTime")!=null){
							studyRecord.setFirstUseTime(Integer.valueOf((String)temp.get("firstUseTime")));
						}else{
							studyRecord.setFirstUseTime(0);
						}
//						if(temp.get("isFinish")!=null) {
//							finishMap.put(calendar.getTimeInMillis()+"", temp.get("isFinish"));
//						}
//						if(temp.get("isShare")!=null) {
//							shareMap.put(calendar.getTimeInMillis()+"", dateFormat.format(calendar.getTime()));
//						}
						finishMap.put(calendar.getTimeInMillis()+"", studyRecord);
					}
//					if(temp.get("isCard")!=null) {
//						cardMap.put(calendar.getTimeInMillis()+"", dateFormat.format(calendar.getTime()));
//					}
				}
			}
		} catch (Exception e) {
			
		}
		returnMap.put("learnMap", learnMap);
//		returnMap.put("shareMap", shareMap);
		returnMap.put("finishMap", finishMap);
//		returnMap.put("cardMap", cardMap);
		log.info("returnMap:{}",returnMap);
		return returnMap;
	}

	public SquirrelLesson getLessonIdByDate(String date, Integer userId, Integer levelId) throws Exception {
		UserLevel userLevel = userMapper.selectUserLevel(userId,levelId);
		String beginAt = userLevel.getBeginAt();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long datetime = 0;
		long beginTime = 0;
		try {
			datetime = dateFormat.parse(date).getTime();
			beginTime = dateFormat.parse(beginAt).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long order=(datetime-beginTime)/(24 * 60 * 60 * 1000)+1;
		SquirrelLesson lesson= squirrelLessonMapper.selectByCondition(Integer.parseInt(order+""),levelId);
		if(lesson==null) {
			throw new Exception("squirrel_lessons:lesson is null  levelId:"+levelId+" order:"+order);
		}
		return lesson;
	}

	public String findMin(String a, String b, String c) {
		String min, temp;
		min = a;
		temp = b.compareTo(c)<0 ? b : c;
		min = min.compareTo(temp)<0 ? min : temp;
		return min;
	}

    public int editPurchaseRecord(Integer userId, String expireDate, 
    		String beginAt, Integer sendLessonDays, 
    		String beforeVipEndTime, Integer levelId) throws ParseException {
        String vipEndTime = null;
        if(expireDate != null && expireDate.length() > 6){
            vipEndTime = expireDate;
        }else if(expireDate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE,Integer.parseInt(expireDate));
            vipEndTime = sdf.format(cal.getTime());
        }
        log.info("userId:{}, levelId:{}, beginAt:{},sendLessonDays:{}",userId, levelId,beginAt,sendLessonDays);
        int result = userMapper.editUserLevel(beginAt,beginAt,vipEndTime,userId,sendLessonDays, levelId);

        return result;
    }

    /**
     * 修改推课天数（一次性推出）
     * */
    public int editPurchaseRecord(Integer userId, Integer sendLessonDays, Integer levelId) throws ParseException {

    	log.info("userId:{}, levelId:{}, sendLessonDays:{}",userId,levelId,sendLessonDays);
    	
		Integer maxOrder = squirrelLessonMapper.selectMaxOrder(levelId);
		if(sendLessonDays > maxOrder){
			sendLessonDays = maxOrder;
		}

		sendLessonDays = sendLessonDays - 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE,0-sendLessonDays);
        String beginAt = sdf.format(cal.getTime());
        log.info("userId:{}, levelId:{}, sendLessonDays:{}",userId,levelId,sendLessonDays);
        int result = userMapper.editUserLevel(beginAt,beginAt,null,userId,sendLessonDays, levelId);

        return result;
    }

	public Map<String, Integer> lessonRecord(MongoUser mongoUser, Integer levelId) {
		Map<String, Integer> recordMap = new HashMap<String, Integer>();
		try {
			recordMap = recordUtil.alreadyDaysAll(mongoUser,levelId);
			log.info("mongoUser==null ::"+ (mongoUser==null));
			// 计算推课天数
			if(mongoUser==null) {
				recordMap.put("sendCourseDays", 0);
				return recordMap;
			}
			log.info("mongoUser!=null ::"+ (mongoUser==null));
			UserLevel userLevel = userMapper.selectUserLevel(mongoUser.getUserId(), levelId);
			Integer order = squirrelLessonMapper.selectMaxOrder(levelId);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String nowTime = df.format(date);
			Date endDate = dateFormat.parse(userLevel.getVipEndTime());
			Date nowDate = dateFormat.parse(nowTime);
			Date beginDate = dateFormat.parse(userLevel.getBeginAt());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(beginDate);
			calendar.add(Calendar.DATE, order);
			long maxOrderTime=calendar.getTimeInMillis();
			log.info("calendar.getTimeInMillis() ::"+ maxOrderTime+
					"nowDate.getTime() ::"+ nowDate.getTime()
					+"endDate.getTime() ::"+ endDate.getTime());
			long time = findMin(maxOrderTime, nowDate.getTime(), endDate.getTime());
			long day = (time - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			if(time!=maxOrderTime) {
				day+=+1;
			}
			
			log.info("day ::"+ day+"  time ::"+ time+"  userLevel.getBeginAt() ::"+ userLevel.getBeginAt());
			if(day<0) day=0;
			log.info("recordMap:{}",recordMap);
			recordMap.put("sendCourseDays", Integer.parseInt(day + ""));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return recordMap;
	}
	
	public Map<String, String> studyDayLessonRecord(MongoUser mongoUser, Integer levelId, int minOrder, int maxOrder) {
		Map<String, String> recordMap = new HashMap<String, String>();
		log.info("mongoUser==null:{}", mongoUser==null);
		if(mongoUser==null) {
			return null;
		}
		
		try {
			recordMap = recordUtil.studyRecordDays(mongoUser,levelId, minOrder, maxOrder);
			
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String nowTime = df.format(new Date());
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
//			UserLevel userLevel = userMapper.selectUserLevel(mongoUser.getUserId(), levelId);
//			Integer order = squirrelLessonMapper.selectMaxOrder(levelId);
//			
//			Date nowDate = dateFormat.parse(nowTime);
//			Date beginDate = dateFormat.parse(userLevel.getBeginAt());
//			Date endDate = dateFormat.parse(userLevel.getVipEndTime());
//			
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(beginDate);
//			calendar.add(Calendar.DATE, order);
//			long maxOrderTime=calendar.getTimeInMillis();
//			
//			log.info("maxOrderTime:{}, nowDateTime:{}, endDateTime:{}",
//					maxOrderTime, nowDate.getTime(), endDate.getTime() );
//			long minEndTime = findMin(maxOrderTime, nowDate.getTime(), endDate.getTime());
//			
//			long day = (minEndTime - beginDate.getTime()) / (24 * 60 * 60 * 1000);
//			if(minEndTime!=maxOrderTime) {
//				day=day+1;
//			}
//			if(day<0) day=0;
//			log.info("已推课天数day:{}, minEndTime:{}, beginAt():{}", day, minEndTime, userLevel.getBeginAt());
//			log.info("recordMap:{}",recordMap);
		}catch (Exception e) {
			e.printStackTrace();
			log.info("Exception:{}",e);
		}
		return recordMap;
	}

	long findMin(long a, long b, long c) {
		long min, temp;
		min = a;
		temp = b < c ? b : c;
		min = min < temp ? min : temp;
		return min;
	}
	
	public static void main(String[] args){
		int day = 0;
		day+=+1;
		System.out.println(day);
		day+=+1;
		System.out.println(day);
	}
}

