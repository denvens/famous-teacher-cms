package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.wechat.WxCustom;
import com.qingclass.squirrel.cms.entity.wechat.WxTemplate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SquirrelWxCustomMapper {


    @Select({
            "<script>",
            "select id,type,content,isOpen from squirrel_wx_custom",
            "where type = #{type}",
            "</script>"
    })
    List<WxCustom> selectByType(String type);


    @Select({
            "<script>",
            "select id,type,content,isOpen from squirrel_wx_custom",
            "where id = #{id}",
            "</script>"
    })
    List<WxCustom> selectByPrimaryKey(Integer id);

    @Insert({
            "<script>",
            "insert into squirrel_wx_custom(type,content,isOpen)",
            "values(#{p.type},#{p.content},#{p.isOpen})",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    int insert(@Param("p") WxCustom template);

    @Update({
            "<script>",
            "update squirrel_wx_custom",
            "<set>",
            "<if test='content != null'>",
            "content = #{content}",
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKey(WxCustom template);

    @Update({
            "<script>",
            "update squirrel_wx_custom set",
            "<if test='isOpen != null'>",
            "isOpen = #{isOpen}",
            "</if>",
            "where id = #{id}",
            "</script>"
    })
    int updateOpenOrClose(WxCustom template);
}
