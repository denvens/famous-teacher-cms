package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelLevelMapper {
    @Update({
            "<script>",
            "update squirrel_levels set delKey = now()",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByPrimaryKey(Integer id);
    
    @Insert({
            "<script>",
            "insert into squirrel_levels (id, subjectId, `name`,",
            "`order`,minWord,maxWord,isOpen,updateDate,image, introduction,buySite,return_fee_day,lessonDay )",
            "values (",
            "	#{id,jdbcType=INTEGER}, #{subjectId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR},",
            "	#{order,jdbcType=INTEGER}, #{minWord,jdbcType=INTEGER}, #{maxWord,jdbcType=INTEGER},",
            "	#{isOpen,jdbcType=INTEGER},now(),#{image,jdbcType=VARCHAR}, #{introduction,jdbcType=VARCHAR}, ",
            "	#{buySite,jdbcType=VARCHAR}, #{returnFeeDay}, #{lessonDay}  ",
            " )",
            "</script>"
    })
    int insert(SquirrelLevel record);

    int insertSelective(SquirrelLevel record);


    @Select({
            "<script>",
            "select ",
            "	id, subjectId, `name`, introduction, `order`, " + 
            "	minWord, maxWord,isOpen,updateDate,image,buySite, return_fee_day returnFeeDay, " + 
            "	lessonDay, " + 
            "	shareId, lessonDay,channelId ",
            "from ",
            "	squirrel_levels",
            "where ",
            "	delKey = 0",
            "	AND id = #{id, jdbcType=INTEGER}  ",
            "order by id desc",
            "</script>"
    })
    SquirrelLevel selectByPrimaryKey(@Param("id") Integer id);

    @Update({
            "<script>",
            "update squirrel_levels",
            "<set>",
            "<if test='subjectId != null'>",
            "subjectId = #{subjectId,jdbcType=INTEGER},",
            "</if>",
            "<if test='name != null'>",
            "`name` = #{name,jdbcType=VARCHAR},",
            "</if>",
            "<if test='order != null'>",
            "`order` = #{order,jdbcType=INTEGER},",
            "</if>",
            "<if test='minWord != null'>",
            "minWord = #{minWord,jdbcType=INTEGER},",
            "</if>",
            "<if test='maxWord != null'>",
            "maxWord = #{maxWord,jdbcType=INTEGER},",
            "</if>",
            "<if test='isOpen != null'>",
            "isOpen = #{isOpen,jdbcType=INTEGER},",
            "</if>",
            "updateDate = now(),",
            "<if test='image != null'>",
            "image = #{image,jdbcType=VARCHAR},",
            "</if>",
            "<if test='introduction != null'>",
            "introduction = #{introduction,jdbcType=VARCHAR},",
            "</if>",
            "<if test='buySite != null'>",
            "buySite = #{buySite,jdbcType=VARCHAR},",
            "</if>",
            "<if test='shareId != null'>",
            "shareId = #{shareId},",
            "</if>",
            "<if test='channelId != null'>",
            "channelId = #{channelId},",
            "</if>",
            "<if test='returnFeeDay != null'>",
            "return_fee_day = #{returnFeeDay},",
            "</if>",
            "<if test='lessonDay != null'>",
            "lessonDay = #{lessonDay},",
            "</if>",
            "</set>",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int updateByPrimaryKeySelective(SquirrelLevel record);

    int updateByPrimaryKey(SquirrelLevel record);

    @Select({
            "<script>",
            "select id, subjectId, `name`, introduction, `order`, minWord, maxWord, isOpen, updateDate, image,buySite,shareId",
            "from squirrel_levels",
            "where delKey = 0",
            "AND subjectId = #{subjectId, jdbcType=INTEGER}",
            "<if test='name != null'>",
            "AND `name` = #{name, jdbcType=VARCHAR}",
            "</if>",
            " order by id desc",
            "</script>"
    })
    List<SquirrelLevel> selectBy(SquirrelLevel record);

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "subjectId",property = "subjectId"),
            @Result(column = "name",property = "name"),
            @Result(column = "order",property = "order"),
            @Result(column = "minWord",property = "minWord"),
            @Result(column = "maxWord",property = "maxWord"),
            @Result(column = "isOpen",property = "isOpen"),
            @Result(column = "updateDate",property = "updateDate"),
            @Result(column = "image",property = "image"),
            @Result(column = "buySite",property = "buySite"),
            @Result(column = "shareId",property = "shareId"),
            @Result(column = "shareId",property = "wxShare",one = @One(select = "com.qingclass.squirrel.cms.mapper.cms.SquirrelWxShareMapper.selectByPrimaryKey"))
    })
    @Select({
            "<script>",
            "select ",
            "	l.id, l.subjectId, l.`name`, l.`order`, l.minWord, l.maxWord, ",
            "	l.isOpen, l.updateDate,  l.image, l.buySite, l.shareId, ",
            "	s.type ",
            "from ",
            "	squirrel_levels l " +
            "left join " + 
            "	squirrel_wx_share s on l.shareId = s.id",
            "where l.delKey = 0",
            "order by id desc",
            "</script>"
    })
    List<SquirrelLevel> selectAll();
    
    @Select({
        "<script>",
        "select ",
        "	l.id, l.subjectId, l.`name`, l.`order`, l.minWord, l.maxWord, ",
        "	l.isOpen, l.updateDate,  l.image, l.buySite, l.shareId, ",
        "	s.type ",
        "from ",
        "	msyb_resource.squirrel_levels l " +
        "left join " + 
        "	msyb_resource.squirrel_wx_share s on l.shareId = s.id",
        "where l.delKey = 0 ",
        "<if test='id != null'>",
        "	and l.id = #{id} ",
        "</if>",
        "order by id desc",
        "</script>"
    })
    List<SquirrelLevel> selectById(@Param("id") Integer id);
    
    @Select({
            "<script>",
            "select id, subjectId, `name`, `order`, minWord, maxWord, isOpen, updateDate, image,buySite,shareId",
            "from squirrel_levels",
            "where delKey = 0 and id in(select levelId from squirrel_lessons where id = #{lessonId})",
            "order by id desc",
            "</script>"
    })
    SquirrelLevel selectByLessonId(@Param("lessonId")Integer lessonId);

    @Update("update squirrel_levels set lessonDay =#{lessonDay} where id=#{levelId}")
	int updateLessonDay(@Param("levelId")Integer levelId, @Param("lessonDay")Integer lessonDay);
}