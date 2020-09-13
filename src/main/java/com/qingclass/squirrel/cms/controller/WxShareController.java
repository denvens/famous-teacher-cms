package com.qingclass.squirrel.cms.controller;


import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelLevelService;
import com.qingclass.squirrel.cms.Service.SquirrelWxShareService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelChannel;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import com.qingclass.squirrel.cms.entity.wechat.WxShare;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelChannelMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxShareMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx-share")
public class WxShareController {

	@Autowired
	SquirrelWxShareMapper squirrelWxShareMapper;
	@Autowired
	SquirrelWxShareService squirrelWxShareService;
	@Autowired
	SquirrelLevelService squirrelLevelService;
	@Autowired
	SquirrelLevelMapper squirrelLevelMapper;
	@Autowired
	SquirrelLessonMapper squirrelLessonMapper;
	@Autowired
	SquirrelChannelMapper squirrelChannelMapper;



	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String,Object> list(){

		List<SquirrelLevel> squirrelLevels = squirrelLevelMapper.selectAll();

		HashMap<String, Object> map = new HashMap<>();
		map.put("list",squirrelLevels);

		return Tools.s(map);
	}

	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Map<String,Object> info(
			@RequestParam(value = "id",required = false)Integer id,
			@RequestParam(value = "levelId",required = false)Integer levelId){
		if(levelId != null){
			RequestInfo info = squirrelLevelService.selectByPrimaryKey(levelId);
			SquirrelLevel level = (SquirrelLevel)info.getDataObject();
			id = level.getShareId();
		}
		WxShare wxShare = squirrelWxShareService.selectByPrimaryKey(id);
		return Tools.s(wxShare);
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object> create(
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "url",required = false)String url,
			@RequestParam(value = "spaceTitle",required = false)String spaceTitle,
			@RequestParam(value = "freTitle",required = false)String freTitle,
			@RequestParam(value = "content",required = false)String content,
			@RequestParam(value = "img",required = false)String img,
			@RequestParam(value = "channelId",required = false)Integer channelId,
			@RequestParam(value = "type",required = false)String type,
			@RequestParam(value = "shareContent",required = false)String shareContent){
		WxShare wxShare = new WxShare();
		wxShare.setUrl(url);
		wxShare.setSpaceTitle(spaceTitle);
		wxShare.setFreTitle(freTitle);
		wxShare.setContent(content);
		wxShare.setImg(img);
		wxShare.setChannelId(channelId);
		wxShare.setShareContent(shareContent);
		if(type != null && !type.equals("")){
			wxShare.setType(type);
		}

		if(channelId != null){
			SquirrelChannel squirrelChannel = new SquirrelChannel();
			squirrelChannel.setId(channelId);
			squirrelChannel = squirrelChannelMapper.selectByPrimaryKey(squirrelChannel);
			wxShare.setChannelQr(squirrelChannel.getTicket());
			wxShare.setChannelQrSite(squirrelChannel.getSite());
		}

		WxShare insert = squirrelWxShareService.insert(wxShare);

		SquirrelLevel squirrelLevel = new SquirrelLevel();
		squirrelLevel.setId(levelId);
		squirrelLevel.setShareId(insert.getId());
		squirrelLevel.setChannelId(channelId);
		squirrelLevelService.update(squirrelLevel);

		wxShare.setId(insert.getId());
		String s = JSONObject.toJSONString(wxShare);
		squirrelWxShareMapper.updateAllShareQuestion(levelId,s);//同步到level下的每一个分享打卡页



		return Tools.s(insert.getId());
	}

	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Map<String,Object> edit(
			@RequestParam(value = "id",required = false)Integer id, 
			@RequestParam(value = "url",required = false)String url,
			@RequestParam(value = "spaceTitle",required = false)String spaceTitle,
			@RequestParam(value = "freTitle",required = false)String freTitle, 
			@RequestParam(value = "content",required = false)String content,
			@RequestParam(value = "img",required = false)String img, 
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "channelId",required = false)Integer channelId,
			@RequestParam(value = "type",required = false)String type,
			@RequestParam(value = "shareContent",required = false)String shareContent){
		WxShare wxShare = new WxShare();
		wxShare.setId(id);
		wxShare.setUrl(url);
		wxShare.setSpaceTitle(spaceTitle);
		wxShare.setFreTitle(freTitle);
		wxShare.setContent(content);
		wxShare.setImg(img);
		wxShare.setChannelId(channelId);
		wxShare.setShareContent(shareContent);
		if(type != null && !type.equals("")){
			wxShare.setType(type);
		}


//		if(channelId != null){
//			SquirrelChannel squirrelChannel = new SquirrelChannel();
//			squirrelChannel.setId(channelId);
//			squirrelChannel = squirrelChannelMapper.selectByPrimaryKey(squirrelChannel);
//			wxShare.setChannelQr(squirrelChannel.getTicket());
//			wxShare.setChannelQrSite(squirrelChannel.getSite());
//		}

		SquirrelLevel squirrelLevel = new SquirrelLevel();
		squirrelLevel.setId(levelId);
		squirrelLevel.setChannelId(channelId);
		squirrelLevel.setShareId(id);
		squirrelLevelService.update(squirrelLevel);

		String s = JSONObject.toJSONString(wxShare);
		squirrelWxShareService.updateByPrimaryKey(wxShare);//修改
		squirrelWxShareMapper.updateAllShareQuestion(levelId,s);//同步到level下的每一个分享打卡页

		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String,Object> delete(@RequestParam(value = "id",required = false)Integer id){
		squirrelWxShareMapper.deleteByPrimaryKey(id);
		return Tools.s();
	}


	@ResponseBody
	@RequestMapping(value = "/save-template", method = RequestMethod.POST)
	public Map<String,Object> insertTemplate(@RequestParam(value = "levelId",required = false)Integer levelId,
											 @RequestParam(value = "type",required = false)String type,
											 @RequestParam(value = "shareContent",required = false)String shareContent){
		WxShare wxShare = squirrelWxShareMapper.selectTemplateByLevelId(levelId);

		if(wxShare!= null && wxShare.getLevelId() != null){
			squirrelWxShareMapper.updateTemplate(levelId,shareContent);
		}else{
			WxShare wxShare1 = new WxShare();
			wxShare1.setLevelId(levelId);
			wxShare1.setShareContent(shareContent);
			wxShare1.setType(type);
			squirrelWxShareMapper.insertTemplate(wxShare1);
		}
		return Tools.s();
	}

}
