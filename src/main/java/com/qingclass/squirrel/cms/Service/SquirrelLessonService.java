package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.*;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLessonMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelPicturebookMapper;
import com.qingclass.squirrel.cms.utils.OssUtil;
import com.qingclass.squirrel.cms.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Component
public class SquirrelLessonService {
	
	@Autowired
    private SquirrelLessonMapper squirrelLessonMapper;
	@Autowired
    private SquirrelPicturebookMapper squirrelPicturebookMapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
     * 无条件查询
     * */
    public List<SquirrelLesson> selectAll(int pageNo, int pageSize){
        pageNo = (pageNo-1)*pageSize;
        return squirrelLessonMapper.selectAll(pageNo, pageSize);
    }

    int deleteByPrimaryKey(Integer id){
        return squirrelLessonMapper.deleteByPrimaryKey(id);
    }

    /**
     * 多条件查询
     * */
    public RequestInfo selectBy(SquirrelLesson lesson){
        RequestInfo info;
        info = new RequestInfo();
        List<SquirrelLesson> squirrelLessons = squirrelLessonMapper.selectBy(lesson);

        if(squirrelLessons.size()>0){
            Collections.sort(squirrelLessons);
            info.setOrderMax(squirrelLessons.get(squirrelLessons.size()-1).getOrder());
        }

        info.setDataList(squirrelLessons);

        return info;
    }

    /**
     * 多条件查询
     * */
    public RequestInfo selectByPage(SquirrelLesson lesson){
        RequestInfo info;
        info = new RequestInfo();

        lesson.setPageNo((lesson.getPageNo()-1)*lesson.getPageSize());

        List<SquirrelLesson> squirrelLessons = squirrelLessonMapper.selectByPage(lesson);
        List<SquirrelLesson> sls = new ArrayList<>();

        squirrelLessons.forEach(e -> sls.add(e));

        if(squirrelLessons.size()>0){
            Collections.sort(squirrelLessons);
            info.setOrderMax(squirrelLessons.get(squirrelLessons.size()-1).getOrder());
        }

        info.setDataList(sls);

        return info;
    }

    /**
     * 插入
     * */
    public RequestInfo insert(SquirrelLesson lesson){
        RequestInfo info;
        lesson.setLessonkey(UUID.randomUUID().toString());
        squirrelLessonMapper.insert(lesson);
        info = new RequestInfo();
        info.setDataObject(lesson);
        return info;
    }
    
    /**
     * 插入
     * */
    public SquirrelLesson insertLesson(SquirrelLesson lesson){
    	lesson.setLessonkey(UUID.randomUUID().toString());
        squirrelLessonMapper.insert(lesson);
        return lesson;
    }

    /**
     * 修改
     * */
    public RequestInfo update(SquirrelLesson lesson){
        RequestInfo info;
        squirrelLessonMapper.updateByPrimaryKeySelective(lesson);

        info = new RequestInfo();

        return info;
    }

    public int delete(Integer id){
        SquirrelLesson squirrelLesson = squirrelLessonMapper.selectByPrimaryKey(id);

        OssUtil ossUtil = new OssUtil();
        //删除oss上的json文件
        try{
            ossUtil.executeDelete("lessons/"+squirrelLesson.getLessonkey()+".json");
            logger.info("Successful to delete oss file (.json)");
        }catch (Exception e){
            logger.error("Failed to delete oss file (.json)");
            logger.error(e.toString());
        }

        squirrelLessonMapper.deleteByPrimaryKey(id);


        return 0;
    }

    /**
     * 修改star
     * */
    public RequestInfo updateStar(SquirrelLesson lesson){
        RequestInfo info;
        squirrelLessonMapper.updateByPrimaryKeySelective(lesson);

        info = new RequestInfo();

        return info;
    }

    /**
     * 根据unitName获取父级lesson
     * */
    public SquirrelLesson selectByUnitName(String unitName, Integer lessonId){
        return squirrelLessonMapper.selectByUnitName(unitName, lessonId);
    }

    /**
     *  根据questionType获取父父级lesson
     * */
    public SquirrelLesson selectByQuestionType(String questionType, Integer unitId){
        return squirrelLessonMapper.selectByQuestionTypeAndUnitId(questionType, unitId);
    }

    /**
     * 主键查询
     * */
    public RequestInfo selectByPrimaryKey(Integer id){
        RequestInfo info;
        info = new RequestInfo();
        SquirrelLesson squirrelLesson = squirrelLessonMapper.selectByPrimaryKey(id);
        info.setDataObject(squirrelLesson);
        return info;
    }
    
    /**
     * 主键查询
     * */
    public SquirrelLesson selectByLessonId(Integer id){
        SquirrelLesson squirrelLesson = squirrelLessonMapper.selectByPrimaryKey(id);
        return squirrelLesson;
    }

    /**
     * 获取lesson下词库
     * */
    public RequestInfo getWords(Integer lessonId){
        RequestInfo info;
        info = new RequestInfo();
        info.setDataList(squirrelLessonMapper.getWords(lessonId));
        return info;
    }

    /**
     *
     * */
    public RequestInfo insertWords(Integer lessonId, String wordsId){
        RequestInfo info;
        info = new RequestInfo();

        ArrayList<LessonMidWord> words = new ArrayList<>();


        String[] split = wordsId.split(",");


        for(int i = 0 ; i < split.length ; i ++){
            LessonMidWord lessonMidWord = new LessonMidWord();
            lessonMidWord.setLessonId(lessonId);
            String s = split[i];
            int wordId = Integer.parseInt(s);
            lessonMidWord.setWordId(wordId);
            words.add(lessonMidWord);
        }
        squirrelLessonMapper.insertWords(words);

        return info;
    }

    /**
     *
     * */
    public Map<String,Object> relationPicturebook(Integer lessonId, Integer picturebook, Integer part){

        List<LessonMidPicturebook> list = squirrelLessonMapper.selectMidPicByLessonId(lessonId);
        if(list.size()>0){
            squirrelLessonMapper.updateMidPic(lessonId, picturebook, part);
        }else{
            squirrelLessonMapper.relationPicturebook(lessonId, picturebook, part);
        }
        return Tools.s();
    }


}
