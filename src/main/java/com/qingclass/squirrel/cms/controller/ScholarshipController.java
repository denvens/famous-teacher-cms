package com.qingclass.squirrel.cms.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingclass.squirrel.cms.Service.WechatEntPayScholarshipService;
import com.qingclass.squirrel.cms.Service.model.EntPayCreateOrderScholarshipRequest;
import com.qingclass.squirrel.cms.constant.Constants;
import com.qingclass.squirrel.cms.constant.InvitationTypeEnum;
import com.qingclass.squirrel.cms.constant.ScholarshipApplyForRefundStatusEnum;
import com.qingclass.squirrel.cms.constant.ScholarshipApplyForStatusEnum;
import com.qingclass.squirrel.cms.constant.ScholarshipCashBackTypeEnum;
import com.qingclass.squirrel.cms.constant.ScholarshipTypeEnum;
import com.qingclass.squirrel.cms.constant.SpecialScholarshipApplyForErrorEnum;
import com.qingclass.squirrel.cms.entity.PaymentTransactions;
import com.qingclass.squirrel.cms.entity.cms.ScholarshipApplyFor;
import com.qingclass.squirrel.cms.entity.cms.ScholarshipApplyForResponse;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipRecordReq;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipSetting;
import com.qingclass.squirrel.cms.entity.cms.SquirrelScholarshipSettingReq;
import com.qingclass.squirrel.cms.entity.scholarship.ScholarshipApplyForResult;
import com.qingclass.squirrel.cms.entity.user.InvitationRecord;
import com.qingclass.squirrel.cms.entity.user.UserLevel;
//import com.qingclass.squirrel.service.WechatEntPayScholarshipService;
import com.qingclass.squirrel.cms.mapper.cms.BigbayMapper;
import com.qingclass.squirrel.cms.mapper.cms.PaymentTransactionMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelScholarshipSettingMapper;
import com.qingclass.squirrel.cms.mapper.cms.scholarship.ScholarshipApplyForMapper;
import com.qingclass.squirrel.cms.mapper.user.UserMapper;
import com.qingclass.squirrel.cms.utils.PoiHelper;
//import com.qingclass.squirrel.cms.mapper.scholarship.ScholarshipApplyForMapper;
import com.qingclass.squirrel.cms.utils.Tools;

@RestController
@RequestMapping("/scholarship")
public class ScholarshipController {

//	@InitBinder
//	public void initBinder(WebDataBinder binder, WebRequest request) {
//		//转换日期
//		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
//	}
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WechatEntPayScholarshipService scholarshipService;
	
    @Autowired
    private SquirrelScholarshipSettingMapper scholarshipMapper;

    @Autowired
    private ScholarshipApplyForMapper scholarshipApplyForMapper;
    
    @Autowired
	private UserMapper userMapper;
    
    @Autowired
	private HttpClient httpClient;
    
    @Autowired
    private BigbayMapper bigbayMapper;
    
    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;
    
    @Value("${bigbay.refund}")
    private String refundUrl;
    
    @Value("${bigbay.bigbayAppId}")
    private String bigbayAppId;
    
