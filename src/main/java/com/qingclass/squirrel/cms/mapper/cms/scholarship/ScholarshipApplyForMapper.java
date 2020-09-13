package com.qingclass.squirrel.cms.mapper.cms.scholarship;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.qingclass.squirrel.cms.entity.cms.ScholarshipApplyFor;
import com.qingclass.squirrel.cms.entity.cms.ScholarshipApplyForResponse;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipRecordReq;

@Repository
public interface ScholarshipApplyForMapper {
	
	@Select({
		"<script>",
		"select ",
		"	saf.id, ",
		"	su.nickName, ",
		"	saf.scholarship_open_id openId,",
		"	sl.name levelName,   ",
		"	saf.status,	",
		"   saf.refund_status refundStatus, ",
		"	saf.begin_at beginClassTime, ",
		"	saf.learn_day learnDay, ",
		"	saf.learn_make_up_day learnMakeUpDay, ",
		"	saf.created_at createdAt, ",
		"	saf.updated_at updatedAt,  ",
		"	saf.amount, ",
		"	saf.bigbay_tranction_id bigbayTranctionId   ",
		" from msyb.scholarship_apply_for  saf  ",
		"	left join msyb.squirrel_users su on su.openId=saf.scholarship_open_id   ",
		"	left join msyb_resource.squirrel_levels sl on saf.level_id=sl.id ",
		"	left join msyb.user_levels ul on ul.levelId=saf.level_id and ul.beginAt=saf.begin_at and su.id=ul.squirrelUserId",
		"<where>",
		"<if test='nickName != null'>",
        "	and su.nickName like CONCAT(CONCAT('%',#{nickName,jdbcType=VARCHAR}),'%')",
        "</if>",
        "<if test='openId != null'>",
        "	and su.openId like CONCAT(CONCAT('%',#{openId,jdbcType=VARCHAR}),'%')",
        "</if>",
        "<if test='levelId != null'>",
		" 	and saf.level_id = #{levelId} ",
		"</if>",
		"<if test='status != null'>",
		" 	and saf.status = #{status} ",
		"</if>",
		"<if test='refundStatus != null'>",
		" 	and saf.refund_status = #{refundStatus} ",
		"</if>",
		"<if test='beginAtStartTime != null'>",
        "	and saf.begin_at = #{beginAtStartTime} ",
        "</if>",
		"</where>",
		" order by saf.created_at desc  ", 
		"</script>"
    })
    List<ScholarshipApplyFor> applyForRecordExcel(SquirrelScholarshipRecordReq scholarshipApplyFor);
	
	@Select({
		"<script>",
		"select ",
		"	saf.id, ",
		"	su.nickName, ",
		"	saf.scholarship_open_id openId,",
		"	sl.name levelName,   ",
		"	saf.status,	",
		"   saf.refund_status refundStatus, ",
		"	saf.begin_at beginClassTime, ",
		"	saf.learn_day learnDay, ",
		"	saf.learn_make_up_day learnMakeUpDay, ",
		"	saf.created_at createdAt, ",
		"	saf.updated_at updatedAt,  ",
		"	saf.amount, ",
		"	saf.bigbay_tranction_id bigbayTranctionId   ",
//		"	date_add(saf.begin_at, interval sl.lessonDay-1 day) endClassTime,",
//		"	su.id userId, saf.id, su.headImgUrl, ",
//		"	saf.begin_at beginAt, ",
//		"	saf.level_id levelId, ",
//		" 	ul.vipBeginTime, ul.vipEndTime ",
		
		" from msyb.scholarship_apply_for  saf  ",
		"	left join msyb.squirrel_users su on su.openId=saf.scholarship_open_id   ",
		"	left join msyb_resource.squirrel_levels sl on saf.level_id=sl.id ",
		"	left join msyb.user_levels ul on ul.levelId=saf.level_id and ul.beginAt=saf.begin_at and su.id=ul.squirrelUserId",
		"<where>",
		"<if test='nickName != null'>",
        "	and su.nickName like CONCAT(CONCAT('%',#{nickName,jdbcType=VARCHAR}),'%')",
        "</if>",
        "<if test='openId != null'>",
        "	and su.openId like CONCAT(CONCAT('%',#{openId,jdbcType=VARCHAR}),'%')",
        "</if>",
        "<if test='levelId != null'>",
		" 	and saf.level_id = #{levelId} ",
		"</if>",
		"<if test='status != null'>",
		" 	and saf.status = #{status} ",
		"</if>",
		"<if test='refundStatus != null'>",
		" 	and saf.refund_status = #{refundStatus} ",
		"</if>",
		"<if test='beginAtStartTime != null'>",
        "	and saf.begin_at = #{beginAtStartTime} ",
        "</if>",
		"</where>",
		" order by saf.created_at desc  ", 
		" limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER} ",
		"</script>"
    })
    List<ScholarshipApplyFor> applyForRecord(SquirrelScholarshipRecordReq scholarshipApplyFor);
	
