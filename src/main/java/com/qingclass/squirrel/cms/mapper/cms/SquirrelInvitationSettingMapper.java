package com.qingclass.squirrel.cms.mapper.cms;


import com.qingclass.squirrel.cms.entity.cms.SquirrelInvitationSetting;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelInvitationSettingMapper {


    @Select({
            "<script>",
            "select ",
            "	i.id,i.isOpen,i.img,i.rule,i.validDays, ",
            "	i.shareId,i.templateId,i.customId,l.id as levelId,l.name as levelName,i.createdAt as createdTime ",
            "from ",
            "	squirrel_invitation_setting i ",
            "left join squirrel_levels l on i.levelId = l.id",
            "<where>",
            "<if test = 'levelId != null'>",
            "i.levelId = #{levelId}",
            "</if>",
            "<if test = 'invitationType != null'>",
            "i.invitation_type = #{invitationType}",
            "</if>",
            "</where>",
            " order by i.createdAt desc ",
            "   limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER} ",
            "</script>"
    })
    List<SquirrelInvitationSetting> list(SquirrelInvitationSetting squirrelInvitationSetting);

    @Select({
            "<script>",
            "select count(i.id) from squirrel_invitation_setting i left join squirrel_levels l on i.levelId = l.id",
            "<where>",
            "<if test = 'levelId != null'>",
            "i.levelId = #{levelId}",
            "</if>",
            "<if test = 'invitationType != null'>",
            "i.invitation_type = #{invitationType}",
            "</if>",
            "</where>",
            "</script>"
    })
    int listCount(SquirrelInvitationSetting squirrelInvitationSetting);

    @Select({
            "<script>",
            "select i.id,i.img,i.rule,i.validDays,i.shareId,i.templateId,i.customId,",
            "	i.invitation_type invitationType, i.bonus_amount bonusAmount, i.bonus_img bonusImg, i.offer_amount offerAmount, i.offer_img offerImg, ",
            "l.id as levelId,l.name as levelName  ",
            "from squirrel_invitation_setting i ",
            "left join squirrel_levels l on i.levelId = l.id",
            "where i.id = #{id}",
            "</script>"
    })
    SquirrelInvitationSetting selectByPrimaryKey(Integer id);

    @Insert({
        "<script>",
        "insert into squirrel_invitation_setting(",
        "	img,rule,",
        "<if test='p.validDays != null'>",
        "	validDays, ",
        "</if>",
        "<if test='p.invitationType != null'>",
        "	invitation_type, ",
        "</if>",
        "	bonus_amount, bonus_img, offer_amount, offer_img, ",
        "	levelId,createdAt ",
        ")values(",
        "	#{p.img},#{p.rule},",
        "<if test='p.validDays != null'>",
        "	#{p.validDays},",
        "</if>",
        "<if test='p.invitationType != null'>",
        "	#{p.invitationType}, ",
        "</if>",
        "	#{p.bonusAmount}, #{p.bonusImg}, #{p.offerAmount}, #{p.offerImg}, ",
        "   #{p.levelId},now()",
        ")",
        "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    void insert(@Param("p") SquirrelInvitationSetting squirrelInvitationSetting);

    @Update({
            "<script>",
            "update squirrel_invitation_setting",
            "<set>",
            "<if test='p.img != null'>",
            "img = #{p.img},",
            "</if>",
            "<if test='p.rule != null'>",
            "rule = #{p.rule},",
            "</if>",
            "<if test='p.validDays != null'>",
            "validDays = #{p.validDays},",
            "</if>",
            "<if test='p.invitationType != null'>",
            "invitation_type = #{p.invitationType},",
            "</if>",
            "<if test='p.bonusAmount != null'>",
            "bonus_amount = #{p.bonusAmount},",
            "</if>",
            "<if test='p.bonusImg != null'>",
            "bonus_img = #{p.bonusImg},",
            "</if>",
            "<if test='p.offerAmount != null'>",
            "offer_amount = #{p.offerAmount},",
            "</if>",
            "<if test='p.offerImg != null'>",
            "offer_img = #{p.offerImg},",
            "</if>",
            "<if test='p.shareId != null'>",
            "shareId = #{p.shareId},",
            "</if>",
            "<if test='p.templateId != null'>",
            "templateId = #{p.templateId},",
            "</if>",
            "<if test='p.customId != null'>",
            "customId = #{p.customId},",
            "</if>",
            "<if test='p.levelId != null'>",
            "levelId = #{p.levelId},",
            "</if>",
            "<if test='p.isOpen != null'>",
            "isOpen = #{p.isOpen},",
            "</if>",
            "</set>",
            "where id = #{p.id}",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
    void update(@Param("p") SquirrelInvitationSetting squirrelInvitationSetting);

    @Update({
            "<script>",
            "update squirrel_invitation_setting set isOpen = #{isOpen} where id = #{id}",
            "</script>"
    })
    void editStatus(SquirrelInvitationSetting squirrelInvitationSetting);

    @Delete({
            "<script>",
            "delete from squirrel_invitation_setting where id = #{id}",
            "</script>"
    })
    void delete(Integer id);

}
