package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelLessonService;
import com.qingclass.squirrel.cms.Service.SquirrelQuestionService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLesson;
import com.qingclass.squirrel.cms.entity.cms.SquirrelPicturebook;
import com.qingclass.squirrel.cms.entity.cms.SquirrelQuestion;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelPicturebookMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelQuestionMapper;
import com.qingclass.squirrel.cms.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lesson")
public class LessonController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SquirrelLessonService squirrelLessonService;
	@Autowired
	SquirrelLessonMapper squirrelLessonMapper;
	@Autowired
	SquirrelPicturebookMapper squirrelPicturebookMapper;
	
	@Autowired
	SquirrelQuestionService squirrelQuestionService;
	@Autowired
	SquirrelQuestionMapper squirrelQuestionMapper;


	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list( @RequestParam(name = "levelId", required = true)Integer levelId,@RequestParam(name = "audition", required = false)Integer audition,
						@RequestParam(value="pageNo",required = true)Integer pageNo,@RequestParam(value="pageSize",required = true)Integer pageSize){


		SquirrelLesson squirrelLesson = new SquirrelLesson();
		squirrelLesson.setLevelid(levelId);
		squirrelLesson.setPageNo(pageNo);
		squirrelLesson.setPageSize(pageSize);
		squirrelLesson.setAudition(audition);

		RequestInfo info = squirrelLessonService.selectByPage(squirrelLesson);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("lessonList",objects);
		jsonObject1.put("orderMax",info.getOrderMax());

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("pageTotal",squirrelLessonMapper.count(levelId,audition));
		jsonObject.put("data",jsonObject1);
		return jsonObject.toJSONString();
	}

	@ResponseBody
	@PostMapping(value = "create")
	public String create(HttpServletRequest httpServletRequest, 
						 @RequestParam(name="name",required = false)String name, 
						 @RequestParam(name="levelId",required = false)Integer levelId,
						 @RequestParam(name="isOpen", required = false)Integer isOpen,
						 @RequestParam(name="order", required = false)Integer order,
						 @RequestParam(name="title", required = false)String title, 
						 @RequestParam(name="image", required = false)String image,
						 @RequestParam(name="shareImage", required = false)String shareImage){

		SquirrelLesson squirrelLesson = new SquirrelLesson();
		squirrelLesson.setName(name);
		squirrelLesson.setLevelid(levelId);
		squirrelLesson.setAudition(0);
		squirrelLesson.setIsOpen(isOpen);
		squirrelLesson.setTitle(title);
		squirrelLesson.setImage(image);
		squirrelLesson.setShareImage(shareImage);
		squirrelLesson.setOrder(order);
		squirrelLesson.setRelation(0);
		squirrelLesson.setUpdateDate(new Date());
		RequestInfo info = squirrelLessonService.insert(squirrelLesson);


		SquirrelLesson less = (SquirrelLesson)info.getDataObject();
		logger.info("create lesson id:{}", less.getId());
		
		httpServletRequest.setAttribute("id",less.getId());

		JSONObject json = new JSONObject();

		json.put("denied",info.getDenied());
		json.put("success",info.getSuccess());

		return json.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestParam(name = "id", required = false)Integer id, 
					   @RequestParam(name = "name", required = false)String name,
					   @RequestParam(name="isOpen", required = false)Integer isOpen, 
					   @RequestParam(name="title", required = false)String title,
					   @RequestParam(name ="order", required = false)Integer order, 
					   @RequestParam(name="image", required = false)String image,
					   @RequestParam(name="shareImage", required = false)String shareImage){
		SquirrelLesson squirrelLesson = new SquirrelLesson();
		squirrelLesson.setId(id);
		squirrelLesson.setName(name);
		squirrelLesson.setIsOpen(isOpen);
		squirrelLesson.setTitle(title);
		squirrelLesson.setImage(image);
		squirrelLesson.setShareImage(shareImage);
		squirrelLesson.setOrder(order);
		squirrelLesson.setRelation(0);
		squirrelLesson.setUpdateDate(new Date());

		RequestInfo info = squirrelLessonService.update(squirrelLesson);

		JSONObject json = new JSONObject();

		json.put("denied",info.getDenied());
		json.put("success",info.getSuccess());

		return json.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@RequestParam(name = "id",required = false)Integer id){
		squirrelLessonService.delete(id);
		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public String info(@RequestParam(name = "id", required = false)Integer id){

		RequestInfo info = squirrelLessonService.selectByPrimaryKey(id);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());
		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "getWords", method = RequestMethod.POST)
	public String getWords(@RequestParam(value = "lessonId",required = false)Integer lessonId){
		RequestInfo info = squirrelLessonService.getWords(lessonId);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("wordsList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);
		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "insertWords", method = RequestMethod.POST)
	public String insertWords(@RequestParam(value = "lessonId",required = false)Integer lessonId,@RequestParam(value = "wordsId",required = false)String wordsId,
							  @RequestParam(value = "isKey",required = false)Integer isKey){
		RequestInfo info = squirrelLessonService.insertWords(lessonId, wordsId);

		JSONObject json = new JSONObject();

		json.put("denied",info.getDenied());
		json.put("success",info.getSuccess());

		return json.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "edit-word", method = RequestMethod.POST)
	public String editWord(@RequestParam(value = "id",required = false)Integer lessonId,@RequestParam(value = "wordId",required = false)Integer wordId,
						   @RequestParam(value = "isKey",required = false)Integer isKey){
		squirrelLessonMapper.updateWord(lessonId, wordId, isKey);

		JSONObject json = new JSONObject();

		json.put("denied",false);
		json.put("success",true);

		return json.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "deleteWord", method = RequestMethod.POST)
	public String deleteWord(@RequestParam(value = "lessonId",required = false)Integer lessonId,@RequestParam(value = "wordId",required = false)Integer wordId){
		int i = squirrelLessonMapper.deleteWord(lessonId, wordId);

		JSONObject json = new JSONObject();

		json.put("denied",false);
		json.put("success",true);

		return json.toJSONString();
	}
	
	
	@ResponseBody
	@PostMapping(value = "copy")
	public String copy(HttpServletRequest httpServletRequest, 
						 @RequestParam(name="lessonIds",required = false)String lessonIds,
						 @RequestParam(name="levelId",required = false)Integer levelId){
		String[] strs = lessonIds.split(",");
    	List<String> strList = Arrays.asList(strs);
    	for(int i=0; i<strList.size(); i++){
    		Integer lessonId = Integer.parseInt(strList.get(i));
    		
    		SquirrelLesson lesson = squirrelLessonService.selectByLessonId(lessonId);
    		
    		SquirrelLesson squirrelLesson = new SquirrelLesson();
    		squirrelLesson.setName(lesson.getName());
    		squirrelLesson.setLevelid(levelId);
    		squirrelLesson.setAudition(0);
    		squirrelLesson.setIsOpen(0);
    		squirrelLesson.setTitle(lesson.getTitle());
    		squirrelLesson.setImage(lesson.getImage());
    		squirrelLesson.setShareImage(lesson.getShareImage());
    		squirrelLesson.setOrder(lesson.getOrder());
    		squirrelLesson.setRelation(0);
    		squirrelLesson.setUpdateDate(new Date());

    		squirrelLessonService.insertLesson(squirrelLesson);

    		
    		
    		SquirrelQuestion squirrelQuestion = new SquirrelQuestion();
    		squirrelQuestion.setLessonId(lessonId);
            List<SquirrelQuestion> squirrelQuestions = squirrelQuestionMapper.selectBy(squirrelQuestion);
            List<SquirrelQuestion> targetQuestions = new ArrayList<>();
            for(SquirrelQuestion sq : squirrelQuestions){
                sq.setLessonId(squirrelLesson.getId());
                sq.setUnitId(0);
            	targetQuestions.add(sq);
            }
            if(targetQuestions.size() > 0){
                squirrelQuestionMapper.insertAll(targetQuestions);
            }

//    		logger.info("copy lesson id:{}", squirrelLesson.getId());
//    		httpServletRequest.setAttribute("id", squirrelLesson.getId());
    	}
    		
		

		JSONObject json = new JSONObject();
		json.put("denied",false);
		json.put("success",true);
		return json.toJSONString();
	}



}