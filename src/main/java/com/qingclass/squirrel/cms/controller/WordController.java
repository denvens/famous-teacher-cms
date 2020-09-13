package com.qingclass.squirrel.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingclass.squirrel.cms.Service.SquirrelWordService;
import com.qingclass.squirrel.cms.constant.Global;
import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelWord;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWordMapper;
import com.qingclass.squirrel.cms.utils.OssUtil;
import com.qingclass.squirrel.cms.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/word")
public class WordController {

	@Autowired
	SquirrelWordService squirrelWordService;
	@Autowired
	SquirrelWordMapper squirrelWordMapper;
	@Autowired
	Global global;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@RequestParam(value="pageNo",required = false)Integer pageNo,@RequestParam(value="pageSize",required = false)Integer pageSize,
					   @RequestParam(value ="word",required = false)String word,@RequestParam(value = "translation",required = false)String translation){


		SquirrelWord squirrelWord = new SquirrelWord();
		squirrelWord.setPageNo(pageNo);
		squirrelWord.setPageSize(pageSize);
		if(word != null){
			squirrelWord.setWord(word);
		}
		if(translation != null){
			squirrelWord.setTranslation(translation);
		}
		RequestInfo info = squirrelWordService.selectAll(squirrelWord);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();

		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("wordList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);
		jsonObject.put("pageSize",global.getPageSize());
		jsonObject.put("pageTotal",squirrelWordMapper.count(squirrelWord));

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestParam(value="word", required = false)String word,
						 @RequestParam(value="translation", required = false)String translation,
						 @RequestParam(value="voice", required = false)String voice,
						 @RequestParam(value="keyImage", required = false)String keyImage,
						 @RequestParam(value="baseImages", required = false)String baseImages,
						 @RequestParam(value = "confusionImage", required = false)String confusionImage){

		SquirrelWord squirrelWord = new SquirrelWord();

		squirrelWord.setWord(word);
		squirrelWord.setTranslation(translation);
		squirrelWord.setVoice(voice);
		squirrelWord.setKeyImage(keyImage);
		squirrelWord.setConfusionImage(confusionImage);
		squirrelWord.setBaseImage(baseImages);


		RequestInfo info = squirrelWordService.insert(squirrelWord);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@RequestParam(value = "id", required = false)Integer id, @RequestParam(value="word", required = false)String word,
						 @RequestParam(value="translation", required = false)String translation,
						 @RequestParam(value="voice", required = false)String voice,
						 @RequestParam(value="keyImage", required = false)String keyImage,
						 @RequestParam(value="baseImages", required = false)String baseImages,
						 @RequestParam(value = "confusionImage", required = false)String confusionImage){

		SquirrelWord squirrelWord = new SquirrelWord();

		squirrelWord.setId(id);
		squirrelWord.setWord(word);
		squirrelWord.setTranslation(translation);

		squirrelWord.setVoice(voice);
		squirrelWord.setKeyImage(keyImage);
		squirrelWord.setConfusionImage(confusionImage);
		squirrelWord.setBaseImage(baseImages);

		RequestInfo info = squirrelWordService.updateByPrimaryKeySelective(squirrelWord);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data","");

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@RequestParam(value = "id",required = false)Integer id){

		squirrelWordMapper.deleteByPrimaryKey(id);

		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String info(@RequestParam(value = "id",required = false)Integer id){

		RequestInfo info = squirrelWordService.selectByPrimaryKey(id);

		JSONObject jsonObject = new JSONObject();


		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",info.getDataObject());

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/findWord", method = RequestMethod.POST)
	public String findWord(@RequestParam(value = "id",required = false)Integer id,
						   @RequestParam(value= "word",required = false)String word,
						   @RequestParam(value= "translation",required = false)String translation){


		SquirrelWord squirrelWord = new SquirrelWord();
		squirrelWord.setId(id);
		squirrelWord.setWord(word);
		squirrelWord.setTranslation(translation);
		RequestInfo info = squirrelWordService.selectBy(squirrelWord);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();
		JSONArray objects = new JSONArray();
		objects.addAll((List)info.getDataList());
		jsonObject1.put("wordList",objects);

		jsonObject.put("denied",info.getDenied());
		jsonObject.put("success",info.getSuccess());
		jsonObject.put("data",jsonObject1);

		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/file-upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam(value = "file",required = false)MultipartFile[] files,
			@RequestParam(value = "fileType",required = false)String fileType){
		JSONObject jsonObject = new JSONObject();
		OssUtil ossUtil = new OssUtil();
		if(files.length > 0){
			for(MultipartFile file : files){
				if(file != null && !file.isEmpty()){
					switch (fileType){
						case "image":{
							String s = ossUtil.executeUpLoad(global.getOssImagePath(), file, ".png");
							if(s == null){
								jsonObject.put("success",false);
								jsonObject.put("message","fail");
								return jsonObject.toJSONString();
							}

							jsonObject.put("denied",false);
							jsonObject.put("success",true);
							jsonObject.put("data",s);
							return jsonObject.toJSONString();
						}
						case "voice":{
							String s = ossUtil.executeUpLoad(global.getOssVoicePath(), file, ".mp3");
							if(s == null){
								jsonObject.put("success",false);
								jsonObject.put("message","fail");
								return jsonObject.toJSONString();
							}
							jsonObject.put("denied",false);
							jsonObject.put("success",true);
							jsonObject.put("data",s);
							return jsonObject.toJSONString();
						}
						case "audio":{
							String s = ossUtil.executeUpLoad(global.getOssAudioPath(), file, ".mp3");
							if(s == null){
								jsonObject.put("success",false);
								jsonObject.put("message","fail");
								return jsonObject.toJSONString();
							}
							jsonObject.put("denied",false);
							jsonObject.put("success",true);
							jsonObject.put("data",s);
							return jsonObject.toJSONString();
						}
						case "video":{
							logger.info("upload video:start");
							String s = ossUtil.executeUpLoad(global.getOssVideoPath(), file, ".mp4");
							logger.info("upload video-file:{}",s);
							if(s == null){
								jsonObject.put("success",false);
								jsonObject.put("message","fail");
								return jsonObject.toJSONString();
							}
							jsonObject.put("denied",false);
							jsonObject.put("success",true);
							jsonObject.put("data",s);
							return jsonObject.toJSONString();
						}
						default:{
							jsonObject.put("success",false);
							jsonObject.put("message","fileType is worry.");
							return jsonObject.toJSONString();
						}
					}
				}
			}
		}

		jsonObject.put("success",false);
		jsonObject.put("message","fail");
		return jsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/file-upload-temp", method = RequestMethod.POST)
	public Map<String, Object> fileUpload(@RequestParam(value = "file",required = false)MultipartFile file, @RequestParam(value = "fileName",required = false)String fileName){
		OssUtil ossUtil = new OssUtil();
		ossUtil.temp(fileName,file);
		return Tools.s();
	}

}
