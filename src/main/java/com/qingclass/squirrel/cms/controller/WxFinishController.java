package com.qingclass.squirrel.cms.controller;


import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.WxService;
import com.qingclass.squirrel.cms.entity.wechat.WxCustom;
import com.qingclass.squirrel.cms.entity.wechat.WxTemplate;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxCustomMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxTemplateMapper;
import com.qingclass.squirrel.cms.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx-finish")
public class WxFinishController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SquirrelWxTemplateMapper squirrelWxTemplateMapper;
	@Autowired
	SquirrelWxCustomMapper squirrelWxCustomMapper;
	@Autowired
	WxService wxService;

	private final String TEMPLATE_TYPE = "finish-notice-template";
	private final String CUSTOM_TYPE = "finish-notice-custom";
	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Map<String,Object> info(){

		HashMap<String, Object> mapz = new HashMap<>();

		List<WxTemplate> wxTemplates = squirrelWxTemplateMapper.selectByType(TEMPLATE_TYPE);

		if(wxTemplates.size() == 1){
			WxTemplate wxTemplate = wxTemplates.get(0);
			if(wxTemplate.getContent() != null){
				Map<String,Object> map = JSONObject.parseObject(wxTemplate.getContent());
				wxTemplate.setContentMap(map);
				wxTemplate.setContent(null);
			}
			mapz.put("template",wxTemplate);
		}else if(wxTemplates.size() > 1){
			return Tools.f("模版数量有误");
		}else{//没有就创建
			WxTemplate wxTemplate = new WxTemplate();
			wxTemplate.setType(TEMPLATE_TYPE);
			wxTemplate.setIsOpen(0);
			squirrelWxTemplateMapper.insert(wxTemplate);
			List<WxTemplate> wx1 = squirrelWxTemplateMapper.selectByType(TEMPLATE_TYPE);
			mapz.put("template",wx1.get(0));
		}

		List<WxCustom> wxCustoms = squirrelWxCustomMapper.selectByType(CUSTOM_TYPE);

		if(wxCustoms.size() == 1){
			WxCustom wxCustom = wxCustoms.get(0);
			mapz.put("custom",wxCustom);
		}else if(wxTemplates.size() > 1){
			return Tools.f("客服消息数量有误");
		}else{//没有就创建
			WxCustom wxCustom = new WxCustom();
			wxCustom.setType(CUSTOM_TYPE);
			wxCustom.setIsOpen(0);
			squirrelWxCustomMapper.insert(wxCustom);
			List<WxCustom> wx1 = squirrelWxCustomMapper.selectByType(CUSTOM_TYPE);
			mapz.put("custom",wx1.get(0));
		}

		return Tools.s(mapz);
	}

	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit-template", method = RequestMethod.POST)
	public Map<String,Object> editTemplate(
			@RequestParam(value = "id",required = false)Integer id,
			@RequestParam(value = "contentHead",required = false)String contentHead,
			@RequestParam(value = "contentInfo",required = false)String contentInfo,
			@RequestParam(value = "contentDate",required = false)String contentDate,
			@RequestParam(value = "contentBody",required = false)String contentBody,
			@RequestParam(value = "url",required = false)String url,
			@RequestParam(value = "contentInfoColor",required = false)String contentInfoColor,
			@RequestParam(value = "contentBodyColor",required = false)String contentBodyColor){


		HashMap<String, Object> map = new HashMap<>();
		if(contentHead != null){
			map.put("contentHead",contentHead);
		}
		if(contentInfo != null){
			map.put("contentInfo",contentInfo);
		}
		if(contentDate != null){
			map.put("contentDate",contentDate);
		}
		if(contentBody != null){
			map.put("contentBody",contentBody);
		}
		if(contentBodyColor != null){
			map.put("contentBodyColor",contentBodyColor);
		}
		if(contentInfoColor != null){
			map.put("contentInfoColor",contentInfoColor);
		}

		WxTemplate wxTemplate = new WxTemplate();
		wxTemplate.setId(id);
		wxTemplate.setType(TEMPLATE_TYPE);
		wxTemplate.setUrl(url);
		wxTemplate.setContent(JSONObject.toJSONString(map));

		squirrelWxTemplateMapper.updateByPrimaryKey(wxTemplate);

		return Tools.s();
	}

	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit-custom", method = RequestMethod.POST)
	public Map<String,Object> editCustom(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "content",required = false)String content){


		HashMap<String, Object> map = new HashMap<>();


		WxCustom wxCustom = new WxCustom();
		wxCustom.setId(id);
		wxCustom.setType(CUSTOM_TYPE);
		wxCustom.setContent(content);

		squirrelWxCustomMapper.updateByPrimaryKey(wxCustom);

		return Tools.s();
	}


	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/open-or-close-template", method = RequestMethod.POST)
	public Map<String,Object> openOrCloseTemplate(
			@RequestParam(value = "id",required = false)Integer id,
			@RequestParam(value = "isOpen",required = false)Integer isOpen){

		WxTemplate wxTemplate = new WxTemplate();

		wxTemplate.setId(id);
		wxTemplate.setIsOpen(isOpen);

		squirrelWxTemplateMapper.updateOpenOrClose(wxTemplate);

		return Tools.s();
	}

	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/open-or-close-custom", method = RequestMethod.POST)
	public Map<String,Object> openOrCloseCustom(
			@RequestParam(value = "id",required = false)Integer id,
			@RequestParam(value = "isOpen",required = false)Integer isOpen){

		WxCustom wxCustom = new WxCustom();

		wxCustom.setId(id);
		wxCustom.setIsOpen(isOpen);

		squirrelWxCustomMapper.updateOpenOrClose(wxCustom);

		return Tools.s();
	}
	
	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/preview-custom", method = RequestMethod.POST)
	public Map<String,Object> previewCustom(
			@RequestParam(value = "openId",required = false)String openId,
			@RequestParam(value = "content",required = false)String content){

		System.out.println(content);
		Map<String,Object> text = new HashMap<>();
		text.put("content",content);
		Map<String,Object> map = new HashMap<>();
		map.put("touser",openId);
		map.put("msgtype","text");
		map.put("text",text);

		String loggerInfo = "send custom successful.";
		String loggerErr = "send custom failed.";
		wxService.sendToWx(map,wxService.SEND_CUSTOM+wxService.getAccessToken(),loggerInfo,loggerErr);

		return Tools.s();
	}

	/**
	 *
	 * */
	@ResponseBody
	@RequestMapping(value = "/preview-template", method = RequestMethod.POST)
	public Map<String,Object> previewTemplate(
			@RequestParam(value = "openId",required = false)String openId,
			@RequestParam(value = "contentInfo",required = false)String contentInfo,
			@RequestParam(value = "contentBody",required = false)String contentBody,
			@RequestParam(value = "url",required = false)String url,
			@RequestParam(value = "contentInfoColor",required = false)String contentInfoColor,
			@RequestParam(value = "contentBodyColor",required = false)String contentBodyColor){
		
		logger.info("预览模板消息start：openId:{}, contentInfo:{}, contentBody:{}, url:{}, contentInfoColor:{}, contentBodyColor:{}", 
				openId, contentInfo, contentBody, url, contentInfoColor, contentBodyColor);
		
		Map<String,Object> name = new HashMap<>();
		name.put("value",contentInfo);
		name.put("color",contentInfoColor);
		Map<String,Object> remark = new HashMap<>();
		remark.put("value",contentBody);
		remark.put("color",contentBodyColor);
		Map<String,Object> data = new HashMap<>();
		data.put("name",name);
		data.put("remark",remark);


		Map<String,Object> params = new HashMap<>();
		params.put("touser",openId);
		params.put("template_id",wxService.FINISH_NOTICE_TEMPLATE_ID);
		params.put("url",url);
		params.put("data",data);

		String loggerInfo = "send template successful.";
		String loggerErr = "send template failed.";
		logger.info("template_id:{}", wxService.FINISH_NOTICE_TEMPLATE_ID);
		wxService.sendToWx(params,wxService.SEND_TEMPLATE+wxService.getAccessToken(),loggerInfo,loggerErr);
		logger.info("预览模板消息end.");
		return Tools.s();
	}
}