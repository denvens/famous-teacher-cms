package com.qingclass.squirrel.cms.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.qingclass.squirrel.cms.entity.user.User;
import com.qingclass.squirrel.cms.entity.user.UserLevel;

@Repository
public interface UserMapper {

    @Select({
            "<script>",
            "select id,openId,nickName,sex,headImgUrl from squirrel_users ",
            "</script>"
    })
    List<User> selectAll();

    @Select({
            "<script>",
            "select id,openId,nickName,sex,headImgUrl from squirrel_users where openId = #{openId}",
            "</script>"
    })
    User selectByOpenId(String openId);

    @Select({
            "<script>",
            "select u.id,u.openId,u.nickName,u.sex,u.headImgUrl,u.isVip,u.subscribe,l.beginAt,",
            "	sl.name levelName, l.levelId ",
            "from squirrel_users u ",
            "left join user_levels l on u.id = l.squirrelUserId ",
            "left join msyb_resource.squirrel_levels sl  on sl.id = l.levelId ",
            "where 1=1 ",
            "<if test='user.levelId != null and user.levelId != 0'>",
            "	AND l.levelId = #{user.levelId}",
            "</if>",
            "<if test='user.nickName != null'>",
            "and concat(u.nickName,u.openId) like CONCAT(CONCAT('%',#{user.nickName,jdbcType=VARCHAR}),'%')",
            "</if>",
            "<if test ='user.subscribe != null'>",
            "and u.subscribe = #{user.subscribe}",
            "</if>",
            "<if test = 'user.vipBeginTime != null'>",
            "and l.beginAt between #{user.vipBeginTime} and #{user.vipEndTime}",
            "</if>",
            "limit #{user.pageNo},#{user.pageSize}",
            "</script>"
    })
    List<User> selectByPage(@Param("user") User user);

    @Select({
            "<script>",
            "select ",
            "	u.id,u.openId,u.nickName,u.sex,u.headImgUrl,u.isVip,u.subscribe,l.beginAt,",
            " 	sl.name levelName, l.levelId ",
            "from squirrel_users u ",
            "left join user_levels l  on u.id = l.squirrelUserId ",
            "left join msyb_resource.squirrel_levels sl  on sl.id = l.levelId ",
            "where 1=1 ",
            "<if test='user.levelId != null and user.levelId != 0'>",
            "	AND l.levelId = #{user.levelId}",
            "</if>",
            "<if test='user.nickName != null'>",
            "and concat(u.nickName,u.openId) like CONCAT(CONCAT('%',#{user.nickName,jdbcType=VARCHAR}),'%')",
            "</if>",
            "<if test ='user.subscribe != null'>",
            "and u.subscribe = #{user.subscribe}",
            "</if>",
            "<if test = 'user.vipBeginTime != null'>",
            "and l.beginAt between #{user.vipBeginTime} and #{user.vipEndTime}",
            "</if>",
            "</script>"
    })
    List<User> selectForExcel(@Param("user") User user);


    @Select({
            "<script>",
            "select ",
            "	u.id,u.openId,u.nickName,u.sex,u.headImgUrl,u.isVip,u.subscribe,",
            "	l.levelId,l.createdAt,l.vipBeginTime,l.vipEndTime ",
            "from squirrel_users u ",
            "left join user_levels l ",
            "on u.id = l.squirrelUserId ",
            "<if test='user.levelId != null and user.levelId != 0'>",
            "	AND l.levelId = #{user.levelId}",
            "</if>",
            "<where>",
            "<if test='user.nickName != null'>",
            "and concat(u.nickName,u.openId) like CONCAT(CONCAT('%',#{user.nickName,jdbcType=VARCHAR}),'%')",
            "</if>",
            "<if test='user.createdAt != null'>",
            "and l.createdAt between #{user.createdAtA} and #{user.createdAtB}",
            "</if>",
            "<if test='user.isVip == null'>",
            "and (l.levelId = #{user.levelId} or l.levelId is null)",
            "</if>",
            "<if test='user.isVip == 1'>",
            "and (l.levelId = #{user.levelId}) and l.vipEndTime &gt;= now()",
            "</if>",
            "<if test='user.isVip == 0'>",
            "and (l.levelId is null or l.levelId &lt;&gt; #{user.levelId} or l.vipEndTime &lt; now())",
            "</if>",
            "<if test='user.vipBeginTime != null'>",
            "and l.vipBeginTime between #{user.vipBeginTimeA} and #{user.vipBeginTimeB}",
            "</if>",
            "<if test='user.vipEndTime != null'>",
            "and l.vipEndTime between #{user.vipEndTimeA} and #{user.vipEndTimeB}",
            "</if>",
            "<if test ='user.subscribe != null'>",
            "and u.subscribe = #{user.subscribe}",
            "</if>",
            "</where>",
            "limit #{user.pageNo},#{user.pageSize}",
            "</script>"
    })
    List<User> selectUserListByPage(@Param("user") User user);


