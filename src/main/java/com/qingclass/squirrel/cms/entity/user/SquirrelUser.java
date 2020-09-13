package com.qingclass.squirrel.cms.entity.user;

import java.util.Date;

public class SquirrelUser {

	private int id;
	private String openId;
	private String unionId;
	private String nickName;
	private int sex;
	private String headImgUrl;
	private Integer subscribe;
	private Integer bgmStatus;

	//--temp

	private String beginAt;
	private String vipBeginTime;
	private String vipEndTime;

	private Date beginAtDate;
	private Date vipBeginDate;
	private Date vipEndDate;
	private Integer userLevelId;

	private String buySite;
	private String levelName;
	private Integer levelId;
	
	private Integer sendLessonDays;

	private Integer userId;
	
	public Integer getBgmStatus() {
		return bgmStatus;
	}

	public void setBgmStatus(Integer bgmStatus) {
		this.bgmStatus = bgmStatus;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public Integer getSendLessonDays() {
		return sendLessonDays;
	}

	public void setSendLessonDays(Integer sendLessonDays) {
		this.sendLessonDays = sendLessonDays;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Date getBeginAtDate() {
		return beginAtDate;
	}

	public void setBeginAtDate(Date beginAtDate) {
		this.beginAtDate = beginAtDate;
	}

	public Date getVipBeginDate() {
		return vipBeginDate;
	}

	public void setVipBeginDate(Date vipBeginDate) {
		this.vipBeginDate = vipBeginDate;
	}

	public Date getVipEndDate() {
		return vipEndDate;
	}

	public void setVipEndDate(Date vipEndDate) {
		this.vipEndDate = vipEndDate;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getBuySite() {
		return buySite;
	}

	public void setBuySite(String buySite) {
		this.buySite = buySite;
	}

	public Integer getUserLevelId() {
		return userLevelId;
	}

	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
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

	public String getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(String beginAt) {
		this.beginAt = beginAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
