package com.qingclass.squirrel.cms.mapper.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;




import com.qingclass.squirrel.cms.entity.user.SquirrelUser;

import java.util.List;
import java.util.Map;

@Repository
public interface SquirrelUserMapper {


	@Select({
		"<script>",
		"select ",
		"	beginAt,levelId,transactionId,vipBeginTime,vipEndTime ",
		"from ",
		"	user_levels ",
		"where ",
		"	levelId = #{levelId} ",
		"	and squirrelUserId in(",
		"		select id from squirrel_users where openId=#{openId}",
		")",
		"</script>"
	})
	List<SquirrelUser> selectBeginAtByOpenId(@Param("openId")String openId, @Param("levelId")Integer levelId);

	
}
