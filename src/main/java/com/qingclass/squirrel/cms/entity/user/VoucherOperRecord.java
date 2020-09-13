package com.qingclass.squirrel.cms.entity.user;

import java.util.Date;

public class VoucherOperRecord {
    private Integer id;
    private Integer voucherId;
    private Integer type;
    private Integer adminId;
    private String createdAt;
    private String updatedAt;

    //--temp
    private Date updateAtA;
    private Date updateAtB;
    private Date createdAtA;
    private Date createdAtB;
    private String nickName;
    private String img;
    private String openId;
    private String adminName;
    private Integer levelId;
    private Integer pageNo;
    private Integer pageSize;
    private String userParams;

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

    public String getUserParams() {
        return userParams;
    }

    public void setUserParams(String userParams) {
        this.userParams = userParams;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdateAtA() {
        return updateAtA;
    }

    public void setUpdateAtA(Date updateAtA) {
        this.updateAtA = updateAtA;
    }

    public Date getUpdateAtB() {
        return updateAtB;
    }

    public void setUpdateAtB(Date updateAtB) {
        this.updateAtB = updateAtB;
    }
}