	@Select({
		"<script>",
		"select count(saf.id) ",
		" from msyb.scholarship_apply_for  saf  ",
		"	left join msyb.squirrel_users su on su.openId=saf.scholarship_open_id   ",
		"	left join msyb_resource.squirrel_levels sl on saf.level_id=sl.id ",
		"	left join msyb.user_levels ul on ul.levelId=saf.level_id and ul.beginAt=saf.begin_at and su.id=ul.squirrelUserId",
		"<where>",
		"<if test='nickName != null'>",
        "	and su.nickName like CONCAT(CONCAT('%',#{nickName,jdbcType=VARCHAR}),'%')",
        "</if>",
        "<if test='openId != null'>",
        "	and su.openId like CONCAT(CONCAT('%',#{openId,jdbcType=VARCHAR}),'%')",
        "</if>",
        "<if test='levelId != null'>",
		" 	and saf.level_id = #{levelId} ",
		"</if>",
		"<if test='status != null'>",
		" 	and saf.status = #{status} ",
		"</if>",
		"<if test='refundStatus != null'>",
		" 	and saf.refund_status = #{refundStatus} ",
		"</if>",
		"<if test='beginAtStartTime != null'>",
        "	and saf.begin_at = #{beginAtStartTime} ",
        "</if>",
		"</where>",
		"</script>"
    })
    int applyForRecordCount(SquirrelScholarshipRecordReq scholarshipReq);
	
	@Update({
        "<script>",
        "update msyb.scholarship_apply_for ",
        "<set>",
        "	<if test='p.status != null'>",
        "		status = #{p.status},",
        "	</if>",
        "</set>",
        "where id = #{p.id}",
        "</script>"
	})
	@Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
	void update(@Param("p") SquirrelScholarshipRecordReq squirrelScholarshipRecordReq);
	
	
	@Select({
        "<script>",
        "	select 	",
        "		id, scholarship_open_id openId, ",
        "		begin_at beginAt, level_id levelId, status, ",
        "		amount, created_at createdAt, updated_at updatedAt ",
        "	from msyb.scholarship_apply_for ",
        "	where id = #{id}",
        "</script>"
	})
	ScholarshipApplyFor selectById(Integer id);
	
	@Insert({
        "<script>",
        "	insert into squirrel.scholarship_apply_for ",
        "		(begin_at ,scholarship_open_id, level_id, ",
        "		status, amount, description, scholarship_type, created_at  ) ",
        "	values( ",
        "		#{beginAt}, ",
        "		#{scholarshipOpenId},",
        "		#{levelId},",
        "		#{status},",
        "		#{amount},",
        "		#{description},",
        "		#{scholarshipType}, ",
        "		#{createdAt})",
        "</script>"
	})
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(ScholarshipApplyFor entPayOrder);
	
