package com.qingclass.squirrel.cms.entity.user;

import java.util.Date;

public class InvitationRecord {

    private Integer id;
    private Integer invitationUserId;
    private String invitationOpenId;
    private String invitationImg;
    private String invitationNickName;
    private String purchaseUserId;
    private String purchaseOpenId;
    private String purchaseImg;
    private String purchaseNickName;
    private Integer levelId;
    private String createdAt;


    //--temp
    private Integer pageNo;
    private Integer pageSize;
    private Date createdAtA;
    private Date createdAtB;

    private String invitationParams;
    private String purchaseParams;

    private Integer invitationType;
    
    private Integer status;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvitationParams() {
        return invitationParams;
    }

    public void setInvitationParams(String invitationParams) {
        this.invitationParams = invitationParams;
    }

    public String getPurchaseParams() {
        return purchaseParams;
    }

    public void setPurchaseParams(String purchaseParams) {
        this.purchaseParams = purchaseParams;
    }

    public Integer getInvitationUserId() {
        return invitationUserId;
    }

    public void setInvitationUserId(Integer invitationUserId) {
        this.invitationUserId = invitationUserId;
    }

    public String getInvitationOpenId() {
        return invitationOpenId;
    }

    public void setInvitationOpenId(String invitationOpenId) {
        this.invitationOpenId = invitationOpenId;
    }

    public String getInvitationImg() {
        return invitationImg;
    }

    public void setInvitationImg(String invitationImg) {
        this.invitationImg = invitationImg;
    }

    public String getInvitationNickName() {
        return invitationNickName;
    }

    public void setInvitationNickName(String invitationNickName) {
        this.invitationNickName = invitationNickName;
    }

    public String getPurchaseUserId() {
        return purchaseUserId;
    }

    public void setPurchaseUserId(String purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public String getPurchaseOpenId() {
        return purchaseOpenId;
    }

    public void setPurchaseOpenId(String purchaseOpenId) {
        this.purchaseOpenId = purchaseOpenId;
    }

    public String getPurchaseImg() {
        return purchaseImg;
    }

    public void setPurchaseImg(String purchaseImg) {
        this.purchaseImg = purchaseImg;
    }

    public String getPurchaseNickName() {
        return purchaseNickName;
    }

    public void setPurchaseNickName(String purchaseNickName) {
        this.purchaseNickName = purchaseNickName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public Date getCreatedAtA() {
        return createdAtA;
    }

    public void setCreatedAtA(Date createdAtA) {
        this.createdAtA = createdAtA;
    }

    public Date getCreatedAtB() {
        return createdAtB;
    }

    public void setCreatedAtB(Date createdAtB) {
        this.createdAtB = createdAtB;
    }

	public Integer getInvitationType() {
		return invitationType;
	}

	public void setInvitationType(Integer invitationType) {
		this.invitationType = invitationType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
