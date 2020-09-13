package com.qingclass.squirrel.cms.controller;

        import com.alibaba.fastjson.JSONObject;
        import com.qingclass.squirrel.cms.Service.UserService;
        import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
        import com.qingclass.squirrel.cms.entity.user.User;
        import com.qingclass.squirrel.cms.mapper.cms.BigbayMapper;
        import com.qingclass.squirrel.cms.mapper.user.UserMapper;
        import com.qingclass.squirrel.cms.utils.MD5Util;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/payment")
public class WechatPayController {

    @Autowired
    BigbayMapper bigbayMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;


    @ResponseBody
    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public String paymentCallBack(@RequestParam(value = "bigbayAppId",required = false)String bigbayAppId,
                                  @RequestParam(value = "content",required = false)String content,
                                  @RequestParam(value = "random",required = false)String random,
                                  @RequestParam(value = "timestamp",required = false)String timestamp,
                                  @RequestParam(value = "signature",required = false)String signature){


        //获取SignKey
        String bigbaySignKey = bigbayMapper.selectKeyByAppId(bigbayAppId);

        //签名校验
        StringBuffer sb = new StringBuffer();
        sb.append("bigbayAppId="+bigbayAppId).
                append("&").
                append("content="+content).
                append("&").
                append("random="+random).
                append("&").
                append("timestamp="+timestamp).
                append("&").
                append("key="+bigbaySignKey);

        String crypt = MD5Util.crypt(sb.toString());
        System.out.println(crypt);
        JSONObject jsonObject = new JSONObject();
        if(crypt.equals(signature)){
        }else{
            jsonObject.put("success",false);
            return jsonObject.toJSONString();
        }

        RequestInfo info = userService.insertUserLevel(content);
        jsonObject.put("success",info.getSuccess());
        return jsonObject.toJSONString();
    }



}
