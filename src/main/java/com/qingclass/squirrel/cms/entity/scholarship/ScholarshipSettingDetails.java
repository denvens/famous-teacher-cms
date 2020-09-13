package com.qingclass.squirrel.cms.entity.scholarship;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ScholarshipSettingDetails {
	private String beginAt;
	private Integer subjectId;
	private Integer levelId;
    private String name;
    private Integer lessonDay;
    private Integer beginDays;
    private BigDecimal scholarshipCash;
    private Integer alreadyDays;
    private Integer status;
    
    private String createdAt;
    private String updatedAt;
    
    private String groupLink;
    
    private String vipEndTime;
    
    private String beginClassTime;
    private String endClassTime;
    
    private Integer operationStatus;
    
}
