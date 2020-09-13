package com.qingclass.squirrel.cms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.tools.Tool;

import com.qingclass.squirrel.cms.entity.statistic.InvitationSendCashStatistic;
import com.qingclass.squirrel.cms.entity.statistic.InvitationSendCashStatisticBeginReq;
import com.qingclass.squirrel.cms.entity.statistic.InvitationSendCashStatisticReq;
import com.qingclass.squirrel.cms.entity.statistic.SquirrelBatchesStatistic;
import com.qingclass.squirrel.cms.entity.statistic.SquirrelFollowUp;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.statistic.BatchesStatisticMapper;
import com.qingclass.squirrel.cms.mapper.statistic.FollowUpMapper;
import com.qingclass.squirrel.cms.mapper.statistic.InvitationSendCashStatisticMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import com.qingclass.squirrel.cms.mapper.statistic.SquirrelKvalueStatisticMapper;
import com.qingclass.squirrel.cms.utils.Tools;

@RestController
@RequestMapping(value = "/data")
public class DataStatisticController {

	@Autowired
	private SquirrelKvalueStatisticMapper squirrelKvalueStatisticMapper;
	@Autowired
	private SquirrelLevelMapper squirrelLevelMapper;
	@Autowired
	private BatchesStatisticMapper batchesStatisticMapper;
	@Autowired
	private FollowUpMapper followUpMapper;
	@Autowired
	private SquirrelLessonMapper squirrelLessonMapper;
	@Autowired
	private InvitationSendCashStatisticMapper sendCashStatisticMapper;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/data-statistic")
	public Map<String, Object> dataStatistic(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		startTime = StringUtils.isEmpty(startTime) ? null : startTime;
		endTime = StringUtils.isEmpty(endTime) ? null : endTime;
		SquirrelLevel squirrelLevel = squirrelLevelMapper
				.selectByPrimaryKey(levelId);
		Integer lessonDay = squirrelLevel.getLessonDay();
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<Map<String, Object>> list = squirrelKvalueStatisticMapper
				.selectAllByConditionMap(startTime, endTime, levelId, lessonDay);
		int totalNum = squirrelKvalueStatisticMapper.selectCountByCondition(
				startTime, endTime, levelId, lessonDay);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
		list = pageInfo.getList();
		returnMap.put("zebraCashRecords", list);
		returnMap.put("totalNum", totalNum);
		returnMap.put("lessonDay", lessonDay);
		return Tools.s(returnMap);
	}

	@PostMapping("/audition-statistic")
	public Map<String, Object> auditionStatistic(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		startTime = StringUtils.isEmpty(startTime) ? null : startTime;
		endTime = StringUtils.isEmpty(endTime) ? null : endTime;
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<Map<String, Object>> list = squirrelKvalueStatisticMapper
				.selectAuditionByConditionMap(startTime, endTime, levelId);
		int totalNum = squirrelKvalueStatisticMapper
				.selectAuditionCountByCondition(startTime, endTime, levelId);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
		list = pageInfo.getList();
		returnMap.put("list", list);
		returnMap.put("totalNum", totalNum);
		return Tools.s(returnMap);
	}

	@PostMapping("/lesson-day-edit")
	public Map<String, Object> lessonDayEdit(
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(name = "lessonDay", required = false) Integer lessonDay,
			HttpServletRequest request) {
		squirrelLevelMapper.updateLessonDay(levelId, lessonDay);
		return Tools.s();
	}

	@PostMapping("/batches-statistic-detail")
	public Map<String, Object> batchesStatisticDetail(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(name = "beginDate", required = false) String beginDate,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		pageNo = (pageNo - 1) * pageSize;

		SquirrelBatchesStatistic squirrelBatchesStatistic = new SquirrelBatchesStatistic();
		squirrelBatchesStatistic.setPageNo(pageNo);
		squirrelBatchesStatistic.setPageSize(pageSize);
		if (startTime != null && !startTime.equals("")) {
			squirrelBatchesStatistic.setStartTime(startTime);
		}
		if (endTime != null && !endTime.equals("")) {
			squirrelBatchesStatistic.setEndTime(endTime);
		}

		squirrelBatchesStatistic.setLevelId(levelId);

		squirrelBatchesStatistic.setBeginDate(beginDate);

		List<SquirrelBatchesStatistic> squirrelBatchesStatistics = batchesStatisticMapper
				.selectByLevelIdAndBeginDate(squirrelBatchesStatistic);

		Map<String, Object> map = new HashMap<>();
		map.put("list", squirrelBatchesStatistics);
		map.put("total", batchesStatisticMapper
				.selectByLevelIdAndBeginDateCount(squirrelBatchesStatistic));

		return Tools.s(map);

	}

