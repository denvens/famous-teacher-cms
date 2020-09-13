package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelPicturebookService;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelPicturebook;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelPicturebookMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/picturebook")
public class PicturebookController {

	@Autowired
	SquirrelPicturebookMapper squirrelPicturebookMapper;
	@Autowired
	SquirrelPicturebookService squirrelPicturebookService;

	@ResponseBody
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public String list(@RequestParam(value="pageNo",required = false)Integer pageNo,@RequestParam(value="pageSize",required = false)Integer pageSize,
					   @RequestParam(value="id",required = false)Integer id,@RequestParam(value="name",required = false)String name,
					   @RequestParam(value = "levelId",required = false)Integer levelId){
		SquirrelPicturebook squirrelPicturebook = new SquirrelPicturebook();
		squirrelPicturebook.setId(id);
		squirrelPicturebook.setName(name);
		squirrelPicturebook.setPageNo(pageNo);
		squirrelPicturebook.setPageSize(pageSize);
		squirrelPicturebook.setLevelId(levelId);
		RequestInfo info = squirrelPicturebookService.selectByLevelId(squirrelPicturebook);
		int count = squirrelPicturebookMapper.countByLevelId(squirrelPicturebook);
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("picList",objects);
		jsonObject1.put("pageTotal",count);
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);


		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "list-base",method = RequestMethod.POST)
	public Map<String,Object> listBase(@RequestParam(value = "levelId",required = false)Integer levelId){

		List<SquirrelPicturebook> squirrelPicturebooks = squirrelPicturebookService.selectAll(levelId);

		return Tools.s(squirrelPicturebooks);
	}

	@ResponseBody
	@RequestMapping(value = "create",method = RequestMethod.POST)
	public String create(@RequestParam(value = "name",required = false)String name,
						 @RequestParam(value = "part",required = false)Integer part,@RequestParam(value = "levelId",required = false)Integer levelId,
						 @RequestParam(value = "image",required = false)String image){

		SquirrelPicturebook squirrelPicturebook = new SquirrelPicturebook();
		squirrelPicturebook.setName(name);
		squirrelPicturebook.setPart(part);
		squirrelPicturebook.setLevelId(levelId);
		squirrelPicturebook.setImage(image);
		RequestInfo info = squirrelPicturebookService.insert(squirrelPicturebook);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value = "edit",method = RequestMethod.POST)
	public String edit(@RequestParam(value = "id",required = false)Integer id,
					   @RequestParam(value = "name",required = false)String name,
						 @RequestParam(value = "part",required = false)Integer part,
					   @RequestParam(value = "image",required = false)String image){

		SquirrelPicturebook squirrelPicturebook = new SquirrelPicturebook();
		squirrelPicturebook.setId(id);
		squirrelPicturebook.setName(name);
		squirrelPicturebook.setPart(part);
		squirrelPicturebook.setImage(image);
		RequestInfo info = squirrelPicturebookService.update(squirrelPicturebook);

	//	squirrelPicturebookMapper.deleteMid(id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value = "info",method = RequestMethod.POST)
	public String info(@RequestParam(value = "id",required = false)Integer id){



		RequestInfo info = squirrelPicturebookService.selectById(id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();

	}

	@ResponseBody
	@RequestMapping(value = "delete",method = RequestMethod.POST)
	public String delete(@RequestParam(value = "id",required = false)Integer id){

		RequestInfo info = squirrelPicturebookService.delete(id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();

	}
}
