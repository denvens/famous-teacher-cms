package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelLessonRemind;
import com.qingclass.squirrel.cms.entity.wechat.WxTemplate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SquirrelWxTemplateMapper {


    @Select({
            "<script>",
            "select id,type,content,isOpen,url from squirrel_wx_templates",
            "where type = #{type}",
            "</script>"
    })
    List<WxTemplate> selectByType(String type);

    @Select({
            "<script>",
            "select id,type,content,isOpen,url from squirrel_wx_templates",
            "where id = #{id}",
            "</script>"
    })
    List<WxTemplate> selectByPrimaryKey(Integer id);

    @Insert({
            "<script>",
            "insert into squirrel_wx_templates(type,content,isOpen,url)",
            "values(#{p.type},#{p.content},#{p.isOpen},#{p.url})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insert(@Param("p") WxTemplate template);

    @Update({
            "<script>",
            "update squirrel_wx_templates",
            "<set>",
            "<if test='content != null'>",
            "content = #{content}",
            "</if>",
            "<if test='url != null'>",
            ",url = #{url}",
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKey(WxTemplate template);

    @Update({
            "<script>",
            "update squirrel_wx_templates set",
            "<if test='isOpen != null'>",
            "isOpen = #{isOpen}",
            "</if>",
            "where id = #{id}",
            "</script>"
    })
    int updateOpenOrClose(WxTemplate template);

    /**
     *
     * */
    @Insert({
            "<script>",
            "insert into squirrel_lesson_remind(remindRate,firstRemind,secondRemind,createdAt,updatedAt,type) values",
            "(#{remindRate},#{firstRemind},#{secondRemind},now(),now(),#{type})",
            "</script>"
    })
    int insertRemindTime(@Param("remindRate")String remindRate, @Param("firstRemind")String firstRemind,
                         @Param("secondRemind")String secondRemind, @Param("type")String type);

    /**
     *
     * */
    @Insert({
            "<script>",
            "insert into squirrel_lesson_remind(remindRate,firstRemind,secondRemind,createdAt,updatedAt,type) values",
            "(#{remindRate},#{firstRemind},'00:00',now(),now(),#{type})",
            "</script>"
    })
    int insertRemindTimeClass(@Param("remindRate")String remindRate, @Param("firstRemind")String firstRemind, @Param("type")String type);

    @Update({
            "<script>",
            "update squirrel_lesson_remind set remindRate = #{remindRate},firstRemind=#{firstRemind},secondRemind=#{secondRemind}",
            "where id=#{id}",
            "</script>"
    })
    int updateRemindTime(@Param("id")Integer id,
                         @Param("remindRate")String remindRate, @Param("firstRemind")String firstRemind,
                         @Param("secondRemind")String secondRemind);

    @Update({
            "<script>",
            "update squirrel_lesson_remind set remindRate = #{remindRate},firstRemind=#{firstRemind},secondRemind='00:00'",
            "where id=#{id}",
            "</script>"
    })
    int updateRemindTimeClass(@Param("id")Integer id,
                         @Param("remindRate")String remindRate, @Param("firstRemind")String firstRemind);

    /**
     *
     * */
    @Select({
            "<script>",
            "select id,remindRate,firstRemind,secondRemind,type",
            "from squirrel_lesson_remind",
            "where type = #{type}",
            "</script>"
    })
    SquirrelLessonRemind selectRemindTimeDefault(String type);
}
