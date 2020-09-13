package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelChannel;
import com.qingclass.squirrel.cms.entity.statistic.ChannelDetailStatistic;
import com.qingclass.squirrel.cms.entity.statistic.ChannelStatistic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelChannelMapper {

    @Select({
            "<script>",
            "select id,`name`,validity,createTime,updateTime,createBy,updateBy,type,messages,sendTime,ticket,expireSeconds,url,site,code,",
            "(select count(openId) from `squirrel`.`user_behavior` where type = 'subscribe' and subscribe = 1 and channelCode = `code`) as subscribeCount,",
            "(select count(distinct squirrelUserId) from `squirrel`.`user_levels` where channelCode = `code` and squirrelUserId in",
            "(select id from `squirrel`.`squirrel_users` where openId in",
            "(select openId from `squirrel`.`user_behavior` where type = 'subscribe' and channelCode = `code`))) as purchaseCount",
            "from squirrel_channel where delKey = '0' order by id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<SquirrelChannel> selectAll(SquirrelChannel squirrelChannel);

    @Select({
            "<script>",
            "select id,`name`,validity,createTime,updateTime,createBy,updateBy,type,messages,sendTime,ticket,expireSeconds,url,site",
            "from squirrel_channel where delKey = '0' order by id desc",
            "</script>"
    })
    List<SquirrelChannel> selectBase();

    @Select({
            "<script>",
            "select id,`name`,validity,createTime,updateTime,createBy,updateBy,type,messages,sendTime,ticket,expireSeconds,url,site",
            "from squirrel_channel where delKey = '0' and id = #{id}",
            "</script>"
    })
    SquirrelChannel selectByPrimaryKey(SquirrelChannel squirrelChannel);


    @Insert({
            "<script>",
            "insert into squirrel_channel(id,`name`,validity,createTime,updateTime,createBy,updateBy,type,messages,sendTime,ticket,expireSeconds,url,code,delKey,site)",
            "values(#{id},#{name},#{validity},now(),now(),#{createBy},#{updateBy},#{type},#{messages},#{sendTime},#{ticket},#{expireSeconds},#{url},#{code},'0',#{site})",
            "</script>"
    })
    int insert(SquirrelChannel channel);

    @Update({
            "<script>",
            "update squirrel_channel",
            "<set>",
            "<if test='name != null'>",
            "name = #{name},",
            "</if>",
            "<if test='messages != null'>",
            "messages = #{messages},",
            "</if>",
            "<if test='validity != null'>",
            "validity = #{validity},",
            "</if>",
            "<if test='sendTime != null'>",
            "sendTime = #{sendTime},",
            "</if>",
            "updateTime = now()",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKey(SquirrelChannel squirrelChannel);

    @Select("select count(id) from squirrel_channel where delKey = '0'")
    int count();


    @Select({
            "<script>",
            "select date_format(a.updatedAt,'%Y-%m-%d') as date,",
            "channelCode as code,",
            "count(a.openId) as subscribeCount,",
            "sum(a.subscribe = 0) as removeCount,",
            "sum(a.subscribe = 1) as clearCount,",
            "(select count(id) from `squirrel`.`user_levels`",
            "<where>",
            "levelId = 1000005 and channelCode = a.`channelCode` and squirrelUserId in(select id from `squirrel`.`squirrel_users` where openId in(",
            "select openId from `squirrel`.`user_behavior` b WHERE b.channelCode = #{channelCode} and b.updatedAt like concat(date_format(a.updatedAt,'%Y-%m-%d'),'%')",
            "))",
            "and createdAt between concat(date_format(a.updatedAt,'%Y-%m-%d'),' 00:00:00') and concat(date_format(a.updatedAt,'%Y-%m-%d'),' 23:59:59')",
            "</where>",
            ") as levelOne,",
            "(select count(id) from `squirrel`.`user_levels`",
            "<where>",
            "levelId = 1000006 and channelCode = a.`channelCode` and squirrelUserId in(select id from `squirrel`.`squirrel_users` where openId in(",
            "select openId from `squirrel`.`user_behavior` b WHERE b.channelCode = #{channelCode} and b.updatedAt like concat(date_format(a.updatedAt,'%Y-%m-%d'),'%')",
            "))",
            "and createdAt between concat(date_format(a.updatedAt,'%Y-%m-%d'),' 00:00:00') and concat(date_format(a.updatedAt,'%Y-%m-%d'),' 23:59:59')",
            "</where>",
            ") as levelTwo",
            "from `squirrel`.`user_behavior` a",
            "<where>",
            "a.channelCode = #{channelCode}",
            "<if test = 'param != null'>",
            "and a.updatedAt between #{beginTime} and #{endTime}",
            "</if>",
            "</where>",
            "group by date_format(a.updatedAt,'%Y-%m-%d')",
            "order by date desc",
            "limit #{pageNo},#{pageSize}",
            "</script>"
    })
    List<ChannelStatistic> selectChannelDetail(ChannelStatistic channelStatistic);

    @Select({
            "<script>",
            "select count(*) from",
            "(select date_format(a.updatedAt,'%Y-%m-%d')",
            "from `squirrel`.`user_behavior` a",
            "<where>",
            "a.channelCode = #{channelCode}",
            "<if test = 'param != null'>",
            "and a.updatedAt between #{beginTime} and #{endTime}",
            "</if>",
            "</where>",
            "group by date_format(a.updatedAt,'%Y-%m-%d')) T",
            "</script>"
    })
    int selectChannelDetailCount(ChannelStatistic channelStatistic);


    @Select({
            "<script>",
            "select su.nickName,su.headImgUrl,ub.openId,ub.subscribe,count(DISTINCT su.openId),",
            "(select if(count(id)&lt;&gt;0,1,0) from `squirrel`.`user_levels` ul",
            "<where>",
            "levelId = 1000005 and squirrelUserId = su.id and channelCode = #{code}",
            "<if test='param != null'>",
            "AND createdAt between #{beginTime} and #{endTime}",
            "</if>",
            "</where>",
            "limit 1) as levelOne,",
            "(select if(count(id)&lt;&gt;0,1,0) from `squirrel`.`user_levels` ul",
            "<where>",
            "levelId = 1000006 and squirrelUserId = su.id and channelCode = #{code}",
            "<if test='param != null'>",
            "AND createdAt between #{beginTime} and #{endTime}",
            "</if>",
            "</where>",
            "limit 1) as levelTwo",
            "from `squirrel`.`user_behavior` ub left join `squirrel`.`squirrel_users` su on ub.openId = su.openId",
            "left join `squirrel`.`user_levels` ul on su.id = ul.squirrelUserId",
            "<where>",
            "ub.channelCode = #{code}",
            "<if test='subscribe != null'>",
            "AND ub.subscribe = #{subscribe}",
            "</if>",
            "<if test='levelOne == 1 and levelTwo != 1'>",
            "AND ul.levelId = 1000005 and ul.channelCode = #{code} AND ul.createdAt between #{beginTime} and #{endTime}",
            "</if>",
            "<if test='levelOne == 1 and levelTwo == 1'>",
            "AND (select count(id) from `squirrel`.`user_levels` where createdAt between #{beginTime} and #{endTime} and squirrelUserId = ul.squirrelUserId and channelCode = #{code}) &gt; 1",
            "</if>",
            "<if test='levelOne == 0'>",
            "AND (ul.squirrelUserId not in(select squirrelUserId from `squirrel`.`user_levels` ul",
            "<where>",
            "levelId = 1000005 and channelCode = #{code}",
            "AND ul.createdAt between #{beginTime} and #{endTime}",
            "</where>",
            ") or ul.id is null)",
            "</if>",
            "<if test='levelTwo == 1 and levelOne != 1'>",
            "AND ul.levelId = 1000006 and ul.channelCode = #{code} AND ul.createdAt between #{beginTime} and #{endTime}",
            "</if>",
            "<if test='levelTwo == 0'>",
            "AND (ul.squirrelUserId not in(select squirrelUserId from `squirrel`.`user_levels` ul",
            "<where>",
            "levelId = 1000006 and channelCode = #{code}",
            "AND ul.createdAt between #{beginTime} and #{endTime}",
            "</where>",
            ") or ul.id is null)",
            "</if>",
            "<if test='param != null'>",
            "AND ub.updatedAt between #{beginTime} and #{endTime}",
            "</if>",
            "</where>",
            "group by su.openId",
            "order by ub.updatedAt desc",
            "limit #{pageNo},#{pageSize}",
            "</script>"
    })
    List<ChannelDetailStatistic> selectChannelDetailInfo(ChannelDetailStatistic channelDetailStatistic);


    @Select({
            "<script>",
            "select count(*) from (",
            "select count(DISTINCT su.openId)",
            "from `squirrel`.`user_behavior` ub left join `squirrel`.`squirrel_users` su on ub.openId = su.openId",
            "left join `squirrel`.`user_levels` ul on su.id = ul.squirrelUserId",
            "<where>",
            "ub.channelCode = #{code}",
            "<if test='subscribe != null'>",
            "AND ub.subscribe = #{subscribe}",
            "</if>",
            "<if test='levelOne == 1 and levelTwo != 1'>",
            "AND ul.levelId = 1000005 and ul.channelCode = #{code} AND ul.createdAt between #{beginTime} and #{endTime}",
            "</if>",
            "<if test='levelOne == 1 and levelTwo == 1'>",
            "AND (select count(id) from `squirrel`.`user_levels` where createdAt between #{beginTime} and #{endTime} and squirrelUserId = ul.squirrelUserId and channelCode = #{code}) &gt; 1",
            "</if>",
            "<if test='levelOne == 0'>",
            "AND (ul.squirrelUserId not in(select squirrelUserId from `squirrel`.`user_levels` ul",
            "<where>",
            "levelId = 1000005",
            "AND ul.createdAt between #{beginTime} and #{endTime}",
            "</where>",
            ") or ul.id is null)",
            "</if>",
            "<if test='levelTwo == 1 and levelOne != 1'>",
            "AND ul.levelId = 1000006 and ul.channelCode = #{code} AND ul.createdAt between #{beginTime} and #{endTime}",
            "</if>",
            "<if test='levelTwo == 0'>",
            "AND (ul.squirrelUserId not in(select squirrelUserId from `squirrel`.`user_levels` ul",
            "<where>",
            "levelId = 1000006",
            "AND ul.createdAt between #{beginTime} and #{endTime}",
            "</where>",
            ") or ul.id is null)",
            "</if>",
            "<if test='param != null'>",
            "AND ub.updatedAt between #{beginTime} and #{endTime}",
            "</if>",
            "</where>",
            "group by ub.openId",
            ") T",
            "</script>"
    })
    int selectChannelDetailInfoCount(ChannelDetailStatistic channelDetailStatistic);
}
