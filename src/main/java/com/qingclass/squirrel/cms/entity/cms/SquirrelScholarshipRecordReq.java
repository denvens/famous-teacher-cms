package com.qingclass.squirrel.cms.entity.cms;

import lombok.Data;

@Data
public class SquirrelScholarshipRecordReq {

	private Integer id;
	private String nickName;
	private String openId;
    private Integer levelId;
    private Integer status;
    private Integer refundStatus;
    private String beginAtStartTime;
    private Integer pageNo;
    private Integer pageSize;
    
}