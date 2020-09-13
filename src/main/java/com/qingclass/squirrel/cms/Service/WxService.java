package com.qingclass.squirrel.cms.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.util.Map;

/**
 * 处理微信公众消息
 * */
@Service
public class WxService {
    public final String SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="; //推送模版消息
    public final String SEND_CUSTOM = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
    @Value("${purchase.notice.template.id}")
    public String PURCHASE_NOTICE_TEMPLATE_ID;
    @Value("${send.message.lesson.template.id}")
    public String SEND_MESSAGE_LESSON_TEMPLATE_ID;
    @Value("${class.begins.template.id}")
    public String CLASS_BEGINS_TEMPLATE_ID;
    @Value("${class.ends.template.id}")
    public String CLASS_ENDS_TEMPLATE_ID;
    @Value("${finish.notice.template.id}")
    public String FINISH_NOTICE_TEMPLATE_ID;
    
    @Value("${invitation.template.id}")
    public String INVITATION_TEMPLATE_ID;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 取token
     * */
    public String getAccessToken(){
        return stringRedisTemplate.opsForValue().get("msyb_access_token")+"";
    }


    /**
     * 调用微信接口
     * */
    public Map<String,Object> sendToWx(Map<String,Object> paramsMap, String url, String loggerInfo, String loggerErr){
        logger.info("paramsMap === "+paramsMap);
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();


        HttpEntity requestEntity = new HttpEntity(paramsMap, headers);
        Map result = null;
        try {
            ResponseEntity<Map> entity = rest.exchange(url, HttpMethod.POST, requestEntity,Map.class, new Object[0]);
            logger.info("发模板消息参数:paramsMap:{}",paramsMap);
            
            logger.info(loggerInfo);
            result = (Map) entity.getBody();
            logger.info(result.toString());
        } catch (Exception e) {
            logger.error(loggerErr);
            logger.error(result.toString());
        }
        if(result != null){
            return result;
        }
        return null;
    }


}
