package com.qingclass.squirrel.cms.controller;


import com.qingclass.squirrel.cms.entity.cms.*;
import com.qingclass.squirrel.cms.mapper.cms.*;
import com.qingclass.squirrel.cms.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/script")
public class ScriptController {

    @Autowired
    SquirrelLevelMapper squirrelLevelMapper;
    @Autowired
    SquirrelLessonMapper squirrelLessonMapper;
    @Autowired
    SquirrelUnitMapper squirrelUnitMapper;
    @Autowired
    SquirrelQuestionMapper squirrelQuestionMapper;
    @Autowired
    SquirrelPicturebookMapper squirrelPicturebookMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @ResponseBody
    @RequestMapping(value="/copy-level", method = RequestMethod.POST)
    public Map<String,Object> copyLevel(@RequestParam(name = "sourceLevelId", required = false)Integer sourceLevelId,
                                        @RequestParam(name = "targetLevelId", required = false)Integer targetLevelId,
                                        @RequestParam(name = "levelName", required = false)String levelName){

        SquirrelLevel squirrelLevel = squirrelLevelMapper.selectByPrimaryKey(sourceLevelId);
        squirrelLevel.setId(targetLevelId);
        squirrelLevel.setName(levelName);
        squirrelLevelMapper.insert(squirrelLevel);

        logger.info("复制level成功...");

        List<SquirrelPicturebook> squirrelPicturebooks = squirrelPicturebookMapper.selectBaseByLevelIdNoneParam(sourceLevelId);
        squirrelPicturebooks.forEach(e -> e.setLevelId(targetLevelId));

        squirrelPicturebookMapper.insertAll(squirrelPicturebooks);

        SquirrelLesson squirrelLesson = new SquirrelLesson();
        squirrelLesson.setLevelid(sourceLevelId);
        List<SquirrelLesson> squirrelLessons = squirrelLessonMapper.selectBy(squirrelLesson);


        for(SquirrelLesson sl : squirrelLessons){
            int sourceLessonId = sl.getId();    //之前的id
            sl.setLevelid(targetLevelId);
            sl.setLessonkey(UUID.randomUUID().toString());
            squirrelLessonMapper.insertReturnId(sl);
            logger.info("复制lesson"+sourceLessonId);
            int nowLessonId = sl.getId(); //现在的id

            //同步绘本
            List<LessonMidPicturebook> lessonMidPicturebooks = squirrelLessonMapper.selectPicNameAndMidPicByLessonId(sourceLessonId);
            SquirrelPicturebook spb = new SquirrelPicturebook();
            spb.setLevelId(targetLevelId);
            if(lessonMidPicturebooks.size() > 0){
                spb.setName(lessonMidPicturebooks.get(0).getName());
                List<SquirrelPicturebook> spbs = squirrelPicturebookMapper.selectByNonePage(spb);
                if(spbs.size() > 0){
                    squirrelLessonMapper.relationPicturebook(nowLessonId,spbs.get(0).getId(),lessonMidPicturebooks.get(0).getPart());
                }
            }

            SquirrelUnit squirrelUnit = new SquirrelUnit();
            squirrelUnit.setLessonId(sourceLessonId);
            List<SquirrelUnit> squirrelUnits = squirrelUnitMapper.selectBy(squirrelUnit);

            for(SquirrelUnit su : squirrelUnits){
                int sourceUnitId = su.getId();//之前的unitid
                su.setLessonId(nowLessonId);
                squirrelUnitMapper.insertReturnId(su);
                logger.info("复制unit"+sourceUnitId);
                int nowUnitId = su.getId();//现在的unitid

                SquirrelQuestion squirrelQuestion = new SquirrelQuestion();
                squirrelQuestion.setUnitId(sourceUnitId);
                List<SquirrelQuestion> squirrelQuestions = squirrelQuestionMapper.selectBy(squirrelQuestion);

                List<SquirrelQuestion> targetQuestions = new ArrayList<>();

                for(SquirrelQuestion sq : squirrelQuestions){
                    sq.setUnitId(nowUnitId);
                    targetQuestions.add(sq);
                }
                if(targetQuestions.size() > 0){
                    squirrelQuestionMapper.insertAll(targetQuestions);
                }
                logger.info("复制questions"+sourceUnitId);

            }


        }


        return Tools.s();
    }
}
