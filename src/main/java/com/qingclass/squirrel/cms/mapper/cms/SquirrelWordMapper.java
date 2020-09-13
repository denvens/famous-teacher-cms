package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelWord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelWordMapper {

    @Update({
            "<script>",
            "update squirrel_words set delKey = now()",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "<script>",
            "insert into squirrel_words(id, word, `translation`, voice, keyImage, baseImage, confusionImage)",
            "values(#{id,jdbcType=INTEGER},#{word,jdbcType=VARCHAR},#{translation,jdbcType=VARCHAR},#{voice,jdbcType=VARCHAR},",
            "#{keyImage,jdbcType=VARCHAR},#{baseImage,jdbcType=VARCHAR},#{confusionImage,jdbcType=VARCHAR})",
            "</script>"
    })
    int insert(SquirrelWord record);

    int insertSelective(SquirrelWord record);


    @Select({
            "<script>",
            "select id, word, `translation`, voice, keyImage, baseImage, confusionImage",
            "from squirrel_words",
            "where delKey = 0",
            "AND id = #{id,jdbcType=INTEGER} order by id desc",
            "</script>"
    })
    SquirrelWord selectByPrimaryKey(Integer id);

    @Update({
            "<script>",
            "update squirrel_words",
            "<set>",
            "<if test='word != null'>",
            "word = #{word,jdbcType=VARCHAR},",
            "</if>",
            "<if test='translation != null'>",
            "`translation` = #{translation,jdbcType=VARCHAR},",
            "</if>",
            "<if test='voice != null'>",
            "voice = #{voice,jdbcType=VARCHAR},",
            "</if>",
            "<if test='keyImage != null'>",
            "keyImage = #{keyImage,jdbcType=VARCHAR},",
            "</if>",
            "<if test='baseImage != null'>",
            "baseImage = #{baseImage,jdbcType=VARCHAR},",
            "</if>",
            "<if test='confusionImage !=null'>",
            "confusionImage = #{confusionImage,jdbcType=VARCHAR},",
            "</if>",
            "</set>",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int updateByPrimaryKeySelective(SquirrelWord record);

    int updateByPrimaryKey(SquirrelWord record);

    @Select({
            "<script>",
            "select id, word, `translation`, voice, keyImage, baseImage, confusionImage",
            "from squirrel_words",
            "where delKey = 0",
            "<if test='id != null'>",
            "AND id = #{id,jdbcType=INTEGER}",
            "</if>",
            "<if test='word != null'>",
            "AND word = #{word,jdbcType=VARCHAR}",
            "</if>",
            "<if test='translation != null'>",
            "AND `translation` = #{translation,jdbcType=VARCHAR}",
            "</if>",
            "<if test='voice != null'>",
            "AND voice = #{voice,jdbcType=VARCHAR}",
            "</if>",
            "<if test='keyImage != null'>",
            "AND keyImage = #{keyImage,jdbcType=VARCHAR}",
            "</if>",
            "<if test='baseImage != null'>",
            "AND baseImage = #{baseImage,jdbcType=VARCHAR}",
            "</if>",
            "<if test='confusionImage !=null'>",
            "AND confusionImage = #{confusionImage,jdbcType=VARCHAR}",
            "</if>",
            " order by id desc",
            "</script>"
    })
    List<SquirrelWord> selectBy(SquirrelWord record);

    @Select({
            "<script>",
            "select id, word, `translation`, voice, keyImage, baseImage, confusionImage",
            "from squirrel_words",
            "where delKey = 0",
            "<if test='w.word != null'>",
            "AND word LIKE CONCAT('%',#{w.word,jdbcType=VARCHAR},'%')",
            "</if>",
            "<if test='w.translation != null'>",
            "AND `translation` LIKE concat('%',#{w.translation,jdbcType=VARCHAR},'%')",
            "</if>",
            "order by id desc limit #{w.pageNo,jdbcType=INTEGER},#{w.pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelWord> selectAll(@Param("w")SquirrelWord squirrelWord);

    @Select({
            "<script>",
            "select count(id)",
            "from squirrel_words",
            "where delKey = 0",
            "<if test='w.word != null'>",
            "AND word LIKE CONCAT('%',#{w.word,jdbcType=VARCHAR},'%')",
            "</if>",
            "<if test='w.translation != null'>",
            "AND `translation` LIKE concat('%',#{w.translation,jdbcType=VARCHAR},'%')",
            "</if>",
            " order by id desc",
            "</script>"
    })
    int count(@Param("w")SquirrelWord squirrelWord);
}