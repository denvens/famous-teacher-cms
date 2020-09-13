package com.qingclass.squirrel.cms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qingclass.squirrel.cms.entity.user.MongoUser;
import com.qingclass.squirrel.cms.entity.user.UserLevel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.user.UserMapper;

@Component
public class RecordUtil {
	
	private final int SUBJECT_ID = 1000000;// subjectId
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SquirrelLessonMapper squirrelLessonMapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 计算该用户全部学习天数,学完天数，分享天数
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> alreadyDaysAll(MongoUser mongoUser, Integer levelId) {
		logger.info("alreadyDaysAll====start==== mongoUser:"+JSON.toJSONString(mongoUser) +" :levelId:"+levelId);
		int alreadyStudyDaysAll = 0;
		int alreadyFinishAll = 0;
		int alreadyShareDaysAll = 0;
		int alreadyCardDaysAll = 0;
		try{
			if (mongoUser != null && mongoUser.getLearnHistory() != null) {
				Map<String, Object> map = (Map<String, Object>) mongoUser.getLearnHistory().get("subjects");
				if (map != null) {
					Map<String, Object> map1 = (Map<String, Object>) ((Map<String, Object>) map
							.get("subject-" + SUBJECT_ID)).get("levels");
					if (map1 != null) {
						Map<String, Object> map2 = (Map<String, Object>) ((Map<String, Object>) map1
								.get("level-" + levelId)).get("lessons");
						if (map2 != null) {
							for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
								alreadyStudyDaysAll++; // 学习天数增加
								Map<String, Object> record = (Map<String, Object>) ((Map<String, Object>) entry2.getValue())
										.get("record");
								if (record != null) {

									if (record.get("isFinish") != null && (boolean) record.get("isFinish")) {
										alreadyFinishAll++;
									}
									if (record.get("isShare") != null && (boolean) record.get("isShare")) {
										alreadyShareDaysAll++;
									}
									if (record.get("isCard") != null && (boolean) record.get("isCard")) {
										alreadyCardDaysAll++;
									}
								}
							}
						}
					}
				}
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			logger.warn("可忽略空指针");
			return new HashMap<>();
		}


		Map<String, Integer> map = new HashMap<>();
		map.put("alreadyStudyDaysAll", alreadyStudyDaysAll);
		map.put("alreadyFinishAll", alreadyFinishAll);
		map.put("alreadyShareDaysAll", alreadyShareDaysAll);
		map.put("alreadyCardDaysAll",alreadyCardDaysAll);
		logger.info("alreadyDaysAll====end==== alreadyShareDaysAll:"+alreadyShareDaysAll 
				+" :alreadyFinishAll:"+alreadyFinishAll+" :alreadyShareDaysAll:"+alreadyShareDaysAll
				+" :alreadyCardDaysAll"+alreadyCardDaysAll);
		return map;
	}
	
	public Map<String, String> studyRecordDays(MongoUser mongoUser, Integer levelId,
			int minOrder, int maxOrder) {
		logger.info("studyDays====start==== mongoUser:{}, levelId:{}", JSON.toJSONString(mongoUser), levelId);
		Map<String, String> returnLessonMap = new HashMap<String, String>();
		try{
			if(mongoUser != null && mongoUser.getLearnHistory() != null) {
				Map<String, Object> subjectMap = (HashMap<String, Object>) mongoUser.getLearnHistory().get("subjects");
				if (subjectMap != null) {
					Map<String, Object> levelMap = (Map<String, Object>) 
						( (Map<String, Object>)subjectMap.get("subject-" + SUBJECT_ID) ).get("levels");
					if (levelMap != null && levelMap.get("level-" + levelId)!=null) {
						Map<String, Object> lessonMap = (Map<String, Object>) 
							( (Map<String, Object>) levelMap.get("level-" + levelId) ).get("lessons");
						if (lessonMap != null) {
							for (Map.Entry<String, Object> entry2 : lessonMap.entrySet()) {
								Map<String, Object> record = (Map<String, Object>) 
									((Map<String, Object>) entry2.getValue()).get("record");
								if (record != null) {
									if (record.get("isFinish") != null && (boolean) record.get("isFinish")) {
										returnLessonMap.put(record.get("order").toString(), "已学完");
									}else if (record.get("isFinish") != null && !(boolean) record.get("isFinish")) {
										returnLessonMap.put(record.get("order").toString(), "已补学");
									}else{
										returnLessonMap.put(record.get("order").toString(), "未学习");
									}
								}
							}
						}
					}
				}
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			logger.warn("可忽略空指针,学习记录studyDays.");
			return new HashMap<>();
		}
		
		for(int i=minOrder; i<=maxOrder; i++){
			String dayValue = returnLessonMap.get(i+"");
			if(dayValue==null){
				returnLessonMap.put(i+"", "未学习");
			}
		}
		
		return returnLessonMap;
	}

	/**
	 * 计算该用户全部推课天数
	 */
	public int alreadySendDaysAll(int userId) {
		int alreadySendDaysAll = 0;

		List<UserLevel> userLevels = userMapper.selectUserLevels(userId);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		loop: for (UserLevel ul : userLevels) {
			Date beginAt;
			Date vipBeginTime;
			Date vipEndTime;
			try {
				beginAt = df.parse(ul.getBeginAt());
				vipBeginTime = df.parse(ul.getVipBeginTime());
				vipEndTime = df.parse(ul.getVipEndTime());
			} catch (ParseException e) {
				logger.warn("parse is error");
				return 0;
			}
			Date now = new Date();

			long a = now.getTime() - beginAt.getTime();// 开课天数

			if (a < 0) {// 未开课略过
				continue loop;
			}

			long b = vipEndTime.getTime() - vipBeginTime.getTime();// 会员有效天数

			int lessonCount = squirrelLessonMapper.count(ul.getLevelId(), 0);// 本level课程总数

			if (a < b && a < lessonCount) {
				alreadySendDaysAll += a;
			} else if (a < b && a >= lessonCount) {
				alreadySendDaysAll += lessonCount;
			} else if (a >= b) {
				alreadySendDaysAll += b;
			}

		}
		return alreadySendDaysAll;
	}

}
