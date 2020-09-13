package com.qingclass.squirrel.cms.mapper.cms;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.qingclass.squirrel.cms.entity.PaymentTransactions;

@Repository
public interface PaymentTransactionMapper {
	
	@Select({
		"<script>",
		"SELECT ",
		"	pt.totalFee, pt.bigbayTranctionId       ",
		"FROM ",
		"	msyb.payment_transactions pt ",
		"left join msyb.user_levels ul on pt.id = ul.transactionId ",
		"left join msyb.squirrel_users su on su.id=ul.squirrelUserId ",
		"where ",
		"	su.openId=#{openId}  ",
		"	and ul.levelId=#{levelId} ",
		"</script>"
	})
	PaymentTransactions selectTransByOpenIdAndLevelId(
			@Param("openId")String openId,
			@Param("levelId")int levelId);
	
	@Insert({
			"<script>",
			"insert into payment_transactions(wechatTransactionId,bigbayTranctionId,merchantionId,appId,totalFee,tradeType,bankType,openId,feeType,clientIp,sellPageItemConfig,timeEnd)",
			"values(#{p.wechatTransactionId},#{p.bigbayTranctionId},#{p.merchantionId},#{p.appId},#{p.totalFee},#{p.tradeType},#{p.bankType},#{p.openId},#{p.feeType},#{p.clientIp},#{p.sellPageItemConfig},#{p.timeEnd})",
			"</script>"
	})
	@Options(useGeneratedKeys = true, keyProperty = "p.id", keyColumn = "id")
	int insert(@Param("p") PaymentTransactions paymentTransactions);


	@Select({
			"<script>",
			"select id from payment_transactions where bigbayTranctionId = #{bigbayTranctionId}",
			"</script>"
	})
	PaymentTransactions selectTrans(String bigbayTranctionId);

	@Insert({
			"<script>",
			"insert into payment_refund(",
			"		refundFee,totalFee,refundReason,outTradeNo,",
			"		refundMode,wechatTransactionId,createdAt,updatedAt) ",
			"values(",
			"		#{refundFee},#{totalFee},#{refundReason},#{outTradeNo},",
			"		#{refundMode},#{wechatTransactionId},now(),now())",
			"</script>"
	})
	int insertRefund(@Param("refundFee")String refundFee,@Param("totalFee")String totalFee,@Param("refundReason")String refundReason,@Param("outTradeNo")String outTradeNo,
	@Param("refundMode")String refundMode,@Param("wechatTransactionId")String wechatTransactionId);
}
