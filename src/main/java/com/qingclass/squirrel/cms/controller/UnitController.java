package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelLessonService;
import com.qingclass.squirrel.cms.Service.SquirrelUnitService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/unit")
public class UnitController {

	@Autowired
	SquirrelUnitService squirrelUnitService;
	@Autowired
	SquirrelLessonService squirrelLessonService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@RequestParam(value = "lessonId", required = false)Integer lessonId){


		SquirrelUnit squirrelUnit = new SquirrelUnit();
		squirrelUnit.setLessonId(lessonId);


		RequestInfo info = squirrelUnitService.selectBy(squirrelUnit);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("unitList",objects);
		jsonObject1.put("orderMax",info.getOrderMax());

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/listAll", method = RequestMethod.POST)
	public String listAll(){

		RequestInfo info = squirrelUnitService.selectAll();

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("unitList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String info(@RequestParam(name = "id", required = false)Integer id){

		RequestInfo info = squirrelUnitService.selectByPrimaryKey(id);

		JSONObject jsonObject = new JSONObject();

		JSONArray objects = new JSONArray();

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(HttpServletRequest httpServletRequest, @RequestParam(name = "name",required = false)String name, @RequestParam(name = "lessonId", required = false)Integer lessonId,
						 @RequestParam(name = "order",required = false)Integer order){
		SquirrelUnit squirrelUnit = new SquirrelUnit();
		squirrelUnit.setName(name);
		squirrelUnit.setLessonId(lessonId);
		squirrelUnit.setOrder(order);

		RequestInfo info = squirrelUnitService.insert(squirrelUnit);

		int id = (int)info.getDataObject();

		httpServletRequest.setAttribute("id",id);
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@RequestParam(name = "id", required = false)Integer id, @RequestParam(name = "name", required = false)String name,
					   @RequestParam(name = "order",required = false)Integer order){
		SquirrelUnit squirrelUnit = new SquirrelUnit();
		squirrelUnit.setName(name);
		squirrelUnit.setId(id);
		squirrelUnit.setOrder(order);

		RequestInfo info = squirrelUnitService.update(squirrelUnit);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam(name = "id",required = false)Integer id){
		RequestInfo info = squirrelUnitService.delete(id);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();
	}

}
