package com.qingclass.squirrel.cms.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Component
public class WxUtil {
    // 临时二维码
    private final static String QR_SCENE = "QR_SCENE";
    // 永久二维码
    private final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
    // 永久二维码(字符串)
    private final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";
    // 创建二维码
    private String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    // 通过ticket换取二维码
    private String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 创建临时带参数二维码
     * @param accessToken
     * @expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     * @param sceneId 场景Id
     * @return
     */
    public Map createTempQr(String accessToken, String expireSeconds, int sceneId) {
        RestTemplate rest = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken ;
        // 参数：{"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
        Map<String,Integer> intMap = new HashMap<>();
        intMap.put("scene_id",sceneId);
        Map<String,Map<String,Integer>> mapMap = new HashMap<>();
        mapMap.put("scene", intMap);
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("expire_seconds", expireSeconds);
        paramsMap.put("action_name", QR_SCENE);
        paramsMap.put("action_info", mapMap);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(paramsMap, headers);
        Map result = null;
        try {
            ResponseEntity<Map> entity = rest.exchange(url, HttpMethod.POST, requestEntity,Map.class, new Object[0]);
            logger.info("调用生成微信临时二维码URL接口返回结果:" + entity.getBody());
            result = (Map) entity.getBody();
        } catch (Exception e) {
            logger.error("调用生成微信临时二维码URL接口异常",e);
        }
        if(result != null){
            return result;
        }
        return null;
    }


    /**
     * 创建永久二维码(数字)
     * @param sceneId 场景id
     * @param accessToken
     * @return
     */
    public Map createForeverQr(Integer sceneId,String accessToken ) {
        RestTemplate rest = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken ;
        // 参数：{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": sceneStr}}}
        Map<String,Object> param = new HashMap<>();
        param.put("action_name", "QR_LIMIT_STR_SCENE");
        Map<String,Object> action = new HashMap<>();
        Map<String,Object> scene = new HashMap<>();
        scene.put("scene_id", sceneId);
        action.put("scene", scene);
        param.put("action_info", action);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        Map result = null;
        try {
            ResponseEntity<Map> entity = rest.exchange(url, HttpMethod.POST, requestEntity,Map.class, new Object[0]);
            logger.info("调用生成微信永久二维码URL接口返回结果:" + entity.getBody());
            result = (Map) entity.getBody();
        } catch (Exception e) {
            logger.error("调用生成微信永久二维码URL接口异常",e);
        }
        if(result != null){
            return result;
        }
        return null;
    }


    /**
     * 创建永久二维码(字符串)
     * @param scenes 场景值
     * @param accessToken
     * @return
     */
    public Map createForeverStrQr(Map<String,Object> scenes,String accessToken ) {
        RestTemplate rest = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken ;
        // 参数：{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": sceneStr}}}
        Map<String,Object> param = new HashMap<>();
        param.put("action_name", "QR_LIMIT_STR_SCENE");
        Map<String,Object> action = new HashMap<>();
        Map<String,Object> scene = new HashMap<>();
        scene.put("scene_str", JSONObject.toJSONString(scenes));
        action.put("scene", scene);
        param.put("action_info", action);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        Map result = null;
        try {
            ResponseEntity<Map> entity = rest.exchange(url, HttpMethod.POST, requestEntity,Map.class, new Object[0]);
            logger.info("调用生成微信永久二维码URL接口返回结果:" + entity.getBody());
            result = (Map) entity.getBody();
        } catch (Exception e) {
            logger.error("调用生成微信永久二维码URL接口异常",e);
        }
        if(result != null){
            return result;
        }
        return null;
    }



}
