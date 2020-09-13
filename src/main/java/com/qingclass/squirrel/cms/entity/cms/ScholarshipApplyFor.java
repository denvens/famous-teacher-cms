package com.qingclass.squirrel.cms.entity.cms;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ScholarshipApplyFor {
	
	private String nickName;
	private String openId;
	private String levelName;
	private Integer status;
	private Integer refundStatus;
	private String beginClassTime;
	private Integer learnDay;
    private Integer makeUpLearnDay;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  
    private Date updatedAt;
    private int amount;
    private String bigbayTranctionId; 
    
    
    private long entPayOrderScholarshipId; 
    

    private String beginAt;
    private long id;
    
    private String scholarshipOpenId;
    private Integer levelId;
    
    private String headImgUrl;
    
    private Integer operationStatus;
    
    private String vipBeginTime;
    private String vipEndTime;
    
    private Integer userId;
    
    private String endClassTime;
    
    private String description;
    
    private Integer scholarshipType;
}