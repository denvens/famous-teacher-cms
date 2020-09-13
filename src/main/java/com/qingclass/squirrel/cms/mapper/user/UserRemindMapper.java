package com.qingclass.squirrel.cms.mapper.user;

import com.qingclass.squirrel.cms.entity.wechat.WxRemind;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRemindMapper {

	@Insert({
			"<script>",
			"insert into users_lesson_remind(userId,openId,levelId,remindTime,remindRate,isOpen,createdAt,updatedAt) values",
			"(#{userId},#{openId},#{levelId},#{remindTime},#{remindRate},#{isOpen},now(),now())",
			"</script>"
	})
	int insertRemindRecord(@Param("userId") Integer userId, @Param("openId") String openId,
                           @Param("levelId") Integer levelId, @Param("remindTime") String remindTime,
                           @Param("remindRate") String remindRate, @Param("isOpen") int isOpen);


	@Select(
			"select openId,levelId from users_lesson_remind where remindTime = #{remindTime} and isOpen = 1"
	)
	List<WxRemind> selectLessonRemind(@Param("remindTime") String remindTime);


	@Select({
			"<script>",
			"select id,openId,levelId,isOpen,remindTime,remindRate from users_lesson_remind",
			"where openId = #{openId} and levelId = #{levelId}",
			"</script>"
	})
	List<WxRemind> selectUserLessonRemind(@Param("openId") String openId, @Param("levelId") Integer levelId);

	@Update({
			"<script>",
			"update users_lesson_remind set",
			"isOpen = #{isOpen}",
			"where openId = #{openId} and levelId = #{levelId}",
			"</script>"
	})
	int updateOpenStatus(@Param("openId") String openId, @Param("levelId") Integer levelId, @Param("isOpen") Integer isOpen);

	@Delete({
			"<script>",
			"delete from users_lesson_remind",
			"where openId = #{openId} and levelId = #{levelId}",
			"</script>"
	})
	int deleteUserLessonRemind(@Param("openId") String openId, @Param("levelId") Integer levelId);


	@Update({
			"<script>",
			"update users_lesson_remind set",
			"remindTime = #{remindTime}",
			"where id = #{id} and levelId = #{levelId}",
			"</script>"
	})
	int updateUserLessonRemind(@Param("id") Integer id, @Param("levelId") Integer levelId, @Param("remindTime") String remindTime);

}