	@PostMapping("/batches-statistic")
	public Map<String, Object> batchesStatistic(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		pageNo = (pageNo - 1) * pageSize;

		SquirrelBatchesStatistic squirrelBatchesStatistic = new SquirrelBatchesStatistic();
		squirrelBatchesStatistic.setPageNo(pageNo);
		squirrelBatchesStatistic.setPageSize(pageSize);
		if (startTime != null && !startTime.equals("")) {
			squirrelBatchesStatistic.setStartTime(startTime);
		}
		if (endTime != null && !endTime.equals("")) {
			squirrelBatchesStatistic.setEndTime(endTime);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		squirrelBatchesStatistic.setCurrentDate(sdf.format(cal.getTime()));

		squirrelBatchesStatistic.setLevelId(levelId);

		List<SquirrelBatchesStatistic> squirrelBatchesStatistics = batchesStatisticMapper
				.selectByLevelId(squirrelBatchesStatistic);

		Map<String, Object> map = new HashMap<>();
		map.put("list", squirrelBatchesStatistics);
		map.put("total", batchesStatisticMapper
				.selectByLevelIdCount(squirrelBatchesStatistic));

		return Tools.s(map);

	}

	@PostMapping("/follow-up-action-statistic")
	public Map<String, Object> followUpActionStatistic(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		pageNo = (pageNo - 1) * pageSize;

		SquirrelFollowUp squirrelFollowUp = new SquirrelFollowUp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		squirrelFollowUp.setPageNo(pageNo);
		squirrelFollowUp.setPageSize(pageSize);

		if (startTime != null && endTime != null) {
			try {
				squirrelFollowUp.setStartTime(sdf.parse(startTime));
				squirrelFollowUp.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				logger.error("参数有误...", e.getMessage());
			}
		}
		squirrelFollowUp.setLevelId(levelId);

		List<SquirrelFollowUp> squirrelFollowUpActionList = followUpMapper
				.getSquirrelFollowUpActionList(squirrelFollowUp);

		Map<String, Object> map = new HashMap<>();
		map.put("list", squirrelFollowUpActionList);
		map.put("total", followUpMapper
				.getSquirrelFollowUpActionListCount(squirrelFollowUp));

		return Tools.s(map);
	}

	@PostMapping("/follow-up-k-value-statistic")
	public Map<String, Object> followUpKValueStatistic(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = false) Integer levelId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		pageNo = (pageNo - 1) * pageSize;

		SquirrelFollowUp squirrelFollowUp = new SquirrelFollowUp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		squirrelFollowUp.setPageNo(pageNo);
		squirrelFollowUp.setPageSize(pageSize);

		if (startTime != null && endTime != null) {
			try {
				squirrelFollowUp.setStartTime(sdf.parse(startTime));
				squirrelFollowUp.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				logger.error("参数有误...", e.getMessage());
			}
		}
		squirrelFollowUp.setLevelId(levelId);

		List<SquirrelFollowUp> squirrelFollowUpActionList = followUpMapper
				.getSquirrelFollowUpKValueList(squirrelFollowUp);

		Map<String, Object> map = new HashMap<>();
		map.put("list", squirrelFollowUpActionList);
		map.put("total", followUpMapper
				.getSquirrelFollowUpActionListCount(squirrelFollowUp));

		return Tools.s(map);
	}

	@PostMapping("/follow-up-k-value-days")
	public Map<String, Object> followUpKValueDays(
			@RequestParam(name = "levelId", required = false) Integer levelId) {

		Integer integer = squirrelLessonMapper.selectMaxOrder(levelId);

		return Tools.s(integer);
	}

	@PostMapping("/invite-conversion-data-statistic")
	public Map<String, Object> inviteConversionDataStatistic(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = true) Integer levelId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		pageNo = (pageNo - 1) * pageSize;

		InvitationSendCashStatisticReq sendCashStatisticReq = new InvitationSendCashStatisticReq();
		sendCashStatisticReq.setPageNo(pageNo);
		sendCashStatisticReq.setPageSize(pageSize);
		if (startTime != null && !startTime.equals("")) {
			sendCashStatisticReq.setStartTime(startTime);
		}
		if (endTime != null && !endTime.equals("")) {
			sendCashStatisticReq.setEndTime(endTime);
		}

		sendCashStatisticReq.setLevelId(levelId);
		
		List<InvitationSendCashStatistic> squirrelBatchesStatistics = sendCashStatisticMapper
				.selectByLevelId(sendCashStatisticReq);

		Map<String, Object> map = new HashMap<>();
		map.put("list", squirrelBatchesStatistics);
		map.put("total", sendCashStatisticMapper
				.selectByLevelIdCount(sendCashStatisticReq));
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("levelId", levelId);
		map.put("pageNo", pageSize);
		map.put("pageSize", pageSize);
		return Tools.s(map);

	}
	
	@PostMapping("/invite-conversion-data-statistic-detail")
	public Map<String, Object> inviteConversionDataStatisticDetail(
			@RequestParam(name = "startTime", required = false) String startTime,
			@RequestParam(name = "endTime", required = false) String endTime,
			@RequestParam(name = "levelId", required = true) Integer levelId,
			@RequestParam(name = "beginDate", required = true) String beginDate,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		pageNo = (pageNo - 1) * pageSize;

		InvitationSendCashStatisticBeginReq sendCashStatisticBeginReq = new InvitationSendCashStatisticBeginReq();
		sendCashStatisticBeginReq.setPageNo(pageNo);
		sendCashStatisticBeginReq.setPageSize(pageSize);
		if (startTime != null && !startTime.equals("")) {
			sendCashStatisticBeginReq.setStartTime(startTime);
		}
		if (endTime != null && !endTime.equals("")) {
			sendCashStatisticBeginReq.setEndTime(endTime);
		}

		sendCashStatisticBeginReq.setLevelId(levelId);

		sendCashStatisticBeginReq.setBeginDate(beginDate);

		List<InvitationSendCashStatistic> squirrelBatchesStatistics = sendCashStatisticMapper
				.selectByLevelIdAndBeginDate(sendCashStatisticBeginReq);

		Map<String, Object> map = new HashMap<>();
		map.put("list", squirrelBatchesStatistics);
		map.put("total", sendCashStatisticMapper
				.selectByLevelIdAndBeginDateCount(sendCashStatisticBeginReq));
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("levelId", levelId);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("beginDate", beginDate);
		return Tools.s(map);

	}

}