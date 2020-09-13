package com.qingclass.squirrel.cms.entity.statistic;

import lombok.Data;

@Data
public class InvitationSendCashStatistic {
    private String currentDate;
    private String startClassDate;
   
    private Integer beginDays;
    private Integer beginPeoples;
    
    private Integer intoSendCashPageCount;
    private String intoSendPercentage;
    private Integer sendInvitationCount;
    private String sendInvitationPercentage;
    private Integer gotoBuyPageCount;
    private String gotoBuyPagePercentage;
    private Integer clickBuyCount;
    private String clickBuyPercentage;
    private Integer purchaseCount;
    private String purchasePercentage;
    
    
}
