
package com.qingclass.squirrel.cms.mapper.cms;


import com.qingclass.squirrel.cms.entity.cms.ConversionPush;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionPushMapper {

    @Select({
            "<script>",
            "select cp.*,sl.name as levelName from squirrel_conversion_push cp left join squirrel_levels sl on cp.levelId = sl.id order by cp.levelId",
            "</script>"
    })
    List<ConversionPush> selectAll();

    @Select({
            "<script>",
            "select * from squirrel_conversion_push where id = #{id}",
            "</script>"
    })
    ConversionPush selectByPrimaryKey(Integer id);

    @Insert({
            "<script>",
            "insert into squirrel_conversion_push(levelId,pushTime,customId,scope,isOpen,createdAt,updatedAt) values",
            "(#{levelId},#{pushTime},#{customId},#{scope},#{isOpen},now(),now())",
            "</script>"
    })
    void insert(ConversionPush conversionPush);

    @Update({
            "<script>",
            "update squirrel_conversion_push",
            "<set>",
            "levelId = #{levelId},",
            "<if test = 'pushTime != null'>",
            "pushTime = #{pushTime},",
            "</if>",
            "<if test = 'customId != null'>",
            "customId = #{customId},",
            "</if>",
            "<if test = 'scope != null'>",
            "scope = #{scope},",
            "</if>",
            "<if test = 'isOpen != null'>",
            "isOpen = #{isOpen},",
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    void update(ConversionPush conversionPush);


    @Update({
            "<script>",
            "update squirrel_conversion_push set",
            "isOpen = #{isOpen}",
            "where id = #{id}",
            "</script>"
    })
    void updateStatus(ConversionPush conversionPush);

    @Delete({
        "<script>",
        "delete from squirrel_conversion_push where id = #{id}",
        "</script>"
    })
    void delete(Integer id);

}
