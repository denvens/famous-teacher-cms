package com.qingclass.squirrel.cms.mapper.cms;


import com.qingclass.squirrel.cms.entity.cms.SquirrelInvitationSetting;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipRecordReq;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipSetting;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipSettingReq;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelScholarshipSettingMapper {


    @Update({
            "<script>",
            "update squirrel_invitation_setting set isOpen = #{isOpen} where id = #{id}",
            "</script>"
    })
    void editStatus(SquirrelInvitationSetting squirrelInvitationSetting);

    
    //-----------------------------------------------------------------------------
    @Insert({
        "<script>",
        "insert into squirrel_scholarship_setting(",
        "		name, level_id, buy_start_time, buy_end_time, cash_back_type, ",
        "	<if test='p.amount != null'>",
        "		amount, ",
        "	</if>",
        "	<if test='p.ratio != null'>",
        "		ratio, ",
        "	</if>",
        "	created ",
        ")values(",
        "	#{p.name},#{p.levelId}, #{p.buyStartTime}, #{p.buyEndTime}, #{p.cashBackType}, ",
        "	<if test='p.amount != null'>",
        "		#{p.amount},",
        "	</if>",
        "	<if test='p.ratio != null'>",
        "		#{p.ratio}, ",
        "	</if>",
        "   now()",
        ")",
        "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    void insert(@Param("p") SquirrelScholarshipSettingReq squirrelScholarshipSettingReq);
    
    @Update({
            "<script>",
            "update squirrel_scholarship_setting",
            "<set>",
            "<if test='p.name != null'>",
            "name = #{p.name},",
            "</if>",
            "<if test='p.buyStartTime != null'>",
            "buy_start_time = #{p.buyStartTime},",
            "</if>",
            "<if test='p.buyEndTime != null'>",
            "buy_end_time = #{p.buyEndTime},",
            "</if>",
            "<if test='p.cashBackType != null'>",
            "cash_back_type = #{p.cashBackType},",
            "</if>",
            "<if test='p.amount != null'>",
            "amount = #{p.amount},",
            "</if>",
            "<if test='p.ratio != null'>",
            "ratio = #{p.ratio},",
            "</if>",
            "<if test='p.levelId != null'>",
            "level_id = #{p.levelId},",
            "</if>",
            "</set>",
            "where id = #{p.id}",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    void update(@Param("p") SquirrelScholarshipSettingReq squirrelScholarshipSettingReq);

    @Delete({
        "<script>",
        "delete from squirrel_scholarship_setting where id = #{id}",
        "</script>"
	})
	void delete(Integer id);
    
    @Select({
        "<script>",
        "select ",
        "	i.id, i.name, i.level_id levelId, ",
        "	i.buy_start_time buyStartTime, i.buy_end_time buyEndTime, ",
        "	i.cash_back_type cashBackType, i.amount, i.ratio, ",
        " 	i.created, i.updated,",
        " 	l.name as levelName " ,
        "from ",
        "	squirrel_scholarship_setting i ",
        "left join squirrel_levels l on i.level_id = l.id",
        "<where>",
        "<if test = 'levelId != null'>",
        "	i.level_id = #{levelId}	",
        "</if>",
        "<if test='buyStartTime != null '>",
        "	and i.buy_start_time &gt;= #{buyStartTime} ",
        "</if>",
        "<if test='buyEndTime != null'>",
        "	and i.buy_start_time &lt;= #{buyEndTime}",
        "</if>",
        "</where>",
        "	order by i.created desc ",
        "   limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER} ",
        "</script>"
    })
    List<SquirrelScholarshipSetting> list(SquirrelScholarshipSettingReq scholarshipReq);

    @Select({
        "<script>",
        "	select count(i.id) ",
        "	from squirrel_scholarship_setting i ",
        "	left join squirrel_levels l on i.level_id = l.id",
        "<where>",
        "<if test = 'levelId != null'>",
        "	i.level_id = #{levelId}",
        "</if>",
        "<if test='buyStartTime != null'>",
        "	and i.buy_start_time &gt;= #{buyStartTime} ",
        "</if>",
        "<if test='buyEndTime != null'>",
        "	and i.buy_start_time &lt;= #{buyEndTime} ",
        "</if>",
        "</where>",
        "</script>"
    })
    int listCount(SquirrelScholarshipSettingReq scholarshipReq);
    
    @Select({
        "<script>",
        "	select ",
        "		i.id, i.name, i.level_id levelId, ",
        "		i.buy_start_time buyStartTime, i.buy_end_time buyEndTime, ",
        "		i.cash_back_type cashBackType, i.amount, i.ratio, ",
        " 		i.created, i.updated,",
        " 		l.name as levelName " ,
        "	from squirrel_scholarship_setting i ",
        "	left join squirrel_levels l on i.level_id = l.id",
        "	where i.id = #{id}",
        "</script>"
    })
    SquirrelScholarshipSetting selectByPrimaryKey(Integer id);
    
    @Select({
        "<script>",
        "select ",
        "	count(i.id) " ,
        "from ",
        "	squirrel_scholarship_setting i ",
        "left join squirrel_levels l on i.level_id = l.id",
        "<where>",
        "<if test = 'levelId != null'>",
        "	i.level_id = #{levelId}	",
        "</if>",
        "<if test='buyStartTime != null and buyEndTime != null' >",
        "	and ( ",
        "		   ( i.buy_start_time &gt;= #{buyStartTime} ",
        "			and i.buy_start_time &lt;= #{buyEndTime}) ",
        "		or ( i.buy_end_time &gt;= #{buyStartTime} ",
        "			and i.buy_end_time &lt;= #{buyEndTime}) ",
        "		or ( i.buy_start_time &lt;= #{buyStartTime} ",
        "			and i.buy_end_time &gt;= #{buyEndTime}) ",
        "	)",
        "</if>",
        "<if test = 'id != null'>",
        "	and i.id != #{id}	",
        "</if>",
        "</where>",
        "	order by i.created desc ",
        "</script>"
    })
    int buyTimeRangeRepeatList(SquirrelScholarshipSettingReq scholarshipReq);
}