    @Select({
            "<script>",
            "select count(u.id) from squirrel_users u left join user_levels l",
            "on u.id = l.squirrelUserId ",
            "where 1=1 ",
            "<if test='user.levelId != null and user.levelId != 0'>",
            "	AND l.levelId = #{user.levelId}",
            "</if>",
            "<if test='user.nickName != null'>",
            "and concat(u.nickName,u.openId) like CONCAT(CONCAT('%',#{user.nickName,jdbcType=VARCHAR}),'%')",
            "</if>",
            "<if test ='user.subscribe != null'>",
            "and u.subscribe = #{user.subscribe}",
            "</if>",
            "<if test = 'user.vipBeginTime != null'>",
            "and l.beginAt between #{user.vipBeginTime} and #{user.vipEndTime}",
            "</if>",
            "</script>"
    })
    int count(@Param("user") User user);

    @Select({
            "<script>",
            "select count(u.id) from squirrel_users u left join user_levels l ",
            "on u.id = l.squirrelUserId ",
            "<if test='user.levelId != null and user.levelId != 0'>",
            "	AND l.levelId = #{user.levelId}",
            "</if>",
            "where 1=1",
            "<if test='user.nickName != null'>",
            "and concat(u.nickName,u.openId) like CONCAT(CONCAT('%',#{user.nickName,jdbcType=VARCHAR}),'%')",
            "</if>",
            "<if test='user.createdAt != null'>",
            "and l.createdAt between #{user.createdAtA} and #{user.createdAtB}",
            "</if>",
            "<if test='user.isVip == null'>",
            "and (l.levelId = #{user.levelId} or l.levelId is null)",
            "</if>",
            "<if test='user.isVip == 1'>",
            "and (l.levelId = #{user.levelId})",
            "</if>",
            "<if test='user.isVip == 0'>",
            "and (l.levelId is null or l.levelId &lt;&gt; #{user.levelId})",
            "</if>",
            "<if test='user.vipBeginTime != null'>",
            "and l.vipBeginTime between #{user.vipBeginTimeA} and #{user.vipBeginTimeB}",
            "</if>",
            "<if test='user.vipEndTime != null'>",
            "and l.vipEndTime between #{user.vipEndTimeA} and #{user.vipEndTimeB}",
            "</if>",
            "<if test ='user.subscribe != null'>",
            "and u.subscribe = #{user.subscribe}",
            "</if>",
            "</script>"
    })
    int userListCount(@Param("user") User user);



    @Insert({
            "<script>",
            "insert into user_levels(squirrelUserId,levelId)",
            "values(#{openId,jdbcType=VARCHAR},#{levelId,jdbcType=INTEGER})",
            "</script>"
    })
    int insertUserLevel(@Param(value = "openId")String openId, @Param(value = "levelId")Integer levelId);

    @Select({
            "<script>",
            "select id,squirrelUserId,levelId,transactionId,beginAt,vipBeginTime,vipEndTime",
            "from user_levels where squirrelUserId = #{userId} and delKey = '0'",
            "</script>"
    })
    List<UserLevel> selectUserLevels(@Param(value = "userId")int userId);

    @Select({
            "<script>",
            "select id,squirrelUserId,levelId,transactionId,beginAt,vipBeginTime,vipEndTime",
            "from user_levels where delKey = '0' and squirrelUserId in ",
            "<foreach item='item' index='index' collection='userIds' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "and levelId = #{levelId}",
            "</script>"
    })
    List<UserLevel> selectUserLevelsByUserIds(@Param(value = "userIds")List<Integer> userIds,@Param("levelId")Integer levelId);

    @Select({
            "<script>",
            "select id,squirrelUserId,levelId,transactionId,beginAt,vipBeginTime,vipEndTime",
            "from user_levels where delKey = '0' and levelId = #{levelId}",
            "and squirrelUserId in(select id from squirrel_users where openId = #{openId}) and",
            "((vipBeginTime &lt;= now() and vipEndTime &gt;= now()) or vipBeginTime > now())",
            "</script>"
    })
    List<UserLevel> selectUserPurchaseRecordByOpenIdAndLevelId(@Param("openId")String openId,@Param("levelId")Integer levelId);



