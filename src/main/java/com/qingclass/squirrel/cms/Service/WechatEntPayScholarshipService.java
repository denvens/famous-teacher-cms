package com.qingclass.squirrel.cms.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.qingclass.mango.pay.service.common.Result;
//import com.qingclass.mango.pay.service.domain.EntPayOrder;
//import com.qingclass.mango.pay.service.model.request.EntPayCreateOrderRequest;
//import com.qingclass.mango.pay.service.model.response.EntPayCreateOrderResponse;
//import com.qingclass.mango.pay.service.repository.EntPayOrderRepository;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.qingclass.squirrel.cms.Service.model.EntPayCreateOrderScholarshipRequest;
import com.qingclass.squirrel.cms.constant.ScholarshipApplyForStatusEnum;
import com.qingclass.squirrel.cms.entity.cms.ScholarshipApplyFor;
import com.qingclass.squirrel.cms.entity.scholarship.EntPayOrder;
import com.qingclass.squirrel.cms.entity.scholarship.EntPayOrderScholarship;
import com.qingclass.squirrel.cms.entity.scholarship.ScholarshipSettingDetails;
//import com.qingclass.squirrel.cms.mapper.scholarship.EntPayOrderScholarshipMapper;
//import com.qingclass.squirrel.cms.mapper.scholarship.ScholarshipApplyForMapper;
//import com.qingclass.squirrel.entity.EntPayOrderScholarship;
//import com.qingclass.squirrel.entity.ScholarshipApplyFor;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import com.qingclass.squirrel.cms.mapper.cms.scholarship.EntPayOrderScholarshipMapper;
import com.qingclass.squirrel.cms.mapper.cms.scholarship.ScholarshipApplyForMapper;
//import com.qingclass.squirrel.service.model.request.EntPayCreateOrderScholarshipRequest;
//@Slf4j
@Service
public class WechatEntPayScholarshipService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SquirrelLevelMapper squirrelLevelMapper; 
    
    private WxPayService wxService;//new WxPayServiceImpl()

    @Autowired
	private EntPayOrderScholarshipMapper entPayOrderScholarshipMapper;
    
    @Autowired
	private ScholarshipApplyForMapper scholarshipApplyForMapper;
    
    @Autowired
    public WechatEntPayScholarshipService(com.github.binarywang.wxpay.service.WxPayService wxService) {
        this.wxService = wxService;
    }

    @Autowired
	private static  Environment environment;
	
    public Object createOrder(
    		EntPayCreateOrderScholarshipRequest entPayorderRequest,
    		ScholarshipApplyFor scholarship
    		){
        String orderId = UUID.randomUUID().toString();
        String partnerTradeNo = orderId.replaceAll("-", "");

        EntPayRequest entPayRequest = new EntPayRequest();
        entPayRequest.setDeviceInfo("WEB");
        entPayRequest.setAmount(entPayorderRequest.getAmount());
        entPayRequest.setPartnerTradeNo(partnerTradeNo);
        entPayRequest.setOpenid(entPayorderRequest.getOpenid());
        entPayRequest.setDescription(entPayorderRequest.getDescription());
        entPayRequest.setSpbillCreateIp(entPayorderRequest.getSpbillCreateIp());
        entPayRequest.setAppid(entPayorderRequest.getAppId());
        entPayRequest.setCheckName("NO_CHECK");

        try {
            EntPayResult entPayResult = this.wxService.getEntPayService().entPay(entPayRequest);
            //---------------------------------------------------------------------
            
            EntPayOrderScholarship entPayOrder = new EntPayOrderScholarship();
            entPayOrder.setBeginAt(scholarship.getBeginAt());
            entPayOrder.setScholarshipOpenId(entPayorderRequest.getScholarshipOpenId());
            entPayOrder.setLevelId(entPayorderRequest.getLevelId());
            entPayOrder.setOrderId(orderId);
            entPayOrder.setAmount(entPayorderRequest.getAmount());
            entPayOrder.setOpenId(entPayorderRequest.getOpenid());

            entPayOrder.setSpbillCreateIp(entPayorderRequest.getSpbillCreateIp());
            entPayOrder.setCreatedAt(new Date());
            entPayOrder.setUpdatedAt(new Date());

            if(entPayResult.getReturnCode().equals(WxPayConstants.ResultCode.FAIL)) {
            	entPayOrder.setStatus("FAIL");
                entPayOrder.setPartnerTradeNo(partnerTradeNo);
                entPayOrder.setPaymentNo("FAIL");
                entPayOrder.setPaymentTime(new Date());
                entPayOrderScholarshipMapper.insert(entPayOrder);
                log.error("微信打款到零钱-毕业奖学金入账（orderId = " + orderId + "）失败：接口请求失败");

                ScholarshipApplyFor applyFor = new ScholarshipApplyFor();
//				applyFor.setStatus(ScholarshipApplyForStatusEnum.Failure.getKey());
				applyFor.setEntPayOrderScholarshipId(entPayOrder.getId());
				applyFor.setUpdatedAt(new Date());
				applyFor.setId(scholarship.getId());
				scholarshipApplyForMapper.updateScholarshipApplyFor(applyFor);
//                entPayCreateOrderResponse.setSuccess(false);
//                return Result.success(entPayCreateOrderResponse);
            }
            if("SUCCESS".equals(entPayResult.getResultCode())) {
            	entPayOrder.setStatus("SUCCESS");
                entPayOrder.setPartnerTradeNo(entPayResult.getPartnerTradeNo());
                entPayOrder.setPaymentNo(entPayResult.getPaymentNo());
                
            	
				try {
					String strReqDelTime = entPayResult.getPaymentTime();
					Date date = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse(strReqDelTime);
					entPayOrder.setPaymentTime(date);
				} catch (ParseException e) {
					log.error("微信打款到零钱-毕业奖学金入账,PaymentTime格式错误,（orderId = " + orderId + "）失败：接口请求失败");
				}
                
				entPayOrderScholarshipMapper.insert(entPayOrder);
				
				ScholarshipApplyFor applyFor = new ScholarshipApplyFor();
//				applyFor.setStatus(ScholarshipApplyForStatusEnum.Success.getKey());
				applyFor.setEntPayOrderScholarshipId(entPayOrder.getId());
				applyFor.setUpdatedAt(new Date());
				applyFor.setId(scholarship.getId());
				scholarshipApplyForMapper.updateScholarshipApplyFor(applyFor);
//            	log.error("微信打款到零钱（orderId = " + orderId + "）成功");
//                entPayCreateOrderResponse.setSuccess(true);
//                return Result.success(entPayCreateOrderResponse);
            }else {
                entPayOrder.setStatus("FAIL");
                entPayOrder.setPartnerTradeNo(partnerTradeNo);
                entPayOrder.setPaymentNo("FAIL");
                entPayOrder.setPaymentTime(new Date());
                entPayOrderScholarshipMapper.insert(entPayOrder);
                log.error("微信打款到零钱-毕业奖学金入账（orderId = " + orderId + "）失败：resultCode=" + entPayResult.getResultCode());

                ScholarshipApplyFor applyFor = new ScholarshipApplyFor();
//				applyFor.setStatus(ScholarshipApplyForStatusEnum.Failure.getKey());
				applyFor.setEntPayOrderScholarshipId(entPayOrder.getId());
				applyFor.setUpdatedAt(new Date());
				applyFor.setId(scholarship.getId());
				scholarshipApplyForMapper.updateScholarshipApplyFor(applyFor);
//                entPayCreateOrderResponse.setSuccess(false);
//                return Result.success(entPayCreateOrderResponse);
            }
        } catch (WxPayException e) {
            log.error("微信打款到零钱-毕业奖学金入账（orderId = " + orderId +"）失败：" + e.getReturnMsg());

//            entPayCreateOrderResponse.setSuccess(false);
//            return Result.success(entPayCreateOrderResponse);
        }
        return null;
    }
    
}
