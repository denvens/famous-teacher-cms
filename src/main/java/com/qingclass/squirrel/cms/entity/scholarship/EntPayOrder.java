package com.qingclass.squirrel.cms.entity.scholarship;

import lombok.Data;

import java.util.Date;

@Data
public class EntPayOrder {
    private long id;
    private String invitationOpenId;  
    private String purchaseOpenId;
    private Integer levelId;
    private String orderId;
    private int amount;
    private String openId;
    
    private String partnerTradeNo;
    private String paymentNo;
    private Date paymentTime;
    
    private String spbillCreateIp;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    
    private Integer invitationUserId;
}