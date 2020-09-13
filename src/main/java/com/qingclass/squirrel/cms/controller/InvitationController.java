package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.WxService;
import com.qingclass.squirrel.cms.constant.InvitationTypeEnum;
import com.qingclass.squirrel.cms.entity.cms.SquirrelInvitationSetting;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLesson;
import com.qingclass.squirrel.cms.entity.user.InvitationRecord;
import com.qingclass.squirrel.cms.entity.wechat.WxCustom;
import com.qingclass.squirrel.cms.entity.wechat.WxShare;
import com.qingclass.squirrel.cms.entity.wechat.WxTemplate;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelInvitationSettingMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxCustomMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxShareMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxTemplateMapper;
import com.qingclass.squirrel.cms.mapper.user.PurchaseMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import com.qingclass.squirrel.cms.utils.Verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Autowired
    private SquirrelInvitationSettingMapper squirrelInvitationSettingMapper;
    @Autowired
    private SquirrelWxShareMapper squirrelWxShareMapper;
    @Autowired
    private SquirrelWxTemplateMapper squirrelWxTemplateMapper;
    @Autowired
    private SquirrelWxCustomMapper squirrelWxCustomMapper;
    @Autowired
    WxService wxService;
    @Autowired
    private PurchaseMapper purchaseMapper;

    private final String INVITATION_CUSTOM_TYPE = "invitation-custom";
    private final String INVITATION_TEMPLATE_TYPE = "invitation-template";

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Map<String,Object> list(@RequestParam(value = "levelId",required = false)Integer levelId,
    							   @RequestParam(value = "invitationType",required = false)Integer invitationType,	
                                   @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                   @RequestParam(value = "pageSize",required = false)Integer pageSize){

        pageNo = (pageNo - 1) * pageSize;
        SquirrelInvitationSetting invitationSetting = new SquirrelInvitationSetting();
        invitationSetting.setPageNo(pageNo);
        invitationSetting.setPageSize(pageSize);
        if(levelId != null){
        	invitationSetting.setLevelId(levelId);
        }
        if(invitationType != null){
        	invitationSetting.setInvitationType(invitationType);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SquirrelInvitationSetting> list = squirrelInvitationSettingMapper.list(invitationSetting);
        for(SquirrelInvitationSetting sis : list){
            sis.setCreatedAt(sdf.format(sis.getCreatedTime()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",squirrelInvitationSettingMapper.listCount(invitationSetting));
        return Tools.s(map);
    }
    @ResponseBody
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Map<String,Object> info(@RequestParam(value = "id",required = true)Integer id){
        Map<String,Object> map = new HashMap<>();
        SquirrelInvitationSetting squirrelInvitationSetting = squirrelInvitationSettingMapper.selectByPrimaryKey(id);
        map.put("invitation-setting",squirrelInvitationSetting);
        List<WxTemplate> wxTemplates = squirrelWxTemplateMapper.selectByPrimaryKey(squirrelInvitationSetting.getTemplateId());
        if(wxTemplates != null && wxTemplates.size() > 0){
            if(wxTemplates.get(0).getContent() != null){
                Map<String,Object> map1 = JSONObject.parseObject(wxTemplates.get(0).getContent());
                wxTemplates.get(0).setContentMap(map1);
                wxTemplates.get(0).setContent(null);
            }
            map.put("template",wxTemplates.get(0));
        }else{
            map.put("template",null);
        }

        List<WxCustom> wxCustoms = squirrelWxCustomMapper.selectByPrimaryKey(squirrelInvitationSetting.getCustomId());
        if(wxCustoms != null && wxCustoms.size() > 0){
            map.put("custom",wxCustoms.get(0));
        }else{
            map.put("custom",null);
        }

        WxShare wxShare = squirrelWxShareMapper.selectByPrimaryKey(squirrelInvitationSetting.getShareId());
        map.put("share-page",wxShare);


        return Tools.s(map);
    }

    @ResponseBody
    @RequestMapping(value = "edit-setting", method = RequestMethod.POST)
    public Map<String,Object> editSetting(@RequestParam(value = "id",required = false)Integer id, 
    		@RequestParam(value = "levelId",required = false)Integer levelId,
            @RequestParam(value = "img",required = false)String img, 
            @RequestParam(value = "rule",required = false)String rule,
            @RequestParam(value = "validDays",required = false)Integer validDays,
            @RequestParam(value = "invitationType",required = true)Integer invitationType,
            @RequestParam(value = "bonusAmount",required = false)Double bonusAmount,
            @RequestParam(value = "bonusImg",required = false)String bonusImg,
            @RequestParam(value = "offerAmount",required = false)Double offerAmount,
            @RequestParam(value = "offerImg",required = false)String offerImg){
    	
        SquirrelInvitationSetting invitation = new SquirrelInvitationSetting();
        invitation.setLevelId(levelId);
        invitation.setImg(img);
        invitation.setRule(rule);
        invitation.setInvitationType(invitationType);
        if(InvitationTypeEnum.InvitationCoupon.getKey().equals(invitationType.toString())){
        	invitation.setValidDays(validDays);
        }
        if(InvitationTypeEnum.InvitationCash.getKey().equals(invitationType.toString())){
        	BigDecimal ba = new BigDecimal(bonusAmount);
            invitation.setBonusAmount(ba);
            invitation.setBonusImg(bonusImg);
            
            BigDecimal oa = new BigDecimal(offerAmount);
            invitation.setOfferAmount(oa);
            invitation.setOfferImg(offerImg);
        }
        
        if(id == null){
            squirrelInvitationSettingMapper.insert(invitation);
        }else{
        	invitation.setId(id);
            squirrelInvitationSettingMapper.update(invitation);
        }

        return Tools.s(invitation.getId());
    }

    @ResponseBody
    @RequestMapping(value = "edit-setting-status", method = RequestMethod.POST)
    public Map<String,Object> editSettingStatus(
    		@RequestParam(value = "id",required = true)Integer id,
    		@RequestParam(value = "isOpen",required = true)Integer isOpen){

        SquirrelInvitationSetting squirrelInvitationSetting = new SquirrelInvitationSetting();


        squirrelInvitationSetting.setId(id);
        squirrelInvitationSetting.setIsOpen(isOpen);
        squirrelInvitationSettingMapper.editStatus(squirrelInvitationSetting);

        return Tools.s(squirrelInvitationSetting.getId());
    }

    @ResponseBody
    @RequestMapping(value = "delete-setting", method = RequestMethod.POST)
    public Map<String,Object> editSetting(@RequestParam(value = "id",required = false)Integer id){


        squirrelInvitationSettingMapper.delete(id);

        return Tools.s();
    }

    @ResponseBody
    @RequestMapping(value = "edit-share-page", method = RequestMethod.POST)
    public Map<String,Object> editSharePage(@RequestParam(value = "id",required = false)Integer id,//id为邀请表的id
                                            @RequestParam(value = "spaceTitle",required = false)String spaceTitle,
                                            @RequestParam(value = "freTitle",required = false)String freTitle, @RequestParam(value = "content",required = false)String content,
                                            @RequestParam(value = "img",required = false)String img){

        SquirrelInvitationSetting sis = squirrelInvitationSettingMapper.selectByPrimaryKey(id);


        WxShare wxShare = new WxShare();
        wxShare.setSpaceTitle(spaceTitle);
        wxShare.setFreTitle(freTitle);
        wxShare.setContent(content);
        wxShare.setImg(img);

        SquirrelInvitationSetting squirrelInvitationSetting = new SquirrelInvitationSetting();
        squirrelInvitationSetting.setId(id);


        if(sis.getShareId() == null || sis.getShareId() == 0){//不存在新建
            squirrelWxShareMapper.insert(wxShare);
            squirrelInvitationSetting.setShareId(wxShare.getId());
            squirrelInvitationSettingMapper.update(squirrelInvitationSetting);
        }else{//存在更新内容
            wxShare.setId(sis.getShareId());
            squirrelWxShareMapper.updateByPrimaryKey(wxShare);
        }
        return Tools.s(wxShare.getId());
    }

    @ResponseBody
    @RequestMapping(value = "edit-send-message-template", method = RequestMethod.POST)
    public Map<String,Object> editSendMessageTemplate(@RequestParam(value = "id",required = false)Integer id,//id为邀请表的id
                                              @RequestParam(value = "title",required = false)String title,@RequestParam(value = "remark",required = false)String remark,
                                              @RequestParam(value = "titleColor",required = false)String titleColor,@RequestParam(value = "remarkColor",required = false)String remarkColor,
                                                      @RequestParam(value = "url",required = false)String url){
        SquirrelInvitationSetting sis = squirrelInvitationSettingMapper.selectByPrimaryKey(id);
        WxTemplate wxTemplate = new WxTemplate();
        HashMap<String, Object> map = new HashMap<>();
        if(title != null){
            map.put("title",title);
        }
        if(remark != null){
            map.put("remark",remark);
        }
        if(titleColor != null){
            map.put("titleColor",titleColor);
        }
        if(remarkColor != null){
            map.put("remarkColor",remarkColor);
        }

        wxTemplate.setUrl(url);
        wxTemplate.setType(INVITATION_TEMPLATE_TYPE);
        wxTemplate.setContent(JSONObject.toJSONString(map));

        if(sis.getTemplateId() == null || sis.getTemplateId() == 0){
            wxTemplate.setIsOpen(0);
            squirrelWxTemplateMapper.insert(wxTemplate);
            sis.setTemplateId(wxTemplate.getId());
            squirrelInvitationSettingMapper.update(sis);
        }else{
            wxTemplate.setId(sis.getTemplateId());
            squirrelWxTemplateMapper.updateByPrimaryKey(wxTemplate);
        }

        return Tools.s(wxTemplate.getId());
    }

    @ResponseBody
    @RequestMapping(value = "edit-send-message-custom", method = RequestMethod.POST)
    public Map<String,Object> editSendMessageCustom(@RequestParam(value = "id",required = false)Integer id,//id为邀请表的id
                                              @RequestParam(value = "content",required = false)String content){

        SquirrelInvitationSetting sis = squirrelInvitationSettingMapper.selectByPrimaryKey(id);
        WxCustom wxCustom = new WxCustom();

        wxCustom.setType(INVITATION_CUSTOM_TYPE);
        wxCustom.setContent(content);
        if(sis.getCustomId() == null || sis.getCustomId() == 0){
            squirrelWxCustomMapper.insert(wxCustom);
            sis.setCustomId(wxCustom.getId());
            squirrelInvitationSettingMapper.update(sis);
        }else{
            wxCustom.setId(sis.getCustomId());
            squirrelWxCustomMapper.updateByPrimaryKey(wxCustom);
        }

        return Tools.s(wxCustom.getId());

    }

    /**
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/open-or-close-template", method = RequestMethod.POST)
    public Map<String,Object> openOrCloseTemplate(
    		@RequestParam(value = "id",required = true)Integer id,
    		@RequestParam(value = "isOpen",required = true)Integer isOpen){

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
    public Map<String,Object> openOrCloseCustom(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "isOpen",required = false)Integer isOpen){

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
    public Map<String,Object> previewCustom(@RequestParam(value = "openId",required = false)String openId,@RequestParam(value = "content",required = false)String content){

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
    public Map<String,Object> previewBeginTemplate(@RequestParam(value = "openId",required = false)String openId,
                                                   @RequestParam(value = "url",required = false)String url,
                                                   @RequestParam(value = "title",required = false)String title,@RequestParam(value = "remark",required = false)String remarkCon,
                                                   @RequestParam(value = "titleColor",required = false)String titleColor,@RequestParam(value = "remarkColor",required = false)String remarkColor
                                                   ){

        Map<String,Object> first = new HashMap<>();
        first.put("value",title);
        first.put("color",titleColor);
        Map<String,Object> keyword1 = new HashMap<>();
        keyword1.put("value","{nickName}");
        keyword1.put("color",remarkColor);
        Map<String,Object> keyword2 = new HashMap<>();
        keyword2.put("value","{datetime}");
        keyword2.put("color",remarkColor);
        Map<String,Object> remark = new HashMap<>();
        remark.put("value",remarkCon);
        remark.put("color",remarkColor);
        Map<String,Object> data = new HashMap<>();
        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("remark",remark);

        Map<String,Object> params = new HashMap<>();
        params.put("touser",openId);
        params.put("template_id",wxService.INVITATION_TEMPLATE_ID);
        params.put("url",url);
        params.put("data",data);

        String loggerInfo = "send template successful.";
        String loggerErr = "send template failed.";

        wxService.sendToWx(params,wxService.SEND_TEMPLATE+wxService.getAccessToken(),loggerInfo,loggerErr);

        return Tools.s();
    }

    @ResponseBody
    @RequestMapping(value = "record-list", method = RequestMethod.POST)
    public Map<String,Object> recordList(
    		@RequestParam(value = "invitationType",required = true)Integer invitationType,
    		@RequestParam(value = "levelId",required = false)Integer levelId,
    		@RequestParam(value = "invitationParams",required = false)String invitationParams,
            @RequestParam(value = "purchaseParams",required = false)String purchaseParams,
            @RequestParam(value = "createdAt",required = false)String createdAt,
            @RequestParam(value = "pageNo",required = false)Integer pageNo,
            @RequestParam(value = "pageSize",required = false)Integer pageSize) throws ParseException {

        pageNo = (pageNo - 1) * pageSize;
        InvitationRecord invitationRecord = new InvitationRecord();
        invitationRecord.setPageNo(pageNo);
        invitationRecord.setPageSize(pageSize);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(levelId != null && !levelId.equals("")){
            invitationRecord.setLevelId(levelId);
        }
        if(invitationParams != null && !invitationParams.equals("")){
            invitationRecord.setInvitationParams(invitationParams);
        }
        if(purchaseParams != null && !purchaseParams.equals("")){
            invitationRecord.setPurchaseParams(purchaseParams);
        }
        if(createdAt != null && !createdAt.equals("")){
            invitationRecord.setCreatedAt(createdAt);
            String[] split = createdAt.split(",");
            invitationRecord.setCreatedAtA(sdf.parse(split[0]));
            invitationRecord.setCreatedAtB(sdf.parse(split[1]));
        }

        if(InvitationTypeEnum.InvitationCoupon.getKey().equals(invitationType.toString())){
        	invitationRecord.setInvitationType(invitationType);
        }
        if(InvitationTypeEnum.InvitationCash.getKey().equals(invitationType.toString())){
        	invitationRecord.setInvitationType(invitationType);
        }
        List<InvitationRecord> invitationRecords = purchaseMapper.selectInvitationRecords(invitationRecord);
        for(InvitationRecord ir : invitationRecords){
            ir.setCreatedAt(sdf.format(ir.getCreatedAtA()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",invitationRecords);

        map.put("total",purchaseMapper.selectInvitationRecordsCount(invitationRecord));
        return Tools.s(map);
    }
}
