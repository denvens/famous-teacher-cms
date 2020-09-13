package com.qingclass.squirrel.cms.entity.scholarship;

import lombok.Data;

import java.util.Date;

@Data
public class EntPayOrderScholarship {
    private long id;
    private String scholarshipOpenId;  
    private Integer levelId;
    private int amount;
    private String orderId;
    private String openId;
    
    private String partnerTradeNo;
    private String paymentNo;
    private Date paymentTime;
    
    private String spbillCreateIp;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String beginAt;
}