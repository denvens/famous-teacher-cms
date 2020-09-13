package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelQuestion;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipSettingReq;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelQuestionMapper {
    @Update({
            "<script>",
            "update squirrel_questions set delKey = now()",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByPrimaryKey(Integer id);
    @Update({
            "<script>",
            "update squirrel_questions set delKey = now()",
            "where unitId = #{unitId,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByUnitId(Integer unitId);

    @Insert({
            "<script>",
            "insert into squirrel_questions (id, lesson_id, questionType, questionData,",
            "`order`, questionKey)",
            "values (#{p.id,jdbcType=INTEGER}, #{p.lessonId,jdbcType=INTEGER}, #{p.questionType,jdbcType=VARCHAR},",
            "#{p.questionData,jdbcType=INTEGER},#{p.order,jdbcType=INTEGER},#{p.questionKey,jdbcType=VARCHAR})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insert(@Param("p")SquirrelQuestion record);


    @Insert({
            "<script>",
            "insert into squirrel_questions (lesson_id, unitId, questionType, questionData,",
            "`order`, questionKey) values ",
            "<foreach collection='record' item='item' index= 'index' separator =','>",
            "(",
            "#{item.lessonId}, #{item.unitId},#{item.questionType},#{item.questionData},#{item.order},#{item.questionKey}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertAll(@Param("record") List<SquirrelQuestion> record);

    int insertSelective(SquirrelQuestion record);


    @Select({
            "<script>",
            "select id, unitId, questionType, questionData, `order`, questionKey",
            "from squirrel_questions",
            "where delKey = '0' AND id = #{id,jdbcType=INTEGER} order by id asc",
            "</script>"
    })
    SquirrelQuestion selectByPrimaryKey(Integer id);

    @Update({
            "<script>",
            "update squirrel_questions ",
            "<set>",
            
            "<if test='p.lessonId != null'>",
            "	lesson_id = #{p.lessonId,jdbcType=INTEGER}, ",
            "</if>",
            
            "<if test='p.unitId != null'>",
            "	unitId = #{p.unitId,jdbcType=INTEGER},",
            "</if>",
            "<if test='p.questionType != null'>",
            "	questionType = #{p.questionType,jdbcType=VARCHAR},",
            "</if>",
            "<if test='p.questionData != null'>",
            "	questionData = #{p.questionData,jdbcType=VARCHAR},",
            "</if>",
            "<if test='p.questionKey != null'>",
            "	questionKey = #{p.questionKey,jdbcType=VARCHAR}",
            "</if>",
            "<if test='p.order != null'>",
            "	`order` = #{p.order,jdbcType=INTEGER} ",
            "</if>",
            "</set>",
            "	where id = #{p.id,jdbcType=INTEGER}",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int updateByPrimaryKeySelective(@Param("p")SquirrelQuestion record);

    int updateByPrimaryKey(SquirrelQuestion record);

    @Select({
            "<script>",
            "select id, lesson_id lessonId, unitId, questionType, questionData, `order`, questionKey",
            "from squirrel_questions",
            "where delKey = '0'",
            
            "<if test='question.lessonId != null'>",
            "	AND lesson_id = #{question.lessonId,jdbcType=INTEGER} ",
            "</if>",
            " order by id asc ",
            "</script>"
    })
    List<SquirrelQuestion> selectBy(@Param("question")SquirrelQuestion question);

    @Select({
        "<script>",
        "select id, lesson_id lessonId, unitId, questionType, questionData, `order`, questionKey",
        "from squirrel_questions",
        "where delKey = '0'",
        
        "<if test='question.lessonId != null'>",
        "	AND lesson_id = #{question.lessonId,jdbcType=INTEGER} ",
        "</if>",
        
        " order by id asc ",
        " limit #{question.pageNo,jdbcType=INTEGER},#{question.pageSize,jdbcType=INTEGER} ",
        "</script>"
	})
	List<SquirrelQuestion> selectByPage(@Param("question")SquirrelQuestion question);
    
    @Select({
            "<script>",
            "select id, unitId, questionType, questionData, `order`, questionKey",
            "from squirrel_questions",
            "where delKey = '0' ",
            "order by id asc ",
            "</script>"
    })
    List<SquirrelQuestion> selectAll();
    
    @Select({
        "<script>",
        "select count(id) ",
        "from squirrel_questions",
        "where delKey = '0' ",
        "<if test='question.lessonId != null'>",
        "	AND lesson_id = #{question.lessonId,jdbcType=INTEGER} ",
        "</if>",
        "order by id asc ",
        "</script>"
    })
    int listCount(@Param("question")SquirrelQuestion question);

}