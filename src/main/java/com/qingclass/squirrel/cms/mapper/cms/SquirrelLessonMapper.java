package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.LessonMidPicturebook;
import com.qingclass.squirrel.cms.entity.cms.LessonMidWord;
import com.qingclass.squirrel.cms.entity.cms.SquirrelWord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.qingclass.squirrel.cms.entity.cms.SquirrelLesson;

import java.util.List;

@Repository
public interface SquirrelLessonMapper {
     
	@Select({
            "<script>",
            "select ",
            "	l.id, l.levelId, l.`name`, l.`order`, l.star, l.audition, l.lessonKey, ",
            "	l.isOpen, l.title, l.updateDate, l.image, l.share_image shareImage, l.relation, m.picId, m.part",
            "from ",
            "	squirrel_lessons l left join lessons_mid_picturebooks m on l.id = m.lessonId",
            "where ",
            "	l.id = #{id,jdbcType =INTEGER} order by id desc",
            "</script>"
    })
    SquirrelLesson selectByPrimaryKey(Integer id);


    @Update({
            "<script>",
            "update squirrel_lessons",
            "<set>",
            "<if test='levelid != null'>",
            "levelId = #{levelid,jdbcType=INTEGER},",
            "</if>",
            "<if test='name != null'>",
            "`name` = #{name,jdbcType=VARCHAR},",
            "</if>",
            "<if test='order != null'>",
            "`order` = #{order,jdbcType=INTEGER},",
            "</if>",
            "<if test='star != null'>",
            "star = #{star,jdbcType=INTEGER},",
            "</if>",
            "<if test='title != null'>",
            "title = #{title},",
            "</if>",
            "<if test='lessonkey != null'>",
            "lessonKey = #{lessonkey,jdbcType=VARCHAR},",
            "</if>",
            "<if test='isOpen != null'>",
            "isOpen = #{isOpen,jdbcType=INTEGER},",
            "</if>",
            "<if test='relation != null'>",
            "relation = #{relation,jdbcType=INTEGER},",
            "</if>",
            "<if test='updateDate != null'>",
            "updateDate = #{updateDate},",
            "</if>",
            "<if test='image != null'>",
            "image = #{image,jdbcType=VARCHAR},",
            "</if>",
            "<if test='shareImage != null'>",
            "share_image = #{shareImage, jdbcType=VARCHAR}",
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKeySelective(SquirrelLesson record);

    int updateByPrimaryKey(SquirrelLesson record);

    @Update({
            "<script>",
            "update squirrel_lessons l LEFT JOIN squirrel_units u on l.id=u.lessonId",
            "LEFT JOIN squirrel_questions q on u.id=q.unitId set l.delKey= now(),",
            "u.delKey=now(),q.delKey=now() where l.id = #{id}",
            "</script>"
    })
    int deleteByPrimaryKey(@Param("id") Integer id);



    @Select({
            "<script>",
            "select id, levelId, `name`, `order`, star, audition, lessonKey, isOpen, title, updateDate, image,relation",
            "from squirrel_lessons",
            "where delKey = 0 order by id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelLesson> selectAll(@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);

    @Select({
            "<script>",
            "select id, levelId, `name`, `order`, audition, lessonKey, isOpen, title, updateDate, image,relation",
            "from squirrel_lessons",
            "where delKey = 0",
            "AND levelId = #{levelid,jdbcType=INTEGER}",
            "<if test='name != null'>",
            "AND `name` = #{name,jdbcType=VARCHAR} ",
            "</if>",
            "<if test='audition != null'>",
            "AND audition = #{audition,jdbcType=VARCHAR}",
            "</if>",
            " order by `order` desc",
            "</script>"
    })
    List<SquirrelLesson> selectBy(SquirrelLesson record);

    @Select({
            "<script>",
            "select ",
            "	l.id, l.levelId, l.`name`, l.`order`, l.audition, l.lessonKey, l.isOpen, l.title, l.updateDate, ",
            "	l.image, l.share_image shareImage, l.relation ",
            "from ",
            "	squirrel_lessons l ",
            "where l.delKey = '0'",
            "AND l.levelId = #{levelid,jdbcType=INTEGER}",
            "<if test='name != null'>",
            "AND l.`name` = #{name,jdbcType=VARCHAR} ",
            "</if>",
            "<if test='audition != null'>",
            "AND l.audition = #{audition,jdbcType=VARCHAR}",
            "</if>",
            " order by `order` desc",
            "limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelLesson> selectByPage(SquirrelLesson record);

    @Select({
            "<script>",
            "select count(id)",
            "from squirrel_lessons",
            "where delKey = 0 and levelId = #{levelId}",
            "<if test='audition != null'>",
            "AND audition = #{audition,jdbcType=VARCHAR}",
            "</if>",
            "</script>"
    })
    int count(@Param("levelId") Integer levelId,@Param("audition") Integer audition);

    @Insert({
            "<script>",
            "insert into squirrel_lessons (",
            "	id, levelId, `name`,",
            "	`order`, lessonKey, audition, isOpen, title, updateDate, image, share_image, relation )",
            "values (",
            "	#{p.id,jdbcType=INTEGER}, #{p.levelid,jdbcType=INTEGER}, #{p.name,jdbcType=VARCHAR},",
            "	#{p.order,jdbcType=INTEGER}, #{p.lessonkey,jdbcType=VARCHAR}, #{p.audition,jdbcType=VARCHAR},",
            "	#{p.isOpen,jdbcType=INTEGER},#{p.title,jdbcType=VARCHAR},#{p.updateDate},", 
            "	#{p.image,jdbcType=VARCHAR}, #{p.shareImage,jdbcType=VARCHAR}, #{p.relation})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insert(@Param("p") SquirrelLesson record);


    @Insert({
            "<script>",
            "insert into squirrel_lessons (levelId, `name`,",
            "`order`, lessonKey, audition, isOpen, title, updateDate, image,relation)",
            "values (#{p.levelid,jdbcType=INTEGER}, #{p.name,jdbcType=VARCHAR},",
            "#{p.order,jdbcType=INTEGER}, #{p.lessonkey,jdbcType=VARCHAR}, #{p.audition,jdbcType=VARCHAR},",
            "#{p.isOpen,jdbcType=INTEGER},#{p.title,jdbcType=VARCHAR},#{p.updateDate},#{p.image,jdbcType=VARCHAR},#{p.relation})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insertReturnId(@Param("p") SquirrelLesson record);

    @Select({
            "<script>",
            "select t.id as id,t.`name` as `name`,lessonKey, t.isOpen, t.title, t.updateDate, t.image from",
            "(select a.id,a.lessonKey,a.`name`, a.isOpen, a.title, a.updateDate, a.image from squirrel_lessons a",
            "LEFT JOIN squirrel_units b on a.id = b.lessonId where b.name = #{unitName,jdbcType=VARCHAR} and b.lessonId = #{lessonId,jdbcType=INTEGER} ) t",
            "</script>"
    })
    SquirrelLesson selectByUnitName(@Param("unitName") String unitName, @Param("lessonId") Integer lessonId);  //根据 unitId 查询父级

    @Select({
            "<script>",
            "select t.id as id,t.`name` as `name`,lessonKey, t.isOpen, t.title, t.updateDate, t.image from",
            "(select a.id,a.lessonKey,a.`name`, a.isOpen, a.title, a.updateDate, a.image from squirrel_lessons a",
            "LEFT JOIN squirrel_units b on a.id = b.lessonId where b.id = #{id,jdbcType=INTEGER} ) t",
            "</script>"
    })
    SquirrelLesson selectByUnitId(@Param("id") Integer id);  //根据 unitId 查询父级

    @Select({
            "<script>",
            "select t.id as id,t.`name` as `name`,lessonKey, t.isOpen, t.title, t.updateDate, t.image from",
            "(select a.id,a.lessonKey,a.`name`, a.isOpen, a.title, a.updateDate, a.image from squirrel_lessons a",
            "LEFT JOIN squirrel_units b on a.id = b.lessonId where b.id in",
            "(select unitId from squirrel_questions where questionType = #{questionType,jdbcType=VARCHAR} AND unitId = #{unitId,jdbcType=INTEGER})) t",
            "</script>"
    })
    SquirrelLesson selectByQuestionTypeAndUnitId(@Param("questionType") String questionType,@Param("unitId") Integer unitId);    //根据 questionId 查询父级以及父父级

    @Select({
            "<script>",
            "select ",
            "	t.id as id,t.`name` as `name`,lessonKey, ",
            "	t.isOpen, t.title, t.updateDate, t.image ",
            "from (",
            "	select a.id,a.lessonKey,a.`name`, a.isOpen, a.title, ",
            "		a.updateDate, a.image ",
            "	from squirrel_lessons a",
            "	where a.id in (",
            "		select lesson_id from squirrel_questions where id = #{id,jdbcType=INTEGER})",
            ") t ",
            "</script>"
    })
    SquirrelLesson selectByQuestionId(@Param("id")Integer id);    //根据 questionId 查询父级以及父父级

    @Select({
            "<script>",
            "select a.id, a.word, a.translation, a.voice, a.keyImage, a.baseImage, a.confusionImage, b.isKey from squirrel_words a ",
            "inner join lessons_mid_words b on a.id = b.wordId",
            "where a.id in (select wordId from lessons_mid_words where lessonId = #{lessonId,jdbcType=INTEGER})",
            "and b.lessonId = #{lessonId,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelWord> getWords(Integer lessonId);  //取lesson下的词库

    @Insert({
            "<script>",
            "insert into lessons_mid_words(lessonId,wordId)",
            "values",
            "<foreach collection='wordsList' item='words' index= 'index' separator =','>",
            "(",
            "#{words.lessonId,jdbcType=INTEGER},#{words.wordId,jdbcType=INTEGER}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertWords(@Param("wordsList")List<LessonMidWord> words);

    @Select({
            "<script>",
            "select id, levelId, `name`, `order`, star, audition, lessonKey, isOpen, title, updateDate, image from squirrel_lessons where id in",
            "(select lessonId from lessons_mid_words where wordId=#{wordId})",
            "</script>"
    })
    List<SquirrelLesson> selectLessonByWordId(Integer wordId);

    @Update({
            "<script>",
            "update lessons_mid_words set isKey = #{isKey} where lessonId = #{lessonId,jdbcType=INTEGER} and wordId = #{wordId,jdbcType=INTEGER}",
            "</script>"
    })
    int updateWord(@Param("lessonId")Integer lessonId, @Param("wordId")Integer wordId, @Param("isKey")Integer isKey);

    @Delete({
            "<script>",
            "delete from lessons_mid_words",
            "where lessonId = #{lessonId,jdbcType=INTEGER} and wordId = #{wordId,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteWord(@Param("lessonId")Integer lessonId, @Param("wordId")Integer wordId);


    @Insert({
            "<script>",
            "insert into lessons_mid_picturebooks(lessonId,picId,part)",
            "values(#{lessonId},#{picId},#{part})",
            "</script>"
    })
    int relationPicturebook(@Param("lessonId")Integer lessonId,@Param("picId")Integer picturebookId,@Param("part")Integer part);

    @Select({
            "<script>",
            "select id,lessonId,picId,part from lessons_mid_picturebooks where lessonId = #{lessonId}",
            "</script>"
    })
    List<LessonMidPicturebook> selectMidPicByLessonId(@Param("lessonId")Integer lessonId);

    @Select({
            "<script>",
            "select lmp.id,lmp.lessonId,lmp.picId,lmp.part,sp.name from lessons_mid_picturebooks lmp inner join squirrel_picturebook sp on lmp.picId = sp.id where lmp.lessonId = #{lessonId}",
            "</script>"
    })
    List<LessonMidPicturebook> selectPicNameAndMidPicByLessonId(@Param("lessonId")Integer lessonId);

    @Update({
            "<script>",
            "update lessons_mid_picturebooks",
            "<set>",
            "picId = #{picId},part = #{part}",
            "</set>",
            "where lessonId = #{lessonId}",
            "</script>"
    })
    int updateMidPic(@Param("lessonId")Integer lessonId,@Param("picId")Integer picturebookId,@Param("part")Integer part);

    @Select({
        "<script>",
        "SELECT id,`order` from squirrel_lessons a where a.order = #{order} and a.levelId = #{levelId} and a.audition = 0 and a.delKey = '0' and isOpen='1' limit 1",
        "</script>"
    })
	SquirrelLesson selectByCondition(@Param("order") int order, @Param("levelId") Integer levelId);

    @Select({
        "<script>",
        "SELECT " +
        "	min(`order`) " + 
        "from " +
        "	msyb_resource.squirrel_lessons a " +
        "where  " +
        "	a.levelId = #{levelId} " +
        "	and delKey = '0' " + 
        "	and a.audition = '0' " + 
        "	and isOpen='1' ",
        "</script>"
    })
	Integer selectMinOrder(@Param("levelId") Integer levelId);
    
    @Select({
        "<script>",
        "SELECT " +
        "	max(`order`) " + 
        "from " +
        "	msyb_resource.squirrel_lessons a " +
        "where  " +
        "	a.levelId = #{levelId} " +
        "	and delKey = '0' " + 
        "	and a.audition = '0' " + 
        "	and isOpen='1' ",
        "</script>"
    })
	Integer selectMaxOrder(@Param("levelId") Integer levelId);
}