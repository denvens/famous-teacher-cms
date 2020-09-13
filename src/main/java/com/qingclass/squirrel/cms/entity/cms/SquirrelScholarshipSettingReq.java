package com.qingclass.squirrel.cms.entity.cms;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class SquirrelScholarshipSettingReq {

	private Integer id;
    private Integer levelId;
    private String buyStartTime;
    private String buyEndTime;

    private String name;
    private Integer cashBackType;
    private BigDecimal amount;
    private BigDecimal ratio;
    
    private Integer pageNo;
    private Integer pageSize;
    

}