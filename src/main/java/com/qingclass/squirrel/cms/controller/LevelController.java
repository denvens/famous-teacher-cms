package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelLevelService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/level")
public class LevelController {

	@Autowired
    private SquirrelLevelService levelService;

	@ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public String list(@RequestParam(name = "projectId", required = false)Integer projectId){

		SquirrelLevel squirrelLevel = new SquirrelLevel();
		squirrelLevel.setSubjectId(projectId);

		RequestInfo info = levelService.list(squirrelLevel);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("levelList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);

	    return jsonObject.toJSONString();
    }

    @ResponseBody
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@RequestParam(name = "name", required = false)String name, 
						 @RequestParam(name = "subjectId", required = false)Integer subjectId,
						 @RequestParam(name = "minWord", required = false)Integer minWord, 
						 @RequestParam(name = "maxWord", required = false)Integer maxWord,
						 @RequestParam(name = "isOpen", required = false)Integer isOpen,
						 @RequestParam(name = "image", required = false)String image,
						 @RequestParam(name = "introduction", required = false)String introduction,
						 @RequestParam(name = "buySite", required = false)String buySite,
						 @RequestParam(name = "returnFeeDay", required = false)Integer returnFeeDay,
						 @RequestParam(name = "lessonDay", required = false)Integer lessonDay){


		SquirrelLevel squirrelLevel = new SquirrelLevel();
		squirrelLevel.setName(name);
		squirrelLevel.setSubjectId(subjectId);
		squirrelLevel.setMinWord(1);
		squirrelLevel.setMaxWord(100);
		squirrelLevel.setIsOpen(isOpen);
		squirrelLevel.setImage(image);
		squirrelLevel.setIntroduction(introduction);
		squirrelLevel.setBuySite(buySite);
		squirrelLevel.setReturnFeeDay(returnFeeDay);
		squirrelLevel.setLessonDay(lessonDay);
		RequestInfo info = levelService.insert(squirrelLevel);
		return getInfo(info);
	}

	@ResponseBody
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public String edit(@RequestParam(name = "id",required = false)Integer id, 
					   @RequestParam(name = "name",required = false)String name,
					   @RequestParam(name = "minWord", required = false)Integer minWord, 
					   @RequestParam(name = "maxWord", required = false)Integer maxWord,
					   @RequestParam(name = "isOpen", required = false)Integer isOpen,
					   @RequestParam(name = "image", required = false)String image,
					   @RequestParam(name = "introduction", required = false)String introduction,
					   @RequestParam(name = "buySite", required = false)String buySite,
					   @RequestParam(name = "returnFeeDay", required = false)Integer returnFeeDay,
					   @RequestParam(name = "lessonDay", required = false)Integer lessonDay){
		SquirrelLevel squirrelLevel = new SquirrelLevel();
		squirrelLevel.setName(name);
		squirrelLevel.setId(id);
		squirrelLevel.setMinWord(1);
		squirrelLevel.setMaxWord(100);
		squirrelLevel.setIsOpen(isOpen);
		squirrelLevel.setImage(image);
		squirrelLevel.setIntroduction(introduction);
		squirrelLevel.setBuySite(buySite);
		squirrelLevel.setReturnFeeDay(returnFeeDay);
		squirrelLevel.setLessonDay(lessonDay);
		RequestInfo info = levelService.update(squirrelLevel);
		return getInfo(info);

	}

	@ResponseBody
	@RequestMapping(value="/info", method = RequestMethod.POST)
	public String info(@RequestParam(name = "id",required = false)Integer id){

		RequestInfo info = levelService.selectByPrimaryKey(id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();
	}


	/**
	 *
	 * */
	private String getInfo(RequestInfo info) {


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();
	}


}
