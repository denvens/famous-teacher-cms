package com.qingclass.squirrel.cms.entity.cms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ScholarshipApplyForResponse {
	
	private long id;
	private String nickName;
	private String headImgUrl;
	private String openId;
	private String levelName;
	private Integer levelId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  
    private Date createdAt;
	private BigDecimal amount;
	private String description;
	private Integer status;
	private String beginAt;
   
    
}