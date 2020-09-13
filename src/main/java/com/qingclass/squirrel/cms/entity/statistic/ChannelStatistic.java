package com.qingclass.squirrel.cms.entity.statistic;

import java.util.Date;

public class ChannelStatistic {
    private String date;
    private Integer subscribeCount;
    private Integer removeCount;
    private Integer clearCount;
    private Integer levelOne;
    private Integer levelTwo;
    private String code;

    private String param;
    private Date beginTime;
    private Date endTime;

    private String channelCode;

    private Integer pageNo;
    private Integer pageSize;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Integer subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Integer getRemoveCount() {
        return removeCount;
    }

    public void setRemoveCount(Integer removeCount) {
        this.removeCount = removeCount;
    }

    public Integer getClearCount() {
        return clearCount;
    }

    public void setClearCount(Integer clearCount) {
        this.clearCount = clearCount;
    }

    public Integer getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(Integer levelOne) {
        this.levelOne = levelOne;
    }

    public Integer getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(Integer levelTwo) {
        this.levelTwo = levelTwo;
    }
}
