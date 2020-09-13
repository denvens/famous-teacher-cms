package com.qingclass.squirrel.cms.entity.statistic;

import java.util.Date;

public class ChannelDetailStatistic {
    private String nickName;
    private String headImgUrl;
    private String openId;
    private Integer subscribe;
    private Integer levelOne;
    private Integer levelTwo;
    private String param;
    private Date beginTime;
    private Date endTime;
    private String code;
    private Integer PageNo;
    private Integer pageSize;

    public Integer getPageNo() {
        return PageNo;
    }

    public void setPageNo(Integer pageNo) {
        PageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
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
}