    @Insert({ "<script>", "insert into user_levels(squirrelUserId,levelId,transactionId,createdAt,beginAt,vipBeginTime,vipEndTime,sendLessonDays)",
            "values(#{squirrelUserId,jdbcType=VARCHAR},#{levelId,jdbcType=INTEGER},#{transactionId,jdbcType=INTEGER},now(),#{beginAt,jdbcType=VARCHAR},#{vipBeginTime},#{vipEndTime},#{sendLessonCount})",
            "</script>" })
    int insertUserLevels(@Param(value = "squirrelUserId") int squirrelUserId, @Param(value = "levelId") Integer levelId,
                         @Param(value = "transactionId") Integer transactionId, @Param(value = "beginAt") String beginAt,
                         @Param(value = "vipBeginTime")String vipBeginTime,
                         @Param(value = "vipEndTime")String vipEndTime, @Param(value = "sendLessonCount")Integer sendLessonCount);

    @Update({
            "<script>",
            "update user_levels",
            "<set>",
            "<if test='beginAt != null'>",
            "beginAt = #{beginAt},",
            "</if>",
            "<if test='vipBeginTime != null'>",
            "vipBeginTime = #{vipBeginTime},",
            "</if>",
            "<if test='vipEndTime != null'>",
            "vipEndTime = #{vipEndTime},",
            "</if>",
            "<if test='sendLessonCount != null'>",
            "sendLessonDays = #{sendLessonCount},",
            "</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int updateUserLevel(@Param(value = "beginAt") String beginAt, @Param(value = "vipBeginTime")String vipBeginTime,
                        @Param(value = "vipEndTime")String vipEndTime,@Param(value = "id")Integer id,
                        @Param(value = "sendLessonCount")Integer sendLessonCount);

    @Update({
            "<script>",
            "update user_levels",
            "<set>",
            "<if test='beginAt != null'>",
            "	beginAt = #{beginAt},",
            "</if>",
            "<if test='vipBeginTime != null'>",
            "	vipBeginTime = #{vipBeginTime},",
            "</if>",
            "<if test='vipEndTime != null'>",
            "	vipEndTime = #{vipEndTime},",
            "</if>",
            "<if test='sendLessonCount != null'>",
            "	sendLessonDays = #{sendLessonCount},",
            "</if>",
            "</set>",
            "where squirrelUserId = #{userId} and levelId = #{levelId}",
            "</script>"
    })
    int editUserLevel(@Param(value = "beginAt") String beginAt, @Param(value = "vipBeginTime")String vipBeginTime,
                        @Param(value = "vipEndTime")String vipEndTime,@Param(value = "userId")Integer userId,
                        @Param(value = "sendLessonCount")Integer sendLessonCount,@Param(value = "levelId")Integer levelId);


    @Select("select * from squirrel_users a where a.id=#{userId}")
	Map<String,Object> selectById(@Param(value = "userId") Integer userId);

    @Select({
        "<script>",
        "select id,squirrelUserId,levelId,transactionId,beginAt,vipBeginTime,vipEndTime",
        "from user_levels ",
        "where ",
        "	squirrelUserId = #{userId} ",
        "	and levelId= #{levelId} ",
        "	and delKey = '0' ",
        "order by id desc ",
        "limit 1",
        "</script>"
	})
    UserLevel selectUserLevel(@Param(value = "userId")int userId, @Param(value = "levelId") Integer levelId);



    @Delete({
            "<script>",
            "delete from user_levels",
            "where squirrelUserId in(select id from squirrel_users where openId = #{openId})",
            "</script>"
    })
    void deletePurchase(String openId);

    @Delete({
            "<script>",
            "delete from users_lesson_remind",
            "where userId in(select id from squirrel_users where openId = #{openId})",
            "</script>"
    })
    void deleteRemind(String openId);
    
    @Select({
        "<script>",
        "	select ",
        "		ul.beginAt  ",
        "	from ",
        "		squirrel.squirrel_users u  ",
        "	left join 	",
        "		squirrel.user_levels ul on ul.squirrelUserId=u.id  ",
        "	where ",
        "		u.openId = #{openId}	",
        "		and ul.levelId = #{levelId}  ",
        "</script>"
    })
    UserLevel selectUserLevelsByOpenId(@Param(value = "openId")String openId, 
    		@Param(value = "levelId") Integer levelId);
}
