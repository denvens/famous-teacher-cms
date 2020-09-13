package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelSubjectService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
    private SquirrelSubjectService subjectService;

	@ResponseBody
	@RequestMapping(value="/list",  method = RequestMethod.POST)
    public String list(){

	    RequestInfo info = subjectService.selectAll();

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("subjectList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);

	    return jsonObject.toJSONString();
    }

	

}
