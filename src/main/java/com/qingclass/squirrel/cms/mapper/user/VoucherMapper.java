package com.qingclass.squirrel.cms.mapper.user;

import com.qingclass.squirrel.cms.entity.user.UserLevel;
import com.qingclass.squirrel.cms.entity.user.Voucher;
import com.qingclass.squirrel.cms.entity.user.VoucherOperRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherMapper {

    @Select({
            "<script>",
            "select v.id,v.squirrelUserId,v.levelId,v.createdAt as createdAtA,v.useTime as useTimeA,v.status,u.nickName,u.headImgUrl as img,v.isOpen,u.openId from squirrel_patch_vouchers v left join ",
            "squirrel_users u on v.squirrelUserId = u.id",
            "<where>",
            "<if test='userParams != null'>",
            "AND concat(u.openId,u.nickName) like concat('%',#{userParams},'%')",
            "</if>",
            "<if test='levelId != null'>",
            "AND v.levelId = #{levelId}",
            "</if>",
            "<if test='createdAt != null'>",
            "AND v.createdAt between #{createdAtA} and #{createdAtB}",
            "</if>",
            "<if test='useTime != null'>",
            "AND v.useTime between #{useTimeA} and #{useTimeB}",
            "</if>",
            "<if test='status != null'>",
            "AND v.status = #{status}",
            "</if>",
            "</where>",
            "order by v.id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<Voucher> selectByParams(Voucher voucher);

    @Select({
            "<script>",
            "select v.id,v.squirrelUserId,v.levelId,v.createdAt,v.useTime,v.status,u.nickName,u.headImgUrl as img,v.isOpen,u.openId from squirrel_patch_vouchers v left join ",
            "squirrel_users u on v.squirrelUserId = u.id",
            "where v.id = #{id}",
            "</script>"
    })
    Voucher selectByPrimaryKey(Integer id);

    @Select({
            "<script>",
            "select count(v.id) from squirrel_patch_vouchers v left join ",
            "squirrel_users u on v.squirrelUserId = u.id",
            "<where>",
            "<if test='userParams != null'>",
            "AND concat(u.openId,u.nickName) like concat('%',#{userParams},'%')",
            "</if>",
            "<if test='levelId != null'>",
            "AND v.levelId = #{levelId}",
            "</if>",
            "<if test='createdAt != null'>",
            "AND v.createdAt between #{createdAtA} and #{createdAtB}",
            "</if>",
            "<if test='useTime != null'>",
            "AND v.useTime between #{useTimeA} and #{useTimeB}",
            "</if>",
            "<if test='status != null'>",
            "AND v.status = #{status}",
            "</if>",
            "</where>",
            "</script>"
    })
    int selectByParamsCount(Voucher voucher);

    @Update({
            "<script>",
            "update squirrel_patch_vouchers set isOpen = #{isOpen}",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKey(Voucher voucher);

    @Insert({
            "<script>",
            "insert into voucher_oper_record(voucherId,type,adminId,createdAt,updatedAt)",
            "values(#{voucherId},#{type},#{adminId},now(),now())",
            "</script>"
    })
    int insertRecord(VoucherOperRecord voucherOperRecord);

    @Select({
            "<script>",
            "select r.id,v.createdAt as createdAtA,r.updatedAt as updateAtA,u.nickName,u.headImgUrl as img,u.openId,r.type,a.userName as adminName from voucher_oper_record r",
            "left join squirrel_patch_vouchers v on r.voucherId = v.id",
            "left join squirrel_users u on v.squirrelUserId = u.id",
            "left join `squirrel_resource`.`squirrel_cms_admins` a on r.adminId = a.id",
            "<where>",
            "<if test='levelId != null'>",
            "AND v.levelId = #{levelId}",
            "</if>",
            "<if test='userParams != null'>",
            "AND concat(u.openId,u.nickName) like concat('%',#{userParams},'%')",
            "</if>",
            "<if test='updatedAt != null'>",
            "AND r.updatedAt between #{updateAtA} and #{updateAtB}",
            "</if>",
            "</where>",
            "order by r.id desc limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}",
            "</script>"
    })
    List<VoucherOperRecord> selectRecord(VoucherOperRecord voucherOperRecord);

    @Select({
            "<script>",
            "select count(r.id) from voucher_oper_record r",
            "left join squirrel_patch_vouchers v on r.voucherId = v.id",
            "left join squirrel_users u on v.squirrelUserId = u.id",
            "left join `squirrel_resource`.`squirrel_cms_admins` a on r.adminId = a.id",
            "<where>",
            "<if test='levelId != null'>",
            "AND v.levelId = #{levelId}",
            "</if>",
            "<if test='userParams != null'>",
            "AND concat(u.openId,u.nickName) like concat('%',#{userParams},'%')",
            "</if>",
            "<if test='updatedAt != null'>",
            "AND r.updatedAt between #{updateAtA} and #{updateAtB}",
            "</if>",
            "</where>",
            "</script>"
    })
    int selectRecordCount(VoucherOperRecord voucherOperRecord);

    @Insert({
            "<script>",
            "insert into prohibit_temp_table(levelId,squirrelUserId,createdAt) values",
            "(#{levelId},#{squirrelUserId},now())",
            "</script>"
    })
    int insertProhibit(@Param("levelId")Integer levelId,@Param("squirrelUserId")Integer squirrelUserId);
}
