package com.qingclass.squirrel.cms.entity.statistic;

import java.util.Date;

import lombok.Data;

@Data
public class InvitationSendCashStatisticReq {
	private Integer levelId;
    private String startTime;
    private String endTime;

    private Integer pageNo;
    private Integer pageSize;
    
}
