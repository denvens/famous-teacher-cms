package com.qingclass.squirrel.cms.controller;


import com.qingclass.squirrel.cms.Service.SquirrelChannelService;
import com.qingclass.squirrel.cms.Service.SquirrelLevelService;
import com.qingclass.squirrel.cms.Service.SquirrelWxShareService;
import com.qingclass.squirrel.cms.entity.cms.*;
import com.qingclass.squirrel.cms.entity.statistic.ChannelDetailStatistic;
import com.qingclass.squirrel.cms.entity.statistic.ChannelStatistic;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelChannelMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxShareMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	SquirrelChannelService squirrelChannelService;
	@Autowired
	SquirrelChannelMapper squirrelChannelMapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());


	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String,Object> list(@RequestParam(value = "pageNo",required = false)Integer pageNo,@RequestParam(value = "pageSize",required = false)Integer pageSize){

		SquirrelChannel squirrelChannel = new SquirrelChannel();
		squirrelChannel.setPageNo(pageNo);
		squirrelChannel.setPageSize(pageSize);

		List<SquirrelChannel> squirrelChannels = squirrelChannelService.selectAll(squirrelChannel);


		HashMap<String, Object> map = new HashMap<>();
		map.put("list",squirrelChannels);
		map.put("pageTotal",squirrelChannelMapper.count());
		return Tools.s(map);
	}

	@ResponseBody
	@RequestMapping(value = "/list-base", method = RequestMethod.POST)
	public Map<String,Object> listBase(){

		List<SquirrelChannel> squirrelChannels = squirrelChannelMapper.selectBase();


		HashMap<String, Object> map = new HashMap<>();
		map.put("list",squirrelChannels);
		map.put("pageTotal",squirrelChannelMapper.count());
		return Tools.s(map);
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object> create(@RequestParam(value = "name",required = false)String name, @RequestParam(value = "validity",required = false)String validity,
									 @RequestParam(value = "type",required = false)String type, @RequestParam(value = "messages",required = false)String messages,
									 @RequestParam(value = "sendTime",required = false)String sendTime, HttpServletRequest req){

		CmsAdmin admin = (CmsAdmin)req.getSession().getAttribute(CmsAdmin.SESSION_SQUIRREL_ADMIN_KEY);

		SquirrelChannel squirrelChannel = new SquirrelChannel();
		squirrelChannel.setName(name);
		squirrelChannel.setValidity(validity);
		squirrelChannel.setType(type);
		squirrelChannel.setMessages(messages);
		squirrelChannel.setSendTime(sendTime);
		String code = Tools.randomString(5);
		squirrelChannel.setCode(code);
		squirrelChannel.setCreateBy(admin.getUserName());

		Map<String,Object> map = new HashMap<>();
		map.put("t",type);
		map.put("k",code);
		squirrelChannel.setScene(map);
		squirrelChannelService.insert(squirrelChannel);

		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Map<String,Object> edit(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "name",required = false)String name,
								   @RequestParam(value = "validity",required = false)String validity,
								   @RequestParam(value = "messages",required = false)String messages,
								   @RequestParam(value = "sendTime",required = false)String sendTime, HttpServletRequest req){

		CmsAdmin admin = (CmsAdmin)req.getSession().getAttribute(CmsAdmin.SESSION_SQUIRREL_ADMIN_KEY);

		SquirrelChannel squirrelChannel = new SquirrelChannel();
		squirrelChannel.setId(id);
		squirrelChannel.setName(name);
		squirrelChannel.setValidity(validity);
		squirrelChannel.setMessages(messages);
		squirrelChannel.setSendTime(sendTime);
		squirrelChannel.setUpdateBy(admin.getUserName());

		squirrelChannelMapper.updateByPrimaryKey(squirrelChannel);

		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Map<String,Object> info(@RequestParam(value = "id",required = false)Integer id){

		SquirrelChannel squirrelChannel = new SquirrelChannel();
		squirrelChannel.setId(id);

		SquirrelChannel squirrelChannels = squirrelChannelMapper.selectByPrimaryKey(squirrelChannel);

		return Tools.s(squirrelChannels);
	}

	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Map<String,Object> detail(@RequestParam(value = "pageNo",required = false)Integer pageNo,@RequestParam(value = "pageSize",required = false)Integer pageSize,
									 @RequestParam(value = "code",required = false)String code,@RequestParam(value = "param",required = false)String param){

		pageNo = (pageNo - 1) * pageSize;
		ChannelStatistic channelStatistic = new ChannelStatistic();
		channelStatistic.setPageNo(pageNo);
		channelStatistic.setPageSize(pageSize);
		channelStatistic.setChannelCode(code);

		if(param != null && !param.equals("")){
			channelStatistic.setParam(param);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] split = param.split(",");
			try {
				channelStatistic.setBeginTime(sdf.parse(split[0]));
				channelStatistic.setEndTime(sdf.parse(split[1]));
			} catch (ParseException e) {
				logger.error("渠道二维码查询详情 : "+"[{param : "+param+"}..."+e.getMessage()+"]",e);
			}

		}
		List<ChannelStatistic> channelStatistics = squirrelChannelMapper.selectChannelDetail(channelStatistic);
		HashMap<String, Object> map = new HashMap<>();
		map.put("list",channelStatistics);
		map.put("total",squirrelChannelMapper.selectChannelDetailCount(channelStatistic));


		return Tools.s(map);
	}

	@ResponseBody
	@RequestMapping(value = "/detail-info", method = RequestMethod.POST)
	public Map<String,Object> detailInfo(@RequestParam(value = "pageNo",required = false)Integer pageNo,@RequestParam(value = "pageSize",required = false)Integer pageSize,
									 @RequestParam(value = "code",required = false)String code,@RequestParam(value = "param",required = false)String param,
										 @RequestParam(value = "subscribe",required = false)Integer subscribe,@RequestParam(value = "levelOne",required = false)Integer levelOne,
										 @RequestParam(value = "levelTwo",required = false)Integer levelTwo, @RequestParam(value = "date",required = false)String date){

		pageNo = (pageNo - 1) * pageSize;

		ChannelDetailStatistic channelDetailStatistic = new ChannelDetailStatistic();
		channelDetailStatistic.setPageNo(pageNo);
		channelDetailStatistic.setPageSize(pageSize);
		channelDetailStatistic.setCode(code);

		if(levelOne != null){
			channelDetailStatistic.setLevelOne(levelOne);
		}
		if(levelTwo != null){
			channelDetailStatistic.setLevelTwo(levelTwo);
		}
		if(subscribe != null){
			channelDetailStatistic.setSubscribe(subscribe);
		}

		if(param != null && !param.equals("")){
			channelDetailStatistic.setParam(param);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			String[] split = param.split(",");
			if(split[0].substring(0,10).equals(date) && !split[1].substring(0,10).equals(date)){//查询日期和总条件开始日期相同并且和结束时间不同
				try {
					channelDetailStatistic.setBeginTime(sdf.parse(split[0]));
					cal.setTime(sdf.parse(split[0]));
					cal.add(Calendar.DAY_OF_MONTH, 1);
					channelDetailStatistic.setEndTime(sdf.parse(sdf2.format(cal.getTime())+" 00:00:00"));
				} catch (ParseException e) {
					logger.error("渠道二维码查询详情信息 : "+"[{param : "+param+"}..."+e.getMessage()+"]",e);
				}
			}else if(split[0].substring(0,10).equals(date) && split[1].substring(0,10).equals(date)){//查询日期和总条件开始日期和结束日期均相同
				try {
					channelDetailStatistic.setBeginTime(sdf.parse(split[0]));
					channelDetailStatistic.setEndTime(sdf.parse(split[1]));
				} catch (ParseException e) {
					logger.error("渠道二维码查询详情信息 : "+"[{param : "+param+"}..."+e.getMessage()+"]",e);
				}
			}else if(split[1].substring(0,10).equals(date) && !split[0].substring(0,10).equals(date)){//查询日期和总条件结束日期相同
				try {
					channelDetailStatistic.setEndTime(sdf.parse(split[1]));
					channelDetailStatistic.setBeginTime(sdf.parse(date+" 00:00:00"));
				} catch (ParseException e) {
					logger.error("渠道二维码查询详情信息 : "+"[{param : "+param+"}..."+e.getMessage()+"]",e);
				}
			}else{//均不同
				try {
					channelDetailStatistic.setEndTime(sdf.parse(date+" 23:59:59"));
					channelDetailStatistic.setBeginTime(sdf.parse(date+" 00:00:00"));
				} catch (ParseException e) {
					logger.error("渠道二维码查询详情信息 : "+"[{param : "+param+"}..."+e.getMessage()+"]",e);
				}
			}
		}else{
			channelDetailStatistic.setParam("none");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				channelDetailStatistic.setEndTime(sdf.parse(date+" 23:59:59"));
				channelDetailStatistic.setBeginTime(sdf.parse(date+" 00:00:00"));
			} catch (ParseException e) {
				logger.error("渠道二维码查询详情信息 : "+"[{param : "+param+"}..."+e.getMessage()+"]",e);
			}
		}

		List<ChannelDetailStatistic> channelDetailStatistics = squirrelChannelMapper.selectChannelDetailInfo(channelDetailStatistic);
		Map<String, Object> map = new HashMap<>();

		map.put("list",channelDetailStatistics);
		map.put("total",squirrelChannelMapper.selectChannelDetailInfoCount(channelDetailStatistic));
		map.put("param",param);
		return Tools.s(map);
	}
}
