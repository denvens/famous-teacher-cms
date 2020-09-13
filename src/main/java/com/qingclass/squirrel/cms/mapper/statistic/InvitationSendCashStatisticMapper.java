package com.qingclass.squirrel.cms.mapper.statistic;

import com.qingclass.squirrel.cms.entity.statistic.InvitationSendCashStatistic;
import com.qingclass.squirrel.cms.entity.statistic.InvitationSendCashStatisticBeginReq;
import com.qingclass.squirrel.cms.entity.statistic.InvitationSendCashStatisticReq;
import com.qingclass.squirrel.cms.entity.statistic.SquirrelBatchesStatistic;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationSendCashStatisticMapper {

	@Select({
		"<script>",
		"	select", 
		"		point.currentStatisticDate currentDate,",
		"		point.startClassDate, ", 
		"		ifnull(point.beginDays,0) beginDays,",
		"		ifnull(users.beginClassCount,0) beginPeoples,",
		"		ifnull(point.intoSendCashPageCount,0) intoSendCashPageCount, ",
		"		CONCAT(ifnull(ROUND(point.intoSendCashPageCount / ifnull(users.beginClassCount,0) * 100, 2),0),'','%') intoSendPercentage,",
		"		ifnull(point.sendInvitationCount,0) sendInvitationCount, ",
		"		CONCAT(ifnull(ROUND(point.sendInvitationCount / ifnull(point.intoSendCashPageCount,0) * 100, 2),0),'','%') sendInvitationPercentage,",
		"		ifnull(point.gotoBuyPageCount,0) gotoBuyPageCount, ",
		"		CONCAT(ifnull(ROUND(point.gotoBuyPageCount / ifnull(point.sendInvitationCount,0) * 100, 2),0),'','%') gotoBuyPagePercentage,",
		"		ifnull(point.clickBuyCount,0) clickBuyCount, ",
		"		CONCAT(ifnull(ROUND(point.clickBuyCount / ifnull(point.gotoBuyPageCount,0) * 100, 2),0),'','%') clickBuyPercentage,",
		"		ifnull(point.purchaseCount,0) purchaseCount, ",
		"		CONCAT(ifnull(ROUND(point.purchaseCount / ifnull(point.clickBuyCount,0) * 100, 2),0),'','%') purchasePercentage ",
		"	from (",
		"		SELECT ", 
		"			p.current_statistic_date currentStatisticDate, ", 
		"			p.start_class_date startClassDate,",
		"			p.begin_days as beginDays, ",

		"			p.into_send_cash_page_count as intoSendCashPageCount ,",
		"			p.send_invitation_count as sendInvitationCount, ",  
		"			p.goto_buy_page_count as gotoBuyPageCount,   ",
		"			p.click_buy_count as clickBuyCount,  ",
		"			p.purchase_count as purchaseCount",
		"		FROM squirrel_statistic.invitation_send_cash_statistic p  ",
		
		"		where ",
		"			p.level_Id=#{levelId} ",
		"			and p.start_class_date = #{beginDate} ",
		"		<if test='startTime != null and endTime != null'>",
		"			and p.current_statistic_date between #{startTime} and #{endTime}",
		"		</if>",
		
		" 		order by p.current_statistic_date desc",
		" 		limit #{pageNo}, #{pageSize}",
		"	) point ",
		
		"	left join ",
		
		"	(",
		"		select ",
		"	 		beginAt, count(id) beginClassCount  ",
		"		from `squirrel`.`user_levels` ",
		"		where  levelId = #{levelId} ",
		"		group by beginAt  ",
		"	) users ",
		"	on users.beginAt=point.startClassDate ",
		"</script>"
	})
	List<InvitationSendCashStatistic> selectByLevelIdAndBeginDate(InvitationSendCashStatisticBeginReq sendCashStatistic);



	@Select({
			"<script>",
			"		SELECT ", 
			"			count(id) id ",
			"		FROM squirrel_statistic.invitation_send_cash_statistic p  ",
			"		where ",
			"			p.level_Id=#{levelId} ",
			"			and p.start_class_date = #{beginDate}",
			"		<if test='startTime != null and endTime != null'>",
			"			and p.current_statistic_date between #{startTime} and #{endTime}",
			"		</if>",
			"</script>"
	})
	int selectByLevelIdAndBeginDateCount(InvitationSendCashStatisticBeginReq sendCashStatistic);



	@Select({
			"<script>",
			"	select", 
			"		curdate() currentDate,",
			"		point.startClassDate, ", 
			"		ifnull(point.beginDays,0) beginDays,",
			"		ifnull(users.beginClassCount,0) beginPeoples,",
			"		ifnull(point.intoSendCashPageCount,0) intoSendCashPageCount, ",
			"		CONCAT(ifnull(ROUND(point.intoSendCashPageCount / ifnull(users.beginClassCount,0) * 100, 2),0),'','%') intoSendPercentage,",
			"		ifnull(point.sendInvitationCount,0) sendInvitationCount, ",
			"		CONCAT(ifnull(ROUND(point.sendInvitationCount / ifnull(point.intoSendCashPageCount,0) * 100, 2),0),'','%') sendInvitationPercentage,",
			"		ifnull(point.gotoBuyPageCount,0) gotoBuyPageCount, ",
			"		CONCAT(ifnull(ROUND(point.gotoBuyPageCount / ifnull(point.sendInvitationCount,0) * 100, 2),0),'','%') gotoBuyPagePercentage,",
			"		ifnull(point.clickBuyCount,0) clickBuyCount, ",
			"		CONCAT(ifnull(ROUND(point.clickBuyCount / ifnull(point.gotoBuyPageCount,0) * 100, 2),0),'','%') clickBuyPercentage,",
			"		ifnull(point.purchaseCount,0) purchaseCount, ",
			"		CONCAT(ifnull(ROUND(point.purchaseCount / ifnull(point.clickBuyCount,0) * 100, 2),0),'','%') purchasePercentage ",
			"	from (",
			"		SELECT ", 
			"			p.current_statistic_date currentStatisticDate, ", 
			"			p.start_class_date startClassDate,",
			"			p.begin_days as beginDays, ",
    
			"			sum(p.into_send_cash_page_count) as intoSendCashPageCount ,",
			"			sum(p.send_invitation_count) as sendInvitationCount, ",  
			"			sum(p.goto_buy_page_count) as gotoBuyPageCount,   ",
			"			sum(p.click_buy_count) as clickBuyCount,  ",
			"			sum(p.purchase_count) as purchaseCount",
			"		FROM squirrel_statistic.invitation_send_cash_statistic p  ",
			
			"		where ",
			"			p.level_Id=#{levelId} ",
			
			"		<if test='startTime != null and endTime != null'>",
			"			and p.start_class_date between #{startTime} and #{endTime}",
			"		</if>",
			
			"		group by p.start_class_date   ",
			" 		order by p.start_class_date desc",
			" 		limit #{pageNo}, #{pageSize}",
			"	) point ",
			
			"	left join ",
			
			"	(",
			"		select ",
			"	 		beginAt, count(id) beginClassCount  ",
			"		from `squirrel`.`user_levels` ",
			"		where  levelId = #{levelId} ",
			"		group by beginAt  ",
			"	) users ",
			"	on users.beginAt=point.startClassDate ",
			"</script>"
	})
	List<InvitationSendCashStatistic> selectByLevelId(InvitationSendCashStatisticReq sendCashStatisticReq);

	@Select({
			"<script>",
			"	SELECT count(s.id) ", 
			"	from (",
			"		SELECT ", 
			"			count(id) id ",
			"		FROM squirrel_statistic.invitation_send_cash_statistic p  ",
			
			"		where ",
			"			p.level_Id=#{levelId} ",
			
			"		<if test='startTime != null and endTime != null'>",
			"			and p.start_class_date between #{startTime} and #{endTime}",
			"		</if>",
			
			"		group by p.start_class_date   ",
			"	)s ",
			
		
			"</script>"
	})
	int selectByLevelIdCount(InvitationSendCashStatisticReq sendCashStatisticReq);
}