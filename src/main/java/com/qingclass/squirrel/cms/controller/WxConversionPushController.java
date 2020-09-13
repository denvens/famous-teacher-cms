package com.qingclass.squirrel.cms.controller;

import com.qingclass.squirrel.cms.Service.WxService;
import com.qingclass.squirrel.cms.entity.cms.ConversionPush;
import com.qingclass.squirrel.cms.entity.wechat.WxCustom;
import com.qingclass.squirrel.cms.mapper.cms.ConversionPushMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxCustomMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/wx-conversion-push")
public class WxConversionPushController {

    @Autowired
    ConversionPushMapper conversionPushMapper;
    @Autowired
    SquirrelWxCustomMapper squirrelWxCustomMapper;
    @Autowired
    WxService wxService;

    private final String CUSTOM_TYPE = "conversion-push";

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Map<String,Object> list(){


        List<ConversionPush> conversionPushes = conversionPushMapper.selectAll();

        return Tools.s(conversionPushes);
    }

    @ResponseBody
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Map<String,Object> info(@RequestParam(value = "id",required = false)Integer id){


        ConversionPush conversionPush = conversionPushMapper.selectByPrimaryKey(id);
        List<WxCustom> wxCustoms = squirrelWxCustomMapper.selectByPrimaryKey(conversionPush.getCustomId());
        conversionPush.setCustomContent(wxCustoms.get(0).getContent());

        return Tools.s(conversionPush);
    }

    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Map<String,Object> insert(@RequestParam(value = "levelId",required = false)Integer levelId, @RequestParam(value = "pushTime",required = false)String pushTime,
                                     @RequestParam(value = "scope",required = false)Integer scope,
                                     @RequestParam(value = "customContent",required = false)String customContent,@RequestParam(value = "isOpen",required = false)Integer isOpen){


        WxCustom wxCustom = new WxCustom();
        wxCustom.setType(CUSTOM_TYPE);
        wxCustom.setIsOpen(1);
        wxCustom.setContent(customContent);

        squirrelWxCustomMapper.insert(wxCustom);

        ConversionPush conversionPush = new ConversionPush();
        conversionPush.setLevelId(levelId);
        conversionPush.setCustomId(wxCustom.getId());
        conversionPush.setIsOpen(isOpen);
        conversionPush.setScope(scope);
        conversionPush.setPushTime(pushTime);

        conversionPushMapper.insert(conversionPush);

        return Tools.s();
    }

    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Map<String,Object> edit(@RequestParam(value = "id",required = false)Integer id,
                                   @RequestParam(value = "customId",required = false)Integer customId,
                                   @RequestParam(value = "levelId",required = false)Integer levelId,
                                   @RequestParam(value = "scope",required = false)Integer scope,
                                   @RequestParam(value = "pushTime",required = false)String pushTime,
                                     @RequestParam(value = "customContent",required = false)String customContent,
                                   @RequestParam(value = "isOpen",required = false)Integer isOpen){


        WxCustom wxCustom = new WxCustom();
        wxCustom.setId(customId);
        wxCustom.setContent(customContent);
        squirrelWxCustomMapper.updateByPrimaryKey(wxCustom);

        ConversionPush conversionPush = new ConversionPush();
        conversionPush.setId(id);
        if(scope == 1){
            conversionPush.setLevelId(null);
        }else{
            conversionPush.setLevelId(levelId);
        }

        conversionPush.setCustomId(customId);
        conversionPush.setIsOpen(isOpen);
        conversionPush.setScope(scope);
        conversionPush.setPushTime(pushTime);

        conversionPushMapper.update(conversionPush);

        return Tools.s();
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Map<String,Object> delete(@RequestParam(value = "id",required = false)Integer id){

        conversionPushMapper.delete(id);

        return Tools.s();
    }

    @ResponseBody
    @RequestMapping(value = "edit-status", method = RequestMethod.POST)
    public Map<String,Object> editStatus(@RequestParam(value = "id",required = false)Integer id,@RequestParam(value = "isOpen",required = false)Integer isOpen){


        ConversionPush conversionPush = new ConversionPush();

        conversionPush.setId(id);
        conversionPush.setIsOpen(isOpen);

        conversionPushMapper.updateStatus(conversionPush);
        return Tools.s();
    }

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

}