	@Select({
		"<script>",
		"	select ",
		"		saf.id, su.nickName,  su.headImgUrl, saf.scholarship_open_id openId, ",
		"		saf.level_id levelId, sl.name levelName,    ",
		"		convert(saf.amount/100,decimal(15,2)) amount, saf.created_at createdAt,  saf.status, saf.begin_at beginAt,  ",
		"		saf.description ",
		" 	from squirrel.scholarship_apply_for  saf ",
		"	left join msyb.squirrel_users su on su.openId=saf.scholarship_open_id   ",
		"	left join msyb_resource.squirrel_levels sl on saf.level_id=sl.id ",
		"<where>",
		"<if test='beginAtStartTime != null'>",
        "	and saf.created_at &gt;= #{beginAtStartTime} ",
        "</if>",
        "<if test='beginAtEndTime != null'>",
        "	and saf.created_at &lt;= #{beginAtEndTime} ",
        "</if>",
        "<if test='levelId != null'>",
		" and saf.level_id = #{levelId} ",
		"</if>",
		"<if test='scholarshipType != null'>",
		" and saf.scholarship_type = #{scholarshipType} ",
		"</if>",
		"<if test='nickName != null'>",
        "	and concat(su.nickName,su.openId) like CONCAT(CONCAT('%',#{nickName,jdbcType=VARCHAR}),'%')",
        "</if>",
		"</where>",
		" order by saf.created_at desc  ", 
		" limit #{pageNo,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER} ",
		"</script>"
    })
    List<ScholarshipApplyForResponse> specialScholarshipRecord(SquirrelScholarshipRecordReq scholarshipApplyFor);
	
	@Select({
		"<script>",
		"	select ",
		"		count(saf.id)  ",
		" 	from squirrel.scholarship_apply_for  saf ",
		"	left join msyb.squirrel_users su on su.openId=saf.scholarship_open_id   ",
		"<where>",
		"<if test='beginAtStartTime != null'>",
        "	and saf.created_at &gt;= #{beginAtStartTime} ",
        "</if>",
        "<if test='beginAtEndTime != null'>",
        "	and saf.created_at &lt;= #{beginAtEndTime} ",
        "</if>",
        "<if test='levelId != null'>",
		" and saf.level_id = #{levelId} ",
		"</if>",
		"<if test='scholarshipType != null'>",
		" and saf.scholarship_type = #{scholarshipType} ",
		"</if>",
		"<if test='nickName != null'>",
        "	and concat(su.nickName,su.openId) like CONCAT(CONCAT('%',#{nickName,jdbcType=VARCHAR}),'%')",
        "</if>",
		"</where>",
		"</script>"
    })
    int specialScholarshipRecordCount(SquirrelScholarshipRecordReq scholarshipReq);
	
	@Delete({
        "<script>",
        "delete from squirrel.scholarship_apply_for where id = #{id}",
        "</script>"
	})
	void delete(Integer id);
	// -------------------------------------------------------------------------------
    
	@Update({
		"<script>",
		"update msyb.scholarship_apply_for ",
		"<set>",
		"<if test='bigbayTranctionId != null'>",
		"	bigbay_tranction_id = #{bigbayTranctionId}, ",
		"</if>",
		"<if test='updatedAt != null'>",
		"	updated_at = #{updatedAt}, ",
		"</if>",
		"<if test='status != null'>", 
		"	status = #{status}, ",
		"</if>",
		"<if test='refundStatus != null'>", 
		"	refund_status = #{refundStatus}  ",
		"</if>",
		"</set>",
		"where id = #{id} ",
		"</script>"
	})
	int updateScholarshipApplyFor(ScholarshipApplyFor scholarshipApplyFor);
	
	@Select({
		"<script>",
		"select ",
		"	operation_status operationStatus, ", 
		"	id, ent_pay_order_scholarship_id, ",
		"	scholarship_open_id, level_id, status,amount, ",
		" 	scholarship_type scholarshipType, ",
		"	created_at createdAt, updated_at updatedAt ",
		" from squirrel.scholarship_apply_for  ",
		"<where>",
		"<if test='scholarshipOpenId != null'>",
		" scholarship_open_id = #{scholarshipOpenId} ",
		"</if>",
		"<if test='beginAt != null'>",
		" and begin_at = #{beginAt} ",
		"</if>",
		"<if test='levelId != null'>",
		" and level_id = #{levelId} ",
		"</if>",
		"<if test='status != null'>",
		" and status = #{status} ",
		"</if>",
		"</where>",
		" order by created_at desc,  updated_at desc  ", 
		"</script>"
    })
    List<ScholarshipApplyFor> selectApplyForByOpenId(ScholarshipApplyFor scholarshipApplyFor);
	
}