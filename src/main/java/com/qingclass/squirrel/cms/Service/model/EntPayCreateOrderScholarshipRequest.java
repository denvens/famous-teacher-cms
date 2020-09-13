package com.qingclass.squirrel.cms.Service.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EntPayCreateOrderScholarshipRequest {
    private Integer amount;
    private String openid;
    private String description;
    private String appId;
    private String spbillCreateIp;
    
    private String scholarshipOpenId;
    private Integer levelId;
    private BigDecimal scholarshipCash;
    
    private String beginAt;
    
}
