package com.qingclass.squirrel.cms.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.qingclass.squirrel.cms.constant.Global;
import com.qingclass.squirrel.cms.entity.cms.*;
import com.qingclass.squirrel.cms.mapper.cms.*;
import com.qingclass.squirrel.cms.utils.FileUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 用于生成lesson的json文件并上传到oss
 * */
@Component
public class LessonJsonInterceptor implements HandlerInterceptor {

    @Autowired
    private SquirrelLevelMapper squirrelLevelMapper;
    @Autowired
    private SquirrelLessonMapper squirrelLessonMapper;
    @Autowired
    private SquirrelUnitMapper squirrelUnitMapper;
    @Autowired
    private SquirrelQuestionMapper squirrelQuestionMapper;
    @Autowired
    private Global global;
    private static LessonJsonInterceptor lessonJsonInterceptor;

    @PostConstruct
    public void init(){
        lessonJsonInterceptor = this;
        lessonJsonInterceptor.global = this.global;
    }
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    	logger.info("LessonJsonInterceptor:{}","************************");
    	String requestURI;  //URI
        Integer lessonId = null;
        String lessonKey = null;
        int id;
        int levelId;

        requestURI = httpServletRequest.getRequestURI();
        String prefix = requestURI.split("/")[1];   //取uri第一层级

        logger.info("httpServletRequest :{}", httpServletRequest);
        logger.info("httpServletRequest.getParameter :{}", httpServletRequest.getParameter("id"));
        id = StringUtils.isBlank(httpServletRequest.getParameter("id")) ? 0 : Integer.parseInt(httpServletRequest.getParameter("id"));
        levelId = httpServletRequest.getParameter("levelId") == null ? 0 : Integer.parseInt(httpServletRequest.getParameter("levelId"));

        if(id == 0){
            try{
                id = (int)httpServletRequest.getAttribute("id");
                logger.info("httpServletRequest.getAttribute1 :{}", httpServletRequest.getAttribute("id"));
            }catch (NullPointerException e1){
                logger.warn("id is null");
            }

        }
        SquirrelLesson lesson;

        switch (prefix){//取lesson
            case "lesson":{//按 name 查询 squirrel_lessons

                lesson = squirrelLessonMapper.selectByPrimaryKey(id);

                lessonId = lesson.getId();
                lessonKey = lesson.getLessonkey();

                installJson(lesson,lessonKey,lessonId);
                break;
            }
            case "question":{
                lesson = squirrelLessonMapper.selectByQuestionId(id);
                if(lesson != null){
                    lessonId = lesson.getId();
                    lessonKey = lesson.getLessonkey();
                }
                installJson(lesson,lessonKey,lessonId);
                break;
            }
            default:break;
        }

    }

    /**
     *
     * */
    private void installJson(SquirrelLesson lesson, String lessonKey, Integer lessonId){
        if(lessonId !=null && lessonId != 0){
            // 封装lesson json 数据
            lesson = squirrelLessonMapper.selectByPrimaryKey(lessonId);
        }else{
            lessonId = lesson.getId();
        }
        SquirrelQuestion question = new SquirrelQuestion();
        question.setLessonId(lessonId);
        //封装题
        List<SquirrelQuestion> questionList = squirrelQuestionMapper.selectBy(question);
        lesson.setQuestionList(questionList);

        //查询levelName
        SquirrelLevel squirrelLevel = squirrelLevelMapper.selectByLessonId(lessonId);
        lesson.setLevelName(squirrelLevel.getName());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",lesson);
        String json = jsonObject.toJSONString();

        putObject(lessonJsonInterceptor.global.getOssBucket(), "lessons/"+lessonKey+".json", json);
    }

    /**
     * oss写入
     * */
    public void putObject(String bucketName, String filePath, String json){

        // 初始化OSSClient
        OSSClient client = new OSSClient(lessonJsonInterceptor.global.getOssEndPoint(),
                lessonJsonInterceptor.global.getOssAccessKeyId(),
                lessonJsonInterceptor.global.getOssAccessKeySecret());

        // 获取指定文件的输入流
        File file = new File(filePath);

        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("--目标文件所在目录不存在--");
            if(!file.getParentFile().mkdirs()) {
                System.out.println("--创建目录失败--");
            }
        }

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileUtil.writeString(file, json);

        InputStream content = null;
        try {
            content = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            // 上传Object
            PutObjectResult result = client.putObject(bucketName, filePath, content);
            logger.info("Successful writing JSON file to OSS");
            //关闭服务器
            client.shutdown();
            // 打印ETag
            System.out.println(result.getETag());
        }catch (Exception e){
            logger.error("Writing JSON file to OSS failed");
        }

    }


}
