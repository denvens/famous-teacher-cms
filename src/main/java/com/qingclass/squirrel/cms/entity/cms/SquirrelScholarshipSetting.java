package com.qingclass.squirrel.cms.entity.cms;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class SquirrelScholarshipSetting {
    private Integer id;
    private String name;
    private Integer levelId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  
    private Date buyStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  
    private Date buyEndTime;
    
    private Integer cashBackType;
    private BigDecimal amount;
    private BigDecimal ratio;
    
    private String levelName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
    private Date created;
    
}