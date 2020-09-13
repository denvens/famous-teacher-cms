package com.qingclass.squirrel.cms.entity.user;

import java.util.Map;


public class User {

    private Integer id;
    private String openId;
    private String unionId;
    private String nickName;
    private Integer sex;
    private String headImgUrl;

    //--temp
    private Integer alreadyStudyDays;
    private Integer alreadyFinishDays;
    private Integer alreadyShareDays;
    private Integer alreadySendDays;
    private Integer alreadyCardDays;

    private Integer subscribe; //关注情况 0：未关注 1：已关注
    private Integer isVip;  //会员身份 0：非会员 1：会员
    private String createdAt;
    private String vipBeginTime;
    private String vipEndTime;

    private String vipBeginTimeA;
    private String vipBeginTimeB;
    private String vipEndTimeA;
    private String vipEndTimeB;
    private String createdAtA;
    private String createdAtB;
    private String beginAt;

    //--page
    private Integer pageNo;
    private Integer pageSize;
    private Integer levelId;
    
    private String levelName;
    
    Map<String, String> studyRecordMap;

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public Integer getAlreadyCardDays() {
        return alreadyCardDays;
    }

    public void setAlreadyCardDays(Integer alreadyCardDays) {
        this.alreadyCardDays = alreadyCardDays;
    }

    public String getVipBeginTimeA() {
        return vipBeginTimeA;
    }

    public void setVipBeginTimeA(String vipBeginTimeA) {
        this.vipBeginTimeA = vipBeginTimeA;
    }

    public String getVipBeginTimeB() {
        return vipBeginTimeB;
    }

    public void setVipBeginTimeB(String vipBeginTimeB) {
        this.vipBeginTimeB = vipBeginTimeB;
    }

    public String getVipEndTimeA() {
        return vipEndTimeA;
    }

    public void setVipEndTimeA(String vipEndTimeA) {
        this.vipEndTimeA = vipEndTimeA;
    }

    public String getVipEndTimeB() {
        return vipEndTimeB;
    }

    public void setVipEndTimeB(String vipEndTimeB) {
        this.vipEndTimeB = vipEndTimeB;
    }

    public String getCreatedAtA() {
        return createdAtA;
    }

    public void setCreatedAtA(String createdAtA) {
        this.createdAtA = createdAtA;
    }

    public String getCreatedAtB() {
        return createdAtB;
    }

    public void setCreatedAtB(String createdAtB) {
        this.createdAtB = createdAtB;
    }

    public String getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(String vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public String getVipBeginTime() {
        return vipBeginTime;
    }

    public void setVipBeginTime(String vipBeginTime) {
        this.vipBeginTime = vipBeginTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getAlreadyStudyDays() {
        return alreadyStudyDays;
    }

    public void setAlreadyStudyDays(Integer alreadyStudyDays) {
        this.alreadyStudyDays = alreadyStudyDays;
    }

    public Integer getAlreadyFinishDays() {
        return alreadyFinishDays;
    }

    public void setAlreadyFinishDays(Integer alreadyFinishDays) {
        this.alreadyFinishDays = alreadyFinishDays;
    }

    public Integer getAlreadyShareDays() {
        return alreadyShareDays;
    }

    public void setAlreadyShareDays(Integer alreadyShareDays) {
        this.alreadyShareDays = alreadyShareDays;
    }

    public Integer getAlreadySendDays() {
        return alreadySendDays;
    }

    public void setAlreadySendDays(Integer alreadySendDays) {
        this.alreadySendDays = alreadySendDays;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
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

    
    
    public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Map<String, String> getStudyRecordMap() {
		return studyRecordMap;
	}

	public void setStudyRecordMap(Map<String, String> studyRecordMap) {
		this.studyRecordMap = studyRecordMap;
	}

	public User() {
    }
}
