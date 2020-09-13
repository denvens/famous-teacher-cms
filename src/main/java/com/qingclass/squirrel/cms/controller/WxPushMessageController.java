package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.WxService;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLessonRemind;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import com.qingclass.squirrel.cms.entity.user.User;
import com.qingclass.squirrel.cms.entity.wechat.WxCustom;
import com.qingclass.squirrel.cms.entity.wechat.WxTemplate;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxCustomMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxTemplateMapper;
import com.qingclass.squirrel.cms.mapper.user.UserMapper;
import com.qingclass.squirrel.cms.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx-push-message")
public class WxPushMessageController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    SquirrelWxTemplateMapper squirrelWxTemplateMapper;
    @Autowired
    SquirrelWxCustomMapper squirrelWxCustomMapper;
    @Autowired
    WxService wxService;
    @Autowired
    JobController jobController;

    @Autowired
    UserMapper userMapper;
    
    private final String PUSH_MESSAGE_TEMPLATE_TYPE = "push-message-begin-study-template";
    private final String PUSH_MESSAGE_CUSTOM_TYPE = "push-message-begin-study-custom";
    private final String CLASS_BEGINS_TEMPLATE_TYPE = "class-begins-template";
    private final String CLASS_BEGINS_CUSTOM_TYPE = "class-begins-custom";
    private final String CLASS_ENDS_TEMPLATE_TYPE = "class-ends-template";
    private final String CLASS_ENDS_CUSTOM_TYPE = "class-ends-custom";
    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Map<String,Object> info(){

        HashMap<String, Object> mapz = new HashMap<>();

        List<WxTemplate> wxTemplates = squirrelWxTemplateMapper.selectByType(PUSH_MESSAGE_TEMPLATE_TYPE);

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
            wxTemplate.setType(PUSH_MESSAGE_TEMPLATE_TYPE);
            wxTemplate.setIsOpen(0);
            squirrelWxTemplateMapper.insert(wxTemplate);
            List<WxTemplate> wx1 = squirrelWxTemplateMapper.selectByType(PUSH_MESSAGE_TEMPLATE_TYPE);
            mapz.put("template",wx1.get(0));
        }

        List<WxCustom> wxCustoms = squirrelWxCustomMapper.selectByType(PUSH_MESSAGE_CUSTOM_TYPE);

        if(wxCustoms.size() == 1){
            WxCustom wxCustom = wxCustoms.get(0);
            mapz.put("custom",wxCustom);
        }else if(wxTemplates.size() > 1){
            return Tools.f("客服消息数量有误");
        }else{//没有就创建
            WxCustom wxCustom = new WxCustom();
            wxCustom.setType(PUSH_MESSAGE_CUSTOM_TYPE);
            wxCustom.setIsOpen(0);
            squirrelWxCustomMapper.insert(wxCustom);
            List<WxCustom> wx1 = squirrelWxCustomMapper.selectByType(PUSH_MESSAGE_CUSTOM_TYPE);
            mapz.put("custom",wx1.get(0));
        }
        SquirrelLessonRemind remind = squirrelWxTemplateMapper.selectRemindTimeDefault("default");
        mapz.put("remind",remind);

        return Tools.s(mapz);
    }

    @ResponseBody
    @RequestMapping(value = "/edit-remind", method = RequestMethod.POST)
    public Map<String,Object> editTemplate(@RequestParam(value = "id",required = false)Integer id,
                                           @RequestParam(value = "remindRate",required = false)String remindRate,
                                           @RequestParam(value = "firstRemind",required = false)String firstRemind,
                                           @RequestParam(value = "secondRemind",required = false)String secondRemind){

        if(id == null){
            squirrelWxTemplateMapper.insertRemindTime(remindRate,firstRemind,secondRemind,"default");
        }else{
            squirrelWxTemplateMapper.updateRemindTime(id, remindRate, firstRemind, secondRemind);
        }
        return Tools.s();
    }

    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/edit-template", method = RequestMethod.POST)
    public Map<String,Object> editTemplate(@RequestParam(value = "id",required = false)Integer id,
                                           @RequestParam(value = "lessonHead",required = false)String lessonHead,
                                           @RequestParam(value = "lessonName",required = false)String lessonName,
                                           @RequestParam(value = "lessonState",required = false)String lessonState,
                                           @RequestParam(value = "lessonProgress",required = false)String lessonProgress,
                                           @RequestParam(value = "lessonContent",required = false)String lessonContent,
                                           @RequestParam(value = "url",required = false)String url,
                                           @RequestParam(value = "lessonHeadColor",required = false)String lessonHeadColor,
                                           @RequestParam(value = "lessonNameColor",required = false)String lessonNameColor,
                                           @RequestParam(value = "lessonStateColor",required = false)String lessonStateColor,
                                           @RequestParam(value = "lessonProgressColor",required = false)String lessonProgressColor,
                                           @RequestParam(value = "lessonContentColor",required = false)String lessonContentColor){


        HashMap<String, Object> map = new HashMap<>();
        if(lessonHead != null){
            map.put("lessonHead",lessonHead);
        }
        if(lessonName != null){
            map.put("lessonName",lessonName);
        }
        if(lessonState != null){
            map.put("lessonState",lessonState);
        }
        if(lessonProgress != null){
            map.put("lessonProgress",lessonProgress);
        }
        if(lessonContent != null){
            map.put("lessonContent",lessonContent);
        }
        if(lessonHeadColor != null){
            map.put("lessonHeadColor",lessonHeadColor);
        }
        if(lessonNameColor != null){
            map.put("lessonNameColor",lessonNameColor);
        }
        if(lessonStateColor != null){
            map.put("lessonStateColor",lessonStateColor);
        }
        if(lessonProgressColor != null){
            map.put("lessonProgressColor",lessonProgressColor);
        }
        if(lessonContentColor != null){
            map.put("lessonContentColor",lessonContentColor);
        }

        WxTemplate wxTemplate = new WxTemplate();
        wxTemplate.setId(id);
        wxTemplate.setType(PUSH_MESSAGE_TEMPLATE_TYPE);
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
    public Map<String,Object> editCustom(
    		@RequestParam(value = "id",required = false)Integer id,
    		@RequestParam(value = "content",required = false)String content){


        HashMap<String, Object> map = new HashMap<>();


        WxCustom wxCustom = new WxCustom();
        wxCustom.setId(id);
        wxCustom.setType(PUSH_MESSAGE_CUSTOM_TYPE);
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
    public Map<String,Object> previewTemplate(@RequestParam(value = "openId",required = false)String openId,
                                              @RequestParam(value = "lessonHead",required = false)String lessonHead,
                                              @RequestParam(value = "lessonName",required = false)String lessonName,
                                              @RequestParam(value = "lessonState",required = false)String lessonState,
                                              @RequestParam(value = "lessonProgress",required = false)String lessonProgress,
                                              @RequestParam(value = "lessonContent",required = false)String lessonContent,
                                              @RequestParam(value = "url",required = false)String url,
                                              @RequestParam(value = "lessonHeadColor",required = false)String lessonHeadColor,
                                              @RequestParam(value = "lessonNameColor",required = false)String lessonNameColor,
                                              @RequestParam(value = "lessonStateColor",required = false)String lessonStateColor,
                                              @RequestParam(value = "lessonProgressColor",required = false)String lessonProgressColor,
                                              @RequestParam(value = "lessonContentColor",required = false)String lessonContentColor){

    	logger.info("课程提醒预览模板消息start：openId:{}, contentInfo:{}, contentBody:{}, url:{}, contentInfoColor:{}, contentBodyColor:{}", 
				openId);
    	
        Map<String,Object> first = new HashMap<>();
        first.put("value",lessonHead);
        first.put("color",lessonHeadColor);
        Map<String,Object> keyword1 = new HashMap<>();
        keyword1.put("value",lessonName);
        keyword1.put("color",lessonNameColor);
        Map<String,Object> keyword2 = new HashMap<>();
        keyword2.put("value",lessonState);
        keyword2.put("color",lessonStateColor);
        Map<String,Object> keyword3 = new HashMap<>();
        keyword3.put("value",lessonProgress);
        keyword3.put("color",lessonProgressColor);

        Map<String,Object> remark = new HashMap<>();
        remark.put("value",lessonContent);
        remark.put("color",lessonContentColor);
        Map<String,Object> data = new HashMap<>();
        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("keyword3",keyword3);
        data.put("remark",remark);

        Map<String,Object> params = new HashMap<>();
        params.put("touser",openId);
        params.put("template_id",wxService.SEND_MESSAGE_LESSON_TEMPLATE_ID);
        params.put("url",url);
        params.put("data",data);

        String loggerInfo = "send template successful.";
        String loggerErr = "send template failed.";

        logger.info("template_id:{}", wxService.PURCHASE_NOTICE_TEMPLATE_ID);
        wxService.sendToWx(params,wxService.SEND_TEMPLATE+wxService.getAccessToken(),loggerInfo,loggerErr);
        logger.info("课程提醒预览模板消息end.");
        return Tools.s();
    }





    //----------------------------上为课程提醒，下为开课通知-----------------------------------------
    @ResponseBody
    @RequestMapping(value = "/begin-info", method = RequestMethod.POST)
    public Map<String,Object> beginInfo(){

        HashMap<String, Object> mapz = new HashMap<>();

        List<WxTemplate> wxTemplates = squirrelWxTemplateMapper.selectByType(CLASS_BEGINS_TEMPLATE_TYPE);

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
            wxTemplate.setType(CLASS_BEGINS_TEMPLATE_TYPE);
            wxTemplate.setIsOpen(0);
            squirrelWxTemplateMapper.insert(wxTemplate);
            List<WxTemplate> wx1 = squirrelWxTemplateMapper.selectByType(CLASS_BEGINS_TEMPLATE_TYPE);
            mapz.put("template",wx1.get(0));
        }

        List<WxCustom> wxCustoms = squirrelWxCustomMapper.selectByType(CLASS_BEGINS_CUSTOM_TYPE);

        if(wxCustoms.size() == 1){
            WxCustom wxCustom = wxCustoms.get(0);
            mapz.put("custom",wxCustom);
        }else if(wxTemplates.size() > 1){
            return Tools.f("客服消息数量有误");
        }else{//没有就创建
            WxCustom wxCustom = new WxCustom();
            wxCustom.setType(CLASS_BEGINS_CUSTOM_TYPE);
            wxCustom.setIsOpen(0);
            squirrelWxCustomMapper.insert(wxCustom);
            List<WxCustom> wx1 = squirrelWxCustomMapper.selectByType(CLASS_BEGINS_CUSTOM_TYPE);
            mapz.put("custom",wx1.get(0));
        }
        SquirrelLessonRemind remind = squirrelWxTemplateMapper.selectRemindTimeDefault("class-begins");
        mapz.put("remind",remind);

        return Tools.s(mapz);
    }

    @ResponseBody
    @RequestMapping(value = "/edit-begin-remind", method = RequestMethod.POST)
    public Map<String,Object> editBeginTemplate(@RequestParam(value = "id",required = false)Integer id,
                                           @RequestParam(value = "remindRate",required = false)String remindRate,
                                           @RequestParam(value = "firstRemind",required = false)String firstRemind){

        if(id == null){
            squirrelWxTemplateMapper.insertRemindTimeClass(remindRate,firstRemind,"class-begins");
        }else{
            squirrelWxTemplateMapper.updateRemindTimeClass(id, remindRate, firstRemind);
        }

        //修改quartz调度器
        //构造cron表达式
        String cron = "0 ";
        String[] split = firstRemind.split(":");
        if(split[1].equals("00")){
            cron += "0 ";
        }else{
            cron += split[1]+" ";
        }
        if(split[0].equals("00")){
            cron += "0 ";
        }else{
            cron += split[0]+" ";
        }

        cron += "* * ?";

        try {
            jobController.jobreschedule("com.qingclass.squirrel.quartz.ClassBeginsTask","classBeginsMessage",cron);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Tools.s();
    }

    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/edit-begin-template", method = RequestMethod.POST)
    public Map<String,Object> editBeginTemplate(
    		@RequestParam(value = "id",required = false)Integer id,
            @RequestParam(value = "remark",required = false)String remarkCon,
            @RequestParam(value = "url",required = false)String url,
            @RequestParam(value = "remarkColor",required = false)String remarkColor){


        HashMap<String, Object> map = new HashMap<>();
        if(remarkCon != null){
            map.put("remark",remarkCon);
        }
        if(remarkColor != null){
            map.put("remarkColor",remarkColor);
        }


        WxTemplate wxTemplate = new WxTemplate();
        wxTemplate.setId(id);
        wxTemplate.setType(CLASS_BEGINS_TEMPLATE_TYPE);
        wxTemplate.setUrl(url);
        wxTemplate.setContent(JSONObject.toJSONString(map));

        squirrelWxTemplateMapper.updateByPrimaryKey(wxTemplate);

        return Tools.s();
    }

    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/edit-begin-custom", method = RequestMethod.POST)
    public Map<String,Object> editBeginCustom(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "content",required = false)String content){


        HashMap<String, Object> map = new HashMap<>();


        WxCustom wxCustom = new WxCustom();
        wxCustom.setId(id);
        wxCustom.setType(CLASS_BEGINS_CUSTOM_TYPE);
        wxCustom.setContent(content);

        squirrelWxCustomMapper.updateByPrimaryKey(wxCustom);

        return Tools.s();
    }


    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/open-or-close-begin-template", method = RequestMethod.POST)
    public Map<String,Object> openOrCloseBeginTemplate(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "isOpen",required = false)Integer isOpen){

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
    @RequestMapping(value = "/open-or-close-begin-custom", method = RequestMethod.POST)
    public Map<String,Object> openOrCloseBeginCustom(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "isOpen",required = false)Integer isOpen){

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
    @RequestMapping(value = "/preview-begin-custom", method = RequestMethod.POST)
    public Map<String,Object> previewBeginCustom(
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
    @RequestMapping(value = "/preview-begin-template", method = RequestMethod.POST)
    public Map<String,Object> previewBeginTemplate(@RequestParam(value = "openId",required = false)String openId,
                                              @RequestParam(value = "remark",required = false)String remarkCon,
                                              @RequestParam(value = "url",required = false)String url,
                                              @RequestParam(value = "remarkColor",required = false)String remarkColor){

    	logger.info("开课提醒预览模板消息start：openId:{}, contentInfo:{}, contentBody:{}, url:{}, contentInfoColor:{}, contentBodyColor:{}", 
				openId);
    	
        Map<String,Object> courseName = new HashMap<>();
        courseName.put("value","{levelName}");
        courseName.put("color",remarkColor);
        Map<String,Object> date = new HashMap<>();
        date.put("value","{beginDate}");
        date.put("color",remarkColor);
        Map<String,Object> remark = new HashMap<>();
        remark.put("value",remarkCon);
        remark.put("color",remarkColor);
        Map<String,Object> data = new HashMap<>();
        data.put("first",courseName);
        data.put("keyword1",date);
        data.put("remark",remark);

        Map<String,Object> params = new HashMap<>();
        params.put("touser",openId);
        params.put("template_id",wxService.CLASS_BEGINS_TEMPLATE_ID);
        params.put("url",url);
        params.put("data",data);

        String loggerInfo = "send template successful.";
        String loggerErr = "send template failed.";
        
        logger.info("template_id:{}", wxService.CLASS_BEGINS_TEMPLATE_ID);
        wxService.sendToWx(params,wxService.SEND_TEMPLATE+wxService.getAccessToken(),loggerInfo,loggerErr);
        logger.info("开课提醒预览模板消息end.");
        return Tools.s();
    }
    
    //结课提醒start*******************************************************************************
    @ResponseBody
    @RequestMapping(value = "/end-info", method = RequestMethod.POST)
    public Map<String,Object> endInfo(){
        	
		HashMap<String, Object> mapz = new HashMap<>();
		//--------------------
	    List<WxTemplate> wxTemplates = squirrelWxTemplateMapper.selectByType(CLASS_ENDS_TEMPLATE_TYPE);
	
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
	        wxTemplate.setType(CLASS_ENDS_TEMPLATE_TYPE);
	        wxTemplate.setIsOpen(0);
	        squirrelWxTemplateMapper.insert(wxTemplate);
	        List<WxTemplate> wx1 = squirrelWxTemplateMapper.selectByType(CLASS_ENDS_TEMPLATE_TYPE);
	        mapz.put("template",wx1.get(0));
	    }
	
	    List<WxCustom> wxCustoms = squirrelWxCustomMapper.selectByType(CLASS_ENDS_CUSTOM_TYPE);
	
	    if(wxCustoms.size() == 1){
	        WxCustom wxCustom = wxCustoms.get(0);
	        mapz.put("custom",wxCustom);
	    }else if(wxCustoms.size() > 1){
	        return Tools.f("客服消息数量有误");
	    }else{//没有就创建
	        WxCustom wxCustom = new WxCustom();
	        wxCustom.setType(CLASS_ENDS_CUSTOM_TYPE);
	        wxCustom.setIsOpen(0);
	        squirrelWxCustomMapper.insert(wxCustom);
	        List<WxCustom> wx1 = squirrelWxCustomMapper.selectByType(CLASS_ENDS_CUSTOM_TYPE);
	        mapz.put("custom",wx1.get(0));
	    }
	    
	    SquirrelLessonRemind remind = squirrelWxTemplateMapper.selectRemindTimeDefault("class-ends");
	    mapz.put("remind",remind);
        return Tools.s(mapz);
    }
    
    @ResponseBody
    @RequestMapping(value = "/edit-ends-remind", method = RequestMethod.POST)
    public Map<String,Object> editEndTemplate(@RequestParam(value = "id",required = false)Integer id,
                                           @RequestParam(value = "remindRate",required = true)String remindRate,
                                           @RequestParam(value = "firstRemind",required = true)String firstRemind){
        if(id == null){
            squirrelWxTemplateMapper.insertRemindTimeClass(remindRate,firstRemind,"class-ends");
        }else{
            squirrelWxTemplateMapper.updateRemindTimeClass(id, remindRate, firstRemind);
        }

        //修改quartz调度器
        //构造cron表达式
        String cron = "0 ";
        String[] split = firstRemind.split(":");
        if(split[1].equals("00")){
            cron += "0 ";
        }else{
            cron += split[1]+" ";
        }
        if(split[0].equals("00")){
            cron += "0 ";
        }else{
            cron += split[0]+" ";
        }

        cron += "* * ?";

        try {
            jobController.jobreschedule("com.qingclass.squirrel.quartz.ClassEndsTask","classEndsMessage",cron);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Tools.s();
    }
    
    @ResponseBody
    @RequestMapping(value = "/edit-ends-template", method = RequestMethod.POST)
    public Map<String,Object> editEndsTemplate(@RequestParam(value = "id",required = false)Integer id,
    									
                                           @RequestParam(value = "lessonHead",required = false)String lessonHead,
                                           @RequestParam(value = "lessonHeadColor",required = false)String lessonHeadColor,
                                           
                                           @RequestParam(value = "lessonName",required = false)String lessonName,
                                           @RequestParam(value = "lessonNameColor",required = false)String lessonNameColor,
                                           
                                           @RequestParam(value = "lessonEndTime",required = false)String lessonEndTime,
                                           @RequestParam(value = "lessonEndTimeColor",required = false)String lessonEndTimeColor,
                                           
                                           @RequestParam(value = "lessonContent",required = false)String lessonContent,
                                           @RequestParam(value = "lessonContentColor",required = false)String lessonContentColor,
                                           
                                           @RequestParam(value = "url",required = false)String url){


        HashMap<String, Object> map = new HashMap<>();
        if(lessonHead != null){
            map.put("lessonHead",lessonHead);
        }
        if(lessonHeadColor != null){
            map.put("lessonHeadColor",lessonHeadColor);
        }
        
        if(lessonName != null){
            map.put("lessonName",lessonName);
        }
        if(lessonNameColor != null){
            map.put("lessonNameColor",lessonNameColor);
        }
        
        if(lessonEndTime != null){
            map.put("lessonEndTime",lessonEndTime);
        }
        if(lessonEndTimeColor != null){
            map.put("lessonEndTimeColor",lessonEndTimeColor);
        }
        
        if(lessonContent != null){
            map.put("lessonContent",lessonContent);
        }
        if(lessonContentColor != null){
            map.put("lessonContentColor",lessonContentColor);
        }

        WxTemplate wxTemplate = new WxTemplate();
        wxTemplate.setId(id);
        wxTemplate.setType(CLASS_ENDS_TEMPLATE_TYPE);
        wxTemplate.setUrl(url);
        wxTemplate.setContent(JSONObject.toJSONString(map));

        squirrelWxTemplateMapper.updateByPrimaryKey(wxTemplate);

        return Tools.s();
    }

    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/edit-ends-custom", method = RequestMethod.POST)
    public Map<String,Object> editEndsCustom(
    		@RequestParam(value = "id",required = false)Integer id,
    		@RequestParam(value = "content",required = false)String content){

        HashMap<String, Object> map = new HashMap<>();

        WxCustom wxCustom = new WxCustom();
        wxCustom.setId(id);
        wxCustom.setType(CLASS_ENDS_CUSTOM_TYPE);
        wxCustom.setContent(content);

        squirrelWxCustomMapper.updateByPrimaryKey(wxCustom);

        return Tools.s();
    }

   /**
    *
    * */
   @ResponseBody
   @RequestMapping(value = "/preview-ends-template", method = RequestMethod.POST)
   public Map<String,Object> previewEndsTemplate(@RequestParam(value = "openId",required = false)String openId,
		   									 @RequestParam(value = "levelId",required = false)Integer levelId,
		   
										     @RequestParam(value = "lessonHead",required = false)String lessonHead,
								             @RequestParam(value = "lessonHeadColor",required = false)String lessonHeadColor,
								           
								             @RequestParam(value = "lessonName",required = false)String lessonName,
								             @RequestParam(value = "lessonNameColor",required = false)String lessonNameColor,
								           
								             @RequestParam(value = "lessonEndTime",required = false)String lessonEndTime,
								             @RequestParam(value = "lessonEndTimeColor",required = false)String lessonEndTimeColor,
								           
								             @RequestParam(value = "lessonContent",required = false)String lessonContent,
								             @RequestParam(value = "lessonContentColor",required = false)String lessonContentColor,
                                             @RequestParam(value = "url",required = false)String url){

//	   User user = userMapper.selectUserByOpenId(openId, levelId);
	   
	   Calendar rightNow = Calendar.getInstance();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   String rightNowString = sdf.format(rightNow.getTime());
	   lessonEndTime = rightNowString;
	   
       lessonEndTimeColor = "#000000";
       
       Map<String,Object> first = new HashMap<>();
       first.put("value",lessonHead);
       first.put("color",lessonHeadColor);
       
       Map<String,Object> keyword1 = new HashMap<>();
       keyword1.put("value",lessonName);
       keyword1.put("color",lessonNameColor);
       
       Map<String,Object> keyword2 = new HashMap<>();
       keyword2.put("value",lessonEndTime);//user.getEndClassTime()
       keyword2.put("color",lessonEndTimeColor);

       Map<String,Object> remark = new HashMap<>();
       remark.put("value",lessonContent);
       remark.put("color",lessonContentColor);
       
       Map<String,Object> data = new HashMap<>();
       data.put("first",first);
       data.put("keyword1",keyword1);
       data.put("keyword2",keyword2);
       data.put("remark",remark);

       Map<String,Object> params = new HashMap<>();
       params.put("touser",openId);
       params.put("template_id",wxService.CLASS_ENDS_TEMPLATE_ID);
       params.put("url",url);
       params.put("data",data);

       String loggerInfo = "send template successful.";
       String loggerErr = "send template failed.";

       wxService.sendToWx(params,wxService.SEND_TEMPLATE+wxService.getAccessToken(),loggerInfo,loggerErr);

       return Tools.s();
   }
   //结课提醒end*******************************************************************************

}
