package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelPicturebook;
import com.qingclass.squirrel.cms.entity.cms.SquirrelSubject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelPicturebookMapper {

    @Update({
            "<script>",
            "update squirrel_picturebook",
            "set delKey=now()",
            "where id = #{id}",
            "</script>"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "<script>",
            "insert into squirrel_picturebook(`name`,part,image,updateTime,levelId)",
            "values(#{name,jdbcType=VARCHAR},#{part,jdbcType=INTEGER},#{image,jdbcType=VARCHAR},now(),#{levelId})",
            "</script>"
    })
    int insert(SquirrelPicturebook record);

    @Insert({
            "<script>",
            "insert into squirrel_picturebook(`name`,part,image,updateTime,levelId) values ",
            "<foreach collection='record' item='item' index= 'index' separator =','>",
            "(",
            "#{item.name,jdbcType=VARCHAR},#{item.part,jdbcType=INTEGER},#{item.image,jdbcType=VARCHAR},now(),#{item.levelId}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertAll(@Param("record") List<SquirrelPicturebook> record);

    int insertSelective(SquirrelSubject record);

    @Select("select id,name,part,updateTime from squirrel_picturebook where delKey = '0' and id = #{id,jdbcType=INTEGER}")
    SquirrelPicturebook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SquirrelSubject record);

    @Update({
            "<script>",
            "update squirrel_picturebook",
            "<set>",
            "<if test='name != null'>",
            "`name` = #{name,jdbcType=VARCHAR},",
            "</if>",
            "<if test='part != null'>",
            "part = #{part,jdbcType=INTEGER},",
            "</if>",
            "<if test='image != null'>",
            "image = #{image,jdbcType=INTEGER},",
            "</if>",
            "updateTime=now()",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKey(SquirrelPicturebook record);

    @Select({
            "<script>",
            "select id,name,part,updateTime from squirrel_picturebook",
            "where delKey = '0'",
            "<if test='name != null'>",
            "AND CONCAT(`name`,id) LIKE CONCAT(CONCAT('%',#{name,jdbcType=VARCHAR}),'%') ",
            "</if>",
            "order by id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelPicturebook> selectAll(SquirrelPicturebook record);

    @Select({
            "<script>",
            "select id,name,part,updateTime from squirrel_picturebook",
            "where delKey = '0'",
            "<if test='name != null'>",
            "AND CONCAT(`name`,id) LIKE CONCAT(CONCAT('%',#{name,jdbcType=VARCHAR}),'%') ",
            "</if>",
            "AND id in(select picId from lessons_mid_picturebooks where",
            " lessonId in(select id from squirrel_lessons where levelId = #{levelId}))",
            "order by id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelPicturebook> selectByLevelId(SquirrelPicturebook record);

    @Select({
            "<script>",
            "select id,name,part,updateTime from squirrel_picturebook",
            "where delKey = '0' and levelId = #{levelId}",
            "<if test='name != null'>",
            "AND CONCAT(`name`,id) LIKE CONCAT(CONCAT('%',#{name,jdbcType=VARCHAR}),'%') ",
            "</if>",
            "order by id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelPicturebook> selectBy(SquirrelPicturebook record);  //多条件查询

    @Select({
            "<script>",
            "select id,name,part,updateTime from squirrel_picturebook",
            "where delKey = '0' and levelId = #{levelId} and name = #{name}",
            "</script>"
    })
    List<SquirrelPicturebook> selectByNonePage(SquirrelPicturebook record);  //多条件查询

    @Select("select id,name,part,updateTime,image from squirrel_picturebook where delKey = '0' and id = #{id,jdbcType=INTEGER}")
    SquirrelPicturebook selectById(Integer id);  //多条件查询

    @Select({
            "<script>",
            "select p.id,p.name,p.part,p.updateTime,m.part as usePart,m.lessonId from squirrel_picturebook p",
            "left join lessons_mid_picturebooks m on p.id = m.picId where delKey = '0'",
            "</script>"
    })
    List<SquirrelPicturebook> selectBase();

    @Select({
            "<script>",
            "select p.id,p.name,p.part,p.updateTime,m.part as usePart,m.lessonId from squirrel_picturebook p",
            "left join lessons_mid_picturebooks m on p.id = m.picId where delKey = '0' and p.levelId = #{levelId}",
            "</script>"
    })
    List<SquirrelPicturebook> selectBaseByLevelId(Integer levelId);


    @Select({
            "<script>",
            "select * from squirrel_picturebook p",
            "where delKey = '0' and p.levelId = #{levelId}",
            "</script>"
    })
    List<SquirrelPicturebook> selectBaseByLevelIdNoneParam(Integer levelId);

    @Select("select count(id) from squirrel_picturebook where delKey = '0'")
    int count();

    @Select({
            "<script>",
            "select count(id) from squirrel_picturebook",
            "where delKey = '0'",
            "and levelId = #{levelId}",
            "</script>"
    })
    int countByLevelId(SquirrelPicturebook record);

    @Delete({
            "<script>",
            "delete from lessons_mid_picturebooks where lessonId = #{lessonId}",
            "</script>"
    })
    int deleteMid(Integer lessonId);

}