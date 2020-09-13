package com.qingclass.squirrel.cms.mapper.statistic;

import com.qingclass.squirrel.cms.entity.statistic.SquirrelFollowUp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowUpMapper {


	@Select({
			"<script>",
			"select id,onRead,date,clickWordUp,finishWordUp,clickPicbookUp,finishPicbookUp,finishAllUp from follow_up_statistic",
			"<where>",
			"levelId = #{levelId}",
			"<if test = 'startTime != null'>",
			"AND date between #{startTime} and #{endTime}",
			"</if>",
			"</where>",
			"order by date desc",
			"limit #{pageNo},#{pageSize}",
			"</script>"
	})
	List<SquirrelFollowUp> getSquirrelFollowUpActionList(SquirrelFollowUp squirrelFollowUp);


	@Select({
			"<script>",
			"select count(id) from follow_up_statistic",
			"<where>",
			"levelId = #{levelId}",
			"<if test = 'startTime != null'>",
			"AND date between #{startTime} and #{endTime}",
			"</if>",
			"</where>",
			"order by date desc",
			"</script>"
	})
	int getSquirrelFollowUpActionListCount(SquirrelFollowUp squirrelFollowUp);


	@Select({
			"<script>",
			"select ",
			"	id,onRead,date,finishAllUp,shareWordUp,sharePicbookUp,entryShare,",
			"	purchaseCount,finishWordUp,finishPicbookUp ",   
			"from follow_up_statistic",
			"<where>",
			"	levelId = #{levelId}",
			"<if test = 'startTime != null'>",
			"	AND date between #{startTime} and #{endTime}",
			"</if>",
			"</where>",
			"order by date desc",
			"limit #{pageNo},#{pageSize}",
			"</script>"
	})
	List<SquirrelFollowUp> getSquirrelFollowUpKValueList(SquirrelFollowUp squirrelFollowUp);

}