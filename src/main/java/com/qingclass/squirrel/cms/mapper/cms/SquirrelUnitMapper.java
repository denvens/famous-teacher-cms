package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelUnit;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelUnitMapper {
    @Update({
            "<script>",
            "update squirrel_units set delKey = now()",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByPrimaryKey(Integer id);

    @Update({
            "<script>",
            "update squirrel_units set delKey = now()",
            "where lessonId = #{lessonId,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByLessonId(Integer lessonId);

    @Insert({
            "<script>",
            "insert into squirrel_units (id, lessonId, `name`,",
            "`order`)",
            "values (#{p.id,jdbcType=INTEGER}, #{p.lessonId,jdbcType=INTEGER}, #{p.name,jdbcType=VARCHAR},",
            "#{p.order,jdbcType=INTEGER})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insert(@Param("p") SquirrelUnit record);

    @Insert({
            "<script>",
            "insert into squirrel_units (lessonId, `name`,",
            "`order`)",
            "values (#{p.lessonId,jdbcType=INTEGER}, #{p.name,jdbcType=VARCHAR},",
            "#{p.order,jdbcType=INTEGER})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insertReturnId(@Param("p") SquirrelUnit record);

    int insertSelective(SquirrelUnit record);

    @Select({
            "<script>",
            "select id, lessonId, `name`, `order`",
            "from squirrel_units",
            "where delKey = 0",
            "AND id = #{id,jdbcType=INTEGER} order by id asc",
            "</script>"
    })
    SquirrelUnit selectByPrimaryKey(Integer id);

    @Update({
            "<script>",
            "update squirrel_units",
            "<set>",
            "<if test='lessonId != null'>",
            "lessonId = #{lessonId,jdbcType=INTEGER},",
            "</if>",
            "<if test='name != null'>",
            "`name` = #{name,jdbcType=VARCHAR},",
            "</if>",
            "<if test='order != null'>",
            "`order` = #{order,jdbcType=INTEGER},",
            "</if>",
            "</set>",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int updateByPrimaryKeySelective(SquirrelUnit record);

    int updateByPrimaryKey(SquirrelUnit record);


    @Select({
            "<script>",
            "select id, lessonId, `name`, `order`",
            "from squirrel_units",
            "where delKey = 0 order by id asc",
            "</script>"
    })
    List<SquirrelUnit> selectAll();

    @Select({
            "<script>",
            "select id, lessonId, `name`, `order`",
            "from squirrel_units",
            "where delKey = 0",
            "<if test='lessonId != null'>",
            "AND lessonId = #{lessonId, jdbcType=INTEGER}",
            "</if>",
            " order by id asc",
            "</script>"
    })
    List<SquirrelUnit> selectBy(SquirrelUnit record);

    @Select({
            "<script>",
            "select count(id)",
            "from squirrel_units",
            "where delKey = 0",
            "AND lessonId = #{lessonId, jdbcType=INTEGER} order by id asc",
            "</script>"
    })
    int selectCountByLessonId(Integer lessonId);
}
