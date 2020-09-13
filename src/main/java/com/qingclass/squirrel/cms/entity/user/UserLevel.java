package com.qingclass.squirrel.cms.entity.user;

import java.util.Date;

public class UserLevel {
    private int id;
    private int squirrelUserId;
    private int levelId;
    private int transacationId;
    private Date createdAt; //购买时间
    private String beginAt;
    private String vipBeginTime;
    private String vipEndTime;

    //--temp
    private int effectiveDate;
    private int pageNo;
    private int pageSize;
    private String findParam;
    private String nickName;
    private String headImgUrl;
    private String openId;
    private Integer userId;

    private Integer vipDays;
    private Integer sendLessonCount;
    private Integer alreadySendLessonDays;

    public Integer getAlreadySendLessonDays() {
        return alreadySendLessonDays;
    }

    public void setAlreadySendLessonDays(Integer alreadySendLessonDays) {
        this.alreadySendLessonDays = alreadySendLessonDays;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVipDays() {
        return vipDays;
    }

    public void setVipDays(Integer vipDays) {
        this.vipDays = vipDays;
    }

    public Integer getSendLessonCount() {
        return sendLessonCount;
    }

    public void setSendLessonCount(Integer sendLessonCount) {
        this.sendLessonCount = sendLessonCount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFindParam() {
        return findParam;
    }

    public void setFindParam(String findParam) {
        this.findParam = findParam;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(int effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSquirrelUserId() {
        return squirrelUserId;
    }

    public void setSquirrelUserId(int squirrelUserId) {
        this.squirrelUserId = squirrelUserId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getTransacationId() {
        return transacationId;
    }

    public void setTransacationId(int transacationId) {
        this.transacationId = transacationId;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getVipBeginTime() {
        return vipBeginTime;
    }

    public void setVipBeginTime(String vipBeginTime) {
        this.vipBeginTime = vipBeginTime;
    }

    public String getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(String vipEndTime) {
        this.vipEndTime = vipEndTime;
    }
}