    @ResponseBody
    @PostMapping(value = "apply-for-record")
	public Map<String,Object> applyForRecord(
			@RequestParam(value = "nickName",required = false)String nickName,
			@RequestParam(value = "openId",required = false)String openId,
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "status",required = false)Integer status,
			@RequestParam(value = "refundStatus",required = false)Integer refundStatus,
			@RequestParam(value = "beginAtStartTime", required = false) String beginAtStartTime,
            @RequestParam(value = "pageNo",required = false)Integer pageNo,
            @RequestParam(value = "pageSize",required = false)Integer pageSize){
    	if(pageNo==null){pageNo=new Integer(1);}
    	if(pageSize==null){pageSize=new Integer(20);}
        pageNo = (pageNo - 1) * pageSize;
        SquirrelScholarshipRecordReq recordReq = new SquirrelScholarshipRecordReq();
        recordReq.setPageNo(pageNo);
        recordReq.setPageSize(pageSize);
        if(StringUtils.isNotBlank(nickName)){
        	recordReq.setNickName(nickName);
        }
        if(StringUtils.isNotBlank(openId)){
        	recordReq.setOpenId(openId);
        }
        if(levelId != null){
        	recordReq.setLevelId(levelId);
        }
        if(status != null){
        	recordReq.setStatus(status);
        }
        if(refundStatus != null){
        	recordReq.setRefundStatus(refundStatus);
        }
        if(StringUtils.isNotBlank(beginAtStartTime)){
        	recordReq.setBeginAtStartTime(beginAtStartTime);
        }
        List<ScholarshipApplyFor> list = scholarshipApplyForMapper.applyForRecord(recordReq);
        
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",scholarshipApplyForMapper.applyForRecordCount(recordReq));
        return Tools.s(map);
    }
    
    @ResponseBody
    @RequestMapping(value = "/apply-for-record-excel", method = RequestMethod.GET)
    public Map<String, Object> applyForRecordExcel(HttpServletResponse response,
    		@RequestParam(value = "nickName",required = false)String nickName,
			@RequestParam(value = "openId",required = false)String openId,
			@RequestParam(value = "levelId",required = false)Integer levelId,
			@RequestParam(value = "status",required = false)Integer status,
			@RequestParam(value = "refundStatus",required = false)Integer refundStatus,
			@RequestParam(value = "beginAtStartTime", required = false) String beginAtStartTime) throws ParseException {
        
        SquirrelScholarshipRecordReq recordReq = new SquirrelScholarshipRecordReq();
        if(StringUtils.isNotBlank(nickName)){
        	recordReq.setNickName(nickName);
        }
        if(StringUtils.isNotBlank(openId)){
        	recordReq.setOpenId(openId);
        }
        if(levelId != null){
        	recordReq.setLevelId(levelId);
        }
        if(status != null){
        	recordReq.setStatus(status);
        }
        if(refundStatus != null){
        	recordReq.setRefundStatus(refundStatus);
        }
        if(StringUtils.isNotBlank(beginAtStartTime)){
        	recordReq.setBeginAtStartTime(beginAtStartTime);
        }
        List<ScholarshipApplyFor> scholarshipApplyForList = scholarshipApplyForMapper.applyForRecordExcel(recordReq);
        
        //表头
        Map<String, String> headNameMap = new LinkedHashMap<>();
        headNameMap.put("nickName", "用户昵称");
        headNameMap.put("openId", "openId");
        headNameMap.put("levelName", "频道");
        headNameMap.put("status", "状态");
        headNameMap.put("refundStatus", "退款状态");
        headNameMap.put("beginClassTime", "开课时间");
        headNameMap.put("endClassTime", "结课时间");
        headNameMap.put("learnDay", "学习天数");
        headNameMap.put("makeUpLearnDay", "补学天数");
        headNameMap.put("createdAt", "申请时间");
        headNameMap.put("updatedAt", "通过时间");
        headNameMap.put("amount", "支付金额");
        headNameMap.put("entPayOrderScholarshipId", "商户订单号");
        
        //表格数据
        List<Map<String, Object>> list = new ArrayList<>();
        if (scholarshipApplyForList != null && scholarshipApplyForList.size() > 0) {
            for (ScholarshipApplyFor i : scholarshipApplyForList) {
                Map<String, Object> map = new HashMap<>();
                map.put("nickName", i.getNickName());
                map.put("openId", i.getOpenId());
                map.put("levelName", i.getLevelName());
                map.put("status", i.getStatus());
                map.put("refundStatus", i.getRefundStatus());
                map.put("beginClassTime", i.getBeginClassTime());
                map.put("endClassTime", i.getBeginClassTime());
                map.put("learnDay", i.getLearnDay());
                map.put("makeUpLearnDay", i.getMakeUpLearnDay());
                map.put("createdAt", i.getCreatedAt());
                map.put("updatedAt", i.getUpdatedAt());
                map.put("amount", i.getAmount());
                map.put("entPayOrderScholarshipId", i.getEntPayOrderScholarshipId());
                list.add(map);
            }
        }

        PoiHelper.exportXlsx(response, "名师优播奖学金表", headNameMap, list);
        return Tools.s();
    }
    
    @ResponseBody
    @PostMapping(value = "review-scholarship")
    public Map<String,Object> reviewScholarship(
    		@RequestParam(value = "ids", required = true) String ids,
    		@RequestParam(value = "status",required = false)Integer status){
    	
    	logger.info("**********************refundUrl:{}, bigbayAppId:{}", refundUrl, bigbayAppId);
    	
    	String[] strs = ids.split(",");
    	List<String> strList = Arrays.asList(strs);
    	
    	if(status==ScholarshipApplyForStatusEnum.Pass.getKey().intValue()){//审核通过
    		logger.info("审核通过！status:{}", status);
    	}else if(status==ScholarshipApplyForStatusEnum.Refuse.getKey().intValue()){//审核拒绝
    		logger.info("审核拒绝！status:{}", status);
    	}else{
    		logger.info("参数非法！status:{}", status);
    		return Tools.s(null);
    	}
    	//获取SignKey
    	String bigbaySignKey = bigbayMapper.selectKeyByAppId(bigbayAppId);
    	for(int i=0; i<strList.size(); i++){
    		ScholarshipApplyFor scholarshipApplyFor = scholarshipApplyForMapper.selectById(Integer.valueOf(strList.get(i)));
    		logger.info("退奖学金: openId:{}, levelId:{}",scholarshipApplyFor.getOpenId(), scholarshipApplyFor.getLevelId());
    		//退款发放奖学金
    		if(status==ScholarshipApplyForStatusEnum.Pass.getKey().intValue()){//审核通过
    			PaymentTransactions pt = paymentTransactionMapper.selectTransByOpenIdAndLevelId(scholarshipApplyFor.getOpenId(),scholarshipApplyFor.getLevelId());
    			String responseBody = refundScholarship(bigbayAppId, bigbaySignKey, pt);
    			
    			ScholarshipApplyFor s = new ScholarshipApplyFor();
    			s.setId(Integer.valueOf(strList.get(i)));
    			s.setBigbayTranctionId(pt.getBigbayTranctionnId());
    			s.setRefundStatus(ScholarshipApplyForRefundStatusEnum.Success.getKey());
    			s.setUpdatedAt(new Date());
    			scholarshipApplyForMapper.updateScholarshipApplyFor(s);
    		}
    		
    		SquirrelScholarshipRecordReq scholarship = new SquirrelScholarshipRecordReq();
    		scholarship.setId(Integer.valueOf(strList.get(i)));
    		scholarship.setStatus(status);
    		scholarshipApplyForMapper.update(scholarship);
    	}
    	
    	return Tools.s(null);
    }
    
    public String refundScholarship(String bigbayAppId, String bigbaySignKey, PaymentTransactions pt){
		logger.info("开始退款:{}",refundUrl);
		HttpPost postRequest = new HttpPost(refundUrl);
		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		postRequest.setHeader("Accept", "application/json");
		
		JSONObject jsonParamObj = new JSONObject();
		jsonParamObj.put("outTradeNo", pt.getBigbayTranctionnId());
		jsonParamObj.put("refundFee", pt.getTotalFee());
		jsonParamObj.put("revokeDistributorIncome", false);
		
		prepareBigBayRequest(postRequest, jsonParamObj.toString(), String.valueOf(bigbayAppId), bigbaySignKey);
		HttpResponse postResponse = null;
		String responseBody = null;
		try {
			postResponse = httpClient.execute(postRequest);
			responseBody = EntityUtils.toString(postResponse.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.info("StackTrace:{}",e.getStackTrace());
		}
		logger.info("退款结果:{}",responseBody);
		return responseBody;
	}
    
    public static void prepareBigBayRequest(HttpPost request, String content, String bigbayAppId, String signKey) {
		String random = Tools.randomString32Chars();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String signature = sign(bigbayAppId, content, random, timestamp, signKey);
		
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("bigbayAppId", bigbayAppId));
	    params.add(new BasicNameValuePair("content", content));
	    params.add(new BasicNameValuePair("random", random));
	    params.add(new BasicNameValuePair("timestamp", timestamp));
	    params.add(new BasicNameValuePair("signature", signature));
	    
	    UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params,"utf-8");//解决中文乱码问题    
			entity.setContentType("application/x-www-form-urlencoded");  
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	    request.setEntity(entity);
	}
    
    public static String sign(String bigbayAppId, String content, String random, String timestamp, String key) {
		
		StringBuffer buffer = new StringBuffer()
				.append("bigbayAppId=").append(bigbayAppId).append("&")
				.append("content=").append(content).append("&")
				.append("random=").append(random).append("&")
				.append("timestamp=").append(timestamp).append("&")
				.append("key=").append(key);
		return Tools.md5(buffer.toString()).toUpperCase();
		
	}
    
    //****************************************************************************************************************************
    @ResponseBody
    @PostMapping(value = "edit-setting")
    public Map<String,Object> editSetting(
    		@RequestParam(value = "id",required = false)Integer id, 
    		@RequestParam(value = "name",required = true)String name, 
    		@RequestParam(value = "levelId",required = true)Integer levelId,
            @RequestParam(value = "buyStartTime", required = true) String buyStartTime,
            @RequestParam(value = "buyEndTime",required = true)String buyEndTime,
            @RequestParam(value = "cashBackType",required = true)Integer cashBackType,
            @RequestParam(value = "amount",required = false)Double amount,
            @RequestParam(value = "ratio",required = false)Double ratio){
    	
    	SquirrelScholarshipSettingReq scholarship = new SquirrelScholarshipSettingReq();
    	scholarship.setName(name);
    	scholarship.setLevelId(levelId);
    	scholarship.setBuyStartTime(buyStartTime);
    	scholarship.setBuyEndTime(buyEndTime);
    	scholarship.setCashBackType(cashBackType);
        if(ScholarshipCashBackTypeEnum.ScholarshipAmount.getKey().intValue()==cashBackType.intValue()){
        	BigDecimal amountBigDecimal = new BigDecimal(amount);
        	scholarship.setAmount(amountBigDecimal);
        }
        if(ScholarshipCashBackTypeEnum.ScholarshipRatio.getKey().intValue()==cashBackType.intValue()){
        	BigDecimal ratioBigDecimal = new BigDecimal(ratio);
        	scholarship.setRatio(ratioBigDecimal);
        }
        
        //============================================
        SquirrelScholarshipSettingReq scholarshipReq = new SquirrelScholarshipSettingReq();
        if(id != null){
        	scholarshipReq.setId(id);
        }
        if(levelId != null){
        	scholarshipReq.setLevelId(levelId);
        }
        if(buyStartTime!=null){
        	scholarshipReq.setBuyStartTime(buyStartTime);
        }
        if(buyEndTime!=null){
        	scholarshipReq.setBuyEndTime(buyEndTime);
        }
        int intersectionCount = scholarshipMapper.buyTimeRangeRepeatList(scholarshipReq);
        //============================================
        if(intersectionCount!=0){
        	return Tools.f(Constants.SAME_BUY_TIME);
        }
        
        if(id == null){
        	scholarshipMapper.insert(scholarship);
        }else{
        	scholarship.setId(id);
        	scholarshipMapper.update(scholarship);
        }

        return Tools.s(scholarship.getId());
    }
    
    @ResponseBody
    @PostMapping(value = "delete-setting")
    public Map<String,Object> deleteScholarship(@RequestParam(value = "id",required = false)Integer id){


    	scholarshipMapper.delete(id);

        return Tools.s();
    }
    
    @ResponseBody
    @PostMapping(value = "list")
    public Map<String,Object> list(
    		@RequestParam(value = "levelId",required = false)Integer levelId,
    		@RequestParam(value = "buyStartTime", required = false) String buyStartTime,
            @RequestParam(value = "buyEndTime",required = false)String buyEndTime,
            @RequestParam(value = "pageNo",required = false)Integer pageNo,
            @RequestParam(value = "pageSize",required = false)Integer pageSize){
    	if(pageNo==null){pageNo=new Integer(1);}
    	if(pageSize==null){pageSize=new Integer(20);}
        pageNo = (pageNo - 1) * pageSize;
        SquirrelScholarshipSettingReq scholarshipReq = new SquirrelScholarshipSettingReq();
        scholarshipReq.setPageNo(pageNo);
        scholarshipReq.setPageSize(pageSize);
        if(levelId != null){
        	scholarshipReq.setLevelId(levelId);
        }
        if(StringUtils.isNotBlank(buyStartTime)){
        	scholarshipReq.setBuyStartTime(buyStartTime);
        }
        if(StringUtils.isNotBlank(buyEndTime)){
        	scholarshipReq.setBuyEndTime(buyEndTime);
        }
        List<SquirrelScholarshipSetting> list = scholarshipMapper.list(scholarshipReq);
        
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",scholarshipMapper.listCount(scholarshipReq));
        return Tools.s(map);
    }
    
    @ResponseBody
    @PostMapping(value = "info")
    public Map<String,Object> info(@RequestParam(value = "id",required = true)Integer id){
        SquirrelScholarshipSetting scholarship = scholarshipMapper.selectByPrimaryKey(id);
        return Tools.s(scholarship);
    }
    
    @ResponseBody
    @PostMapping(value = "operation-update-status")
	public Map<String,Object> operationUpdateStatus(
    		@RequestParam(value = "id", required = true) Integer id,
    		@RequestParam(value = "operationStatus", required = true) Integer operationStatus){
    	SquirrelScholarshipRecordReq recordReq = new SquirrelScholarshipRecordReq();
    	recordReq.setId(id);
//    	recordReq.setOperationStatus(operationStatus);
    	scholarshipApplyForMapper.update(recordReq);
    	return Tools.s(recordReq);
    }
    
    @ResponseBody
    @PostMapping(value = "reissue-scholarship")
	public Map<String,Object> reissueScholarship(
    		@RequestParam(value = "ids", required = true) String ids){
    	String[] strs = ids.split(",");
    	List<String> strList = Arrays.asList(strs);
    	for(int i=0; i<strList.size(); i++){
    		Integer id = Integer.parseInt(strList.get(i));
    		//重新发放奖学金
    		ScholarshipApplyFor scholarshipApplyFor = scholarshipApplyForMapper.selectById(id);
//    		if(	scholarshipApplyFor!=null 
//    			&& (scholarshipApplyFor.getStatus().intValue()==ScholarshipApplyForStatusEnum.Failure.getKey() 
//    				|| scholarshipApplyFor.getStatus().intValue()==ScholarshipApplyForStatusEnum.ApplyFor.getKey() )
//    			&& scholarshipApplyFor.getOperationStatus().intValue()==ScholarshipOperationStatusEnum.NotIssued.getKey() ){
//    			
//    			issueScholarship(scholarshipApplyFor);
//    			
//    		}
    	}
    	return Tools.s(null);
    }
    
    public void issueScholarship(ScholarshipApplyFor applyFor){
    	
    	EntPayCreateOrderScholarshipRequest orderRequest = new EntPayCreateOrderScholarshipRequest();
		BigDecimal bonusAmount = new BigDecimal(applyFor.getAmount());
		orderRequest.setAmount(bonusAmount.intValue());
//		// my o5SwA1DleGzjahrWkTA0JFinHqgw   gq  o5SwA1NGyHeS9WqaymsnlPGj5WB8  ks o5SwA1JNlIFv_Rr4VRKAW838TdSs
		orderRequest.setOpenid(applyFor.getScholarshipOpenId());//openId   ks o5SwA1JNlIFv_Rr4VRKAW838TdSs  applyFor.getScholarshipOpenId()
		orderRequest.setAppId("wx1afbcff2bdd165c3");
		orderRequest.setDescription("松鼠绘本馆-毕业奖学金入账");
		
		try {
			String localIp = InetAddress.getLocalHost().getHostAddress();
			orderRequest.setSpbillCreateIp(localIp);
		} catch (UnknownHostException e) {
			logger.info("毕业奖学金IP地址解析错误");
		}
		
		orderRequest.setScholarshipOpenId(applyFor.getScholarshipOpenId());
		orderRequest.setBeginAt(applyFor.getBeginAt());
		orderRequest.setLevelId(applyFor.getLevelId());
		//-----------------------------------------------------------------------------------------------------------------------
		ScholarshipApplyFor applyForSearch = new ScholarshipApplyFor();
		applyForSearch.setBeginAt(applyFor.getBeginAt());
		applyForSearch.setScholarshipOpenId(applyFor.getScholarshipOpenId());
		applyForSearch.setLevelId(applyFor.getLevelId());
//    	applyFor.setStatus(ScholarshipApplyForStatusEnum.Success.getKey());
		List<ScholarshipApplyFor> applyForList = scholarshipApplyForMapper.selectApplyForByOpenId(applyFor);
		
		if(!applyForList.isEmpty()){	//	已经成功发放特殊奖学金,不可以再发奖学金，只有失败的才可以重新发放
    		logger.info("申请人:" + applyFor.getScholarshipOpenId() +
    				"LevelId："+ applyFor.getLevelId() +
    				"BeginAt："+ applyFor.getBeginAt() +
    				"已经成功申请奖学金）,不可以再发奖学金！");
    		ScholarshipApplyFor scholarshipApplyFor = applyForList.get(0);
    		
//    		if(scholarshipApplyFor.getStatus().intValue()==ScholarshipApplyForStatusEnum.Success.getKey()){
//    			logger.info("已经成功领取过奖学金，不可以再发奖学金！");
//    			return ;
//			}else if(scholarshipApplyFor.getStatus().intValue()==ScholarshipApplyForStatusEnum.Failure.getKey()){
//				
//			}else if(scholarshipApplyFor.getStatus().intValue()==ScholarshipApplyForStatusEnum.PresetSpecialScholarship.getKey()){
//				return ;
//			}else {
//				return ;
//    		}
    		
    	}else{
    		return ;
    	}
		
		scholarshipService.createOrder(orderRequest, applyFor);
		
    }
    
    @ResponseBody
    @PostMapping(value = "special-scholarship-import-setting")
    public Map<String,Object> specialScholarshipImportSetting(
    		@RequestParam(value = "levelId",required = true)Integer levelId,
    		@RequestParam(value = "openIds",required = false)String openIds, 
    		@RequestParam(value = "amount",required = false)BigDecimal amount,
            @RequestParam(value = "description", required = true) String description){
    	String[] openIdArray = openIds.split(",");
    	List<String> openIdList = Arrays.asList(openIdArray);
    	
    	List<ScholarshipApplyForResult> returnlist = new ArrayList<ScholarshipApplyForResult>();
    	for(int i=0; i<openIdList.size(); i++){
    		String openId = openIdList.get(i);
    		UserLevel userLevel = userMapper.selectUserLevelsByOpenId(openId,levelId);
    		if(userLevel==null){
    			ScholarshipApplyForResult applyForResult = new ScholarshipApplyForResult();
    			applyForResult.setOpenId(openId);
    			applyForResult.setStatus(SpecialScholarshipApplyForErrorEnum.OpenIdIsError.getKey());
    			applyForResult.setDescription(SpecialScholarshipApplyForErrorEnum.OpenIdIsError.getValue());
    			returnlist.add(applyForResult);
    			continue;
    		}
    		ScholarshipApplyFor applyFor = new ScholarshipApplyFor();
    		applyFor.setScholarshipOpenId(openId);
    		applyFor.setLevelId(levelId);
    		applyFor.setBeginAt(userLevel.getBeginAt());
    		List<ScholarshipApplyFor> applyForList = scholarshipApplyForMapper.selectApplyForByOpenId(applyFor);
    		if(!applyForList.isEmpty()){
    			ScholarshipApplyFor scholarshipApplyFor = applyForList.get(0);
    			ScholarshipApplyForResult applyForResult = new ScholarshipApplyForResult();
    			applyForResult.setOpenId(openId);
//    			if(scholarshipApplyFor.getStatus()==ScholarshipApplyForStatusEnum.Success.getKey()){
//    				if(scholarshipApplyFor.getScholarshipType().intValue()==ScholarshipTypeEnum.SpecialScholarship.getKey()){
//    					applyForResult.setStatus(SpecialScholarshipApplyForErrorEnum.AlreadyApplyForSpecialScholarship.getKey());
//            			applyForResult.setDescription(SpecialScholarshipApplyForErrorEnum.AlreadyApplyForSpecialScholarship.getValue());
//    				}else{
//    					applyForResult.setStatus(SpecialScholarshipApplyForErrorEnum.AlreadyApplyFor.getKey());
//            			applyForResult.setDescription(SpecialScholarshipApplyForErrorEnum.AlreadyApplyFor.getValue());
//    				}
//    			}else if(scholarshipApplyFor.getStatus()==ScholarshipApplyForStatusEnum.PresetSpecialScholarship.getKey()){
//        			applyForResult.setStatus(SpecialScholarshipApplyForErrorEnum.PresetSpecialScholarship.getKey());
//        			applyForResult.setDescription(SpecialScholarshipApplyForErrorEnum.PresetSpecialScholarship.getValue());
//    			}else if(scholarshipApplyFor.getStatus()==ScholarshipApplyForStatusEnum.ApplyFor.getKey()){
//        			applyForResult.setStatus(SpecialScholarshipApplyForErrorEnum.AlreadyApplyForButApplyForIng.getKey());
//        			applyForResult.setDescription(SpecialScholarshipApplyForErrorEnum.AlreadyApplyForButApplyForIng.getValue());
//    			}else if(scholarshipApplyFor.getStatus()==ScholarshipApplyForStatusEnum.Failure.getKey()){
//        			applyForResult.setStatus(SpecialScholarshipApplyForErrorEnum.AlreadyApplyForButFailure.getKey());
//        			applyForResult.setDescription(SpecialScholarshipApplyForErrorEnum.AlreadyApplyForButFailure.getValue());
//    			}
    			returnlist.add(applyForResult);
    			continue;
    		}
    		
	    	ScholarshipApplyFor scholarshipApplyFor = new ScholarshipApplyFor();
			BigDecimal bonusAmount=amount.multiply(new BigDecimal(100));//乘以100(单位：分)
			scholarshipApplyFor.setBeginAt(userLevel.getBeginAt());//scholarship.getBeginAt()
			scholarshipApplyFor.setScholarshipOpenId(openId);
			scholarshipApplyFor.setLevelId(levelId);
//			scholarshipApplyFor.setStatus(ScholarshipApplyForStatusEnum.PresetSpecialScholarship.getKey());
			scholarshipApplyFor.setAmount(bonusAmount.intValue());
			scholarshipApplyFor.setCreatedAt(new Date());
			scholarshipApplyFor.setDescription(description);
			scholarshipApplyFor.setScholarshipType(ScholarshipTypeEnum.SpecialScholarship.getKey());
			scholarshipApplyForMapper.insert(scholarshipApplyFor);
    	}
        return Tools.s(returnlist);
    }
    
    @ResponseBody
    @PostMapping(value = "special-scholarship-record")
	public Map<String,Object> specialScholarshipRecord(
			@RequestParam(value = "nickName",required = false)String nickName,
    		@RequestParam(value = "beginAtStartTime", required = false) String beginAtStartTime,
            @RequestParam(value = "beginAtEndTime",required = false)String beginAtEndTime,
            @RequestParam(value = "levelId",required = false)Integer levelId,
            @RequestParam(value = "pageNo",required = false)Integer pageNo,
            @RequestParam(value = "pageSize",required = false)Integer pageSize){
    	if(pageNo==null){pageNo=new Integer(1);}
    	if(pageSize==null){pageSize=new Integer(20);}
        pageNo = (pageNo - 1) * pageSize;
        SquirrelScholarshipRecordReq recordReq = new SquirrelScholarshipRecordReq();
        recordReq.setPageNo(pageNo);
        recordReq.setPageSize(pageSize);
        if(StringUtils.isNotBlank(nickName)){
        	recordReq.setNickName(nickName);
        }
        if(levelId != null){
        	recordReq.setLevelId(levelId);
        }
        if(StringUtils.isNotBlank(beginAtStartTime)){
        	recordReq.setBeginAtStartTime(beginAtStartTime);
        }
        if(StringUtils.isNotBlank(beginAtEndTime)){
//        	recordReq.setBeginAtEndTime(beginAtEndTime);
        }
//        recordReq.setScholarshipType(ScholarshipTypeEnum.SpecialScholarship.getKey());
        List<ScholarshipApplyForResponse> list = scholarshipApplyForMapper.specialScholarshipRecord(recordReq);
        
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",scholarshipApplyForMapper.specialScholarshipRecordCount(recordReq));
        return Tools.s(map);
    }
    
    @ResponseBody
    @PostMapping(value = "special-scholarship-batch-delete")
    public Map<String,Object> deleteBatchScholarshipApplyFor(
    		@RequestParam(value = "ids",required = false)String ids ){
    	
    	String[] idArray = ids.split(",");
    	List<String> idList = Arrays.asList(idArray);
    	
    	for(int i=0; i<idList.size(); i++){
    		Integer id = Integer.valueOf(idList.get(i));
    		scholarshipApplyForMapper.delete(id);
    	}
        return Tools.s();
    }
}
