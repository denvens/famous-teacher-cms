package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.wechat.WxShare;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelWxShareMapper {
    @Update({
            "<script>",
            "update squirrel_wx_share set delKey = now()",
            "where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteByPrimaryKey(Integer id);


    @Select({
            "<script>",
            "select id,url,spaceTitle,freTitle,content,img,type,shareContent",
            "from squirrel_wx_share",
            "where delKey = 0 order by id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<WxShare> selectAll(WxShare record);

    @Select({
            "<script>",
            "select id,url,spaceTitle,freTitle,content,img,channelId,channelQr,type,shareContent",
            "from squirrel_wx_share",
            "where delKey = 0 and id =#{id} order by id",
            "</script>"
    })
    WxShare selectByPrimaryKey(Integer id);

    @Insert({
            "<script>",
            "insert into squirrel_wx_share(url,spaceTitle,freTitle,content,img,delKey,channelId,channelQr,type,shareContent)",
            "values(#{p.url},#{p.spaceTitle},#{p.freTitle},#{p.content},#{p.img},'0',#{p.channelId},#{p.channelQr},#{p.type},#{p.shareContent})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insert(@Param("p") WxShare record);

    @Update({
            "<script>",
            "update squirrel_wx_share",
            "<set>",
            "<if test='url != null'>",
            "url = #{url},",
            "</if>",
            "<if test='spaceTitle != null'>",
            "spaceTitle = #{spaceTitle},",
            "</if>",
            "<if test='freTitle != null'>",
            "freTitle = #{freTitle},",
            "</if>",
            "<if test='content != null'>",
            "content = #{content},",
            "</if>",
            "<if test='img != null'>",
            "img = #{img},",
            "</if>",
            "<if test='type != null'>",
            "type = #{type},",
            "</if>",
            "<if test='shareContent != null'>",
            "shareContent = #{shareContent},",
            "</if>",
            "channelId = #{channelId},",
            "channelQr = #{channelQr},",
            "</set>",
            "where id=#{id}",
            "</script>"
    })
    int updateByPrimaryKey(WxShare record);

    @Select({
            "<script>",
            "select count(id) from squirrel_wx_share where delKey=0",
            "</script>"
    })
    int count();

    @Update({
            "<script>",
            "update ",
            "	squirrel_questions q ",
            "left join ",
            "	squirrel_units u on q.unitId = u.id",
            "set ",
            "	q.questionData = #{shareData}",
            "where",
            "	u.lessonId in(select id from squirrel_lessons where",
            "		levelId = #{levelId} and delKey = '0')",
            "	and q.delKey = '0' and u.delKey = '0' and questionType = 'share-punch'",
            "</script>"
    })
    int updateAllShareQuestion(@Param("levelId")Integer levelId, @Param("shareData")String shareData);


    @Select({
            "<script>",
            "select id,levelId,type,shareContent",
            "from wx_share_templates",
            "where levelId = #{levelId}",
            "</script>"
    })
    WxShare selectTemplateByLevelId(Integer levelId);

    @Insert({
            "<script>",
            "insert into wx_share_templates(levelId,type,shareContent,createdAt,updatedAt)",
            "values(#{levelId},#{type},#{shareContent},now(),now())",
            "</script>"
    })
    int insertTemplate(WxShare record);

    @Update({
            "<script>",
            "update wx_share_templates",
            "set shareContent = #{shareContent}",
            "where",
            "levelId = #{levelId}",
            "</script>"
    })
    int updateTemplate(@Param("levelId")Integer levelId, @Param("shareContent")String shareContent);
}
