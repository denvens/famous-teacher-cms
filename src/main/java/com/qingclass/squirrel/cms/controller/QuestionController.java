package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelQuestionService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelQuestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	SquirrelQuestionService squirrelQuestionService;

	@ResponseBody
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public String list(
			@RequestParam(value = "lessonId", required = true)Integer lessonId,
			@RequestParam(value = "pageNo",required = false)Integer pageNo,
            @RequestParam(value = "pageSize",required = false)Integer pageSize){
		if(pageNo==null){pageNo=new Integer(1);}
    	if(pageSize==null){pageSize=new Integer(20);}
    	pageNo = (pageNo - 1) * pageSize;
		SquirrelQuestion squirrelQuestion = new SquirrelQuestion();
		squirrelQuestion.setLessonId(lessonId);
		squirrelQuestion.setPageNo(pageNo);
		squirrelQuestion.setPageSize(pageSize);
		RequestInfo info = squirrelQuestionService.selectBy(squirrelQuestion);

		JSONObject returnObject = new JSONObject();
		JSONObject dateObject = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		dateObject.put("questionList",objects);
		dateObject.put("count",squirrelQuestionService.getCount(squirrelQuestion));
		dateObject.put("orderMax",info.getOrderMax());

		returnObject.put("denied",info.getDenied());
		returnObject.put("success",info.getSuccess());
		returnObject.put("data",dateObject);

		return returnObject.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value="/listAll", method = RequestMethod.POST)
	public String listAll(){
		RequestInfo info = squirrelQuestionService.selectAll();

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("questionList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);

		return jsonObject.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value="/info", method = RequestMethod.POST)
	public String info(@RequestParam(value = "id",required = true)Integer id){
		RequestInfo info = squirrelQuestionService.selectByPrimaryKey(id);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(HttpServletRequest httpServletRequest, 
			@RequestParam(value = "lessonId",required = true)Integer lessonId, 
			@RequestParam(value = "questionType",required = true)String questionType,
			@RequestParam(value = "questionData",required = true)String questionData, 
			@RequestParam(value = "order",required = false)Integer order){
		SquirrelQuestion squirrelQuestion = new SquirrelQuestion();
		squirrelQuestion.setLessonId(lessonId);
		squirrelQuestion.setQuestionType(questionType);
		squirrelQuestion.setQuestionData(questionData);
		squirrelQuestion.setOrder(0);
		squirrelQuestion.setQuestionKey(UUID.randomUUID().toString());

		RequestInfo info = squirrelQuestionService.insert(squirrelQuestion);

		int id = (int)info.getDataObject();
		httpServletRequest.setAttribute("id",id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();
	}


	@ResponseBody
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public String edit(@RequestParam(value = "id",required = false)Integer id,
					   @RequestParam(value = "lessonId",required = false)Integer lessonId,
					   @RequestParam(value = "questionType",required = false)String questionType,
					   @RequestParam(value = "questionData",required = false)String questionData,
					   @RequestParam(name = "order",required = false)Integer order){


		SquirrelQuestion squirrelQuestion = new SquirrelQuestion();
		squirrelQuestion.setId(id);
		squirrelQuestion.setLessonId(lessonId);
		squirrelQuestion.setQuestionType(questionType);
		squirrelQuestion.setQuestionData(questionData);
		squirrelQuestion.setOrder(order);

		RequestInfo info = squirrelQuestionService.updateByPrimaryKeySelective(squirrelQuestion);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "id",required = false)Integer id){
		
		JSONObject jsonObject = new JSONObject();

		if(id != null){
			squirrelQuestionService.deleteByPrimaryKey(id);
			jsonObject.put("success",true);
		}else{
			jsonObject.put("success",false);
		}
		jsonObject.put("denied",false);

		return jsonObject.toJSONString();

	}


}
