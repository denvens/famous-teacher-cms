package com.qingclass.squirrel.cms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test")
public class TestPingController {

	@RequestMapping("test")
    public String test(){
        return "success";
    }

}