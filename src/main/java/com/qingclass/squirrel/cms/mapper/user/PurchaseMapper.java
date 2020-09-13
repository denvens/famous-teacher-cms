package com.qingclass.squirrel.cms.mapper.user;

import com.qingclass.squirrel.cms.entity.user.InvitationRecord;
import com.qingclass.squirrel.cms.entity.user.UserLevel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseMapper {

    @Select({
            "<script>",
            "select ",
            "	ul.id,ul.levelId,ul.createdAt,ul.beginAt,ul.vipBeginTime,",
            "	ul.vipEndTime,ul.delKey,u.openId,u.nickName,u.headImgUrl ",
            "from 	user_levels ul ",
            "left 	join squirrel_users u on ul.squirrelUserId = u.id ",
            "where ul.delKey = '0'",
            "<if test='levelId != null and levelId != 0'>",
            "AND ul.levelId = #{levelId}",
            "</if>",
            "<if test='findParam != null'>",
            "AND concat(u.nickName,u.openId) LIKE concat('%',#{findParam},'%')",
            "</if>",
            "order by createdAt desc",
            "limit #{pageNo},#{pageSize}",
            "</script>"
    })
    List<UserLevel> selectAllPurchaseRecord(UserLevel userLevel);  //查找所有购买记录，分页，模糊查询

    @Select({
            "<script>",
            "select ",
            "	ul.id,ul.levelId,ul.createdAt,ul.beginAt,ul.vipBeginTime,ul.vipEndTime,ul.delKey,",
            "	u.openId,u.nickName,u.headImgUrl,u.id as userId,ul.sendLessonDays as sendLessonCount ",
            "from user_levels ul ",
            "left join squirrel_users u ",
            "	on ul.squirrelUserId = u.id ",
            "where ",
            "	ul.delKey = '0' ",
            "	and u.openId = #{openId} ",
            "	and levelId = #{levelId}",
            "order by createdAt desc",
            "</script>"
    })
    UserLevel selectPurchaseRecordByOpenId(@Param("openId")String openId,@Param("levelId")Integer levelId);  //查找所有购买记录，分页，模糊查询

    @Select({
            "<script>",
            "select count(ul.id) ",
            "from user_levels ul ",
            "left join squirrel_users u on ul.squirrelUserId = u.id ",
            "where ul.delKey = '0'",
            "<if test='levelId != null and levelId != 0'>",
            "AND ul.levelId = #{levelId}",
            "</if>",
            "<if test='findParam != null'>",
            "AND concat(u.nickName,u.openId) LIKE concat('%',#{findParam},'%')",
            "</if>",
            "</script>"
    })
    int selectAllPurchaseRecordCount(UserLevel userLevel);


    @Select({
            "<script>",
            "select pr.id,pr.levelId,u1.nickName as invitationNickName,",
            "	u1.openId as invitationOpenId,	",
            "	u1.headImgUrl as invitationImg, ",
            "	u2.nickName as purchaseNickName,	",
            "	u2.openId as purchaseOpenId,",
            "	u2.headImgUrl as purchaseImg,	",
            "	pr.createdAt as createdAtA, ",
            "	pr.invitation_Type invitationType, ",
            "	pr.status status ",
            "from invitation_purchase_record pr",
            "	left join squirrel_users u1 on pr.invitationUserId = u1.id",
            "	left join squirrel_users u2 on pr.purchaseUserId = u2.id",
            "<where>",
            "<if test = 'invitationType != null'>",
            "	AND pr.invitation_Type = #{invitationType}",
            "</if>",
            "<if test = 'levelId != null'>",
            "	AND pr.levelId = #{levelId}",
            "</if>",
            "<if test = 'invitationParams != null'>",
            "	AND concat(u1.nickName,u1.openId) like concat('%',#{invitationParams},'%')",
            "</if>",
            "<if test = 'purchaseParams != null'>",
            "	AND concat(u2.nickName,u2.openId) like concat('%',#{purchaseParams},'%')",
            "</if>",
            "<if test = 'createdAt != null'>",
            "	AND pr.createdAt between #{createdAtA} and #{createdAtB}",
            "</if>",
            "</where>",
            "	order by pr.createdAt desc",
            "	limit #{pageNo},#{pageSize}",
            "</script>"
    })
    List<InvitationRecord> selectInvitationRecords(InvitationRecord invitationRecord);

    @Select({
            "<script>",
            "select count(pr.id) from invitation_purchase_record pr",
            "left join squirrel_users u1 on pr.invitationUserId = u1.id",
            "left join squirrel_users u2 on pr.purchaseUserId = u2.id",
            "<where>",
            "<if test = 'invitationType != null'>",
            "AND pr.invitation_Type = #{invitationType}",
            "</if>",
            "<if test = 'levelId != null'>",
            "AND pr.levelId = #{levelId}",
            "</if>",
            "<if test = 'invitationParams != null'>",
            "AND concat(u1.nickName,u1.openId) like concat('%',#{invitationParams},'%')",
            "</if>",
            "<if test = 'purchaseParams != null'>",
            "AND concat(u2.nickName,u2.openId) like concat('%',#{purchaseParams},'%')",
            "</if>",
            "<if test = 'createdAt != null'>",
            "AND pr.createdAt between #{createdAtA} and #{createdAtB}",
            "</if>",
            "</where>",
            "</script>"
    })
    int selectInvitationRecordsCount(InvitationRecord invitationRecord);
}