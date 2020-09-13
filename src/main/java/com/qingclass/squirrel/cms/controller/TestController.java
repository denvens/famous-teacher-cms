package com.qingclass.squirrel.cms.controller;


import com.qingclass.squirrel.cms.entity.user.MongoUser;
import com.qingclass.squirrel.cms.mapper.user.UserMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.query.Query;


import java.util.Map;

@RestController
@RequestMapping("/098F6BCD4621D373CADE4E832627B4F6")
public class TestController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MongoTemplate mongoTemplate;

    @ResponseBody
    @GetMapping(value="/del")
    public Map<String,Object> del(@RequestParam("openId")String openId){
        userMapper.deletePurchase(openId);
        userMapper.deleteRemind(openId);
        Query query = new Query(Criteria.where("_id").is(openId));

        mongoTemplate.remove(query, MongoUser.class);

        return Tools.s();
    }
}
