package com.qingclass.squirrel.cms.mapper.statistic;

import com.qingclass.squirrel.cms.entity.statistic.SquirrelBatchesStatistic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchesStatisticMapper {

	@Select({
			"<script>",
			"select * from batches_statistic where levelId = #{levelId} and beginDate = #{beginDate}",
			"<if test='startTime != null and endTime != null'>",
			"and currentDate between #{startTime} and #{endTime}",
			"</if>",
			"order by currentDate desc",
			"limit #{pageNo},#{pageSize}",
			"</script>"
	})
	List<SquirrelBatchesStatistic> selectByLevelIdAndBeginDate(SquirrelBatchesStatistic squirrelBatchesStatistic);



	@Select({
			"<script>",
			"select count(id) from batches_statistic where levelId = #{levelId} and beginDate = #{beginDate}",
			"<if test='startTime != null and endTime != null'>",
			"and currentDate between #{startTime} and #{endTime}",
			"</if>",
			"</script>"
	})
	int selectByLevelIdAndBeginDateCount(SquirrelBatchesStatistic squirrelBatchesStatistic);



	@Select({
			"<script>",
			"select * from batches_statistic bs,(",
			"select beginDate,max(currentDate) as currentDate from batches_statistic where levelId = #{levelId}",
			"<if test='startTime != null and endTime != null'>",
			"and beginDate between #{startTime} and #{endTime}",
			"</if>",
			"group by beginDate) s",
			"where bs.currentDate = s.currentDate and levelId = #{levelId} and bs.beginDate = s.beginDate",
			"<if test='startTime != null and endTime != null'>",
			"and bs.beginDate between #{startTime} and #{endTime}",
			"</if>",
			"order by bs.beginDate desc",
			"limit #{pageNo},#{pageSize}",
			"</script>"
	})
	List<SquirrelBatchesStatistic> selectByLevelId(SquirrelBatchesStatistic squirrelBatchesStatistic);



	@Select({
			"<script>",
			"select count(id) from batches_statistic bs,(",
			"select beginDate,max(currentDate) as currentDate from batches_statistic where levelId = #{levelId}",
			"<if test='startTime != null and endTime != null'>",
			"and beginDate between #{startTime} and #{endTime}",
			"</if>",
			"group by beginDate) s",
			"where bs.currentDate = s.currentDate and levelId = #{levelId} and bs.beginDate = s.beginDate",
			"<if test='startTime != null and endTime != null'>",
			"and bs.beginDate between #{startTime} and #{endTime}",
			"</if>",
			"</script>"
	})
	int selectByLevelIdCount(SquirrelBatchesStatistic squirrelBatchesStatistic);
}