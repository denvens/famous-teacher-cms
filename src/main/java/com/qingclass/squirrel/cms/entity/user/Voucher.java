package com.qingclass.squirrel.cms.entity.user;

import java.util.Date;

public class Voucher {
    private Integer id;
    private Integer squirrelUserId;
    private Integer levelId;
    private String createdAt;
    private Integer status;
    private String useTime;
    private Integer isOpen;

    //--temp
    private Integer pageNo;
    private Integer pageSize;
    private Date createdAtA;
    private Date createdAtB;
    private Date useTimeA;
    private Date useTimeB;
    private String userParams;
    private String nickName;
    private String img;
    private String openId;


    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSquirrelUserId() {
        return squirrelUserId;
    }

    public void setSquirrelUserId(Integer squirrelUserId) {
        this.squirrelUserId = squirrelUserId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
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

    public Date getUseTimeA() {
        return useTimeA;
    }

    public void setUseTimeA(Date useTimeA) {
        this.useTimeA = useTimeA;
    }

    public Date getUseTimeB() {
        return useTimeB;
    }

    public void setUseTimeB(Date useTimeB) {
        this.useTimeB = useTimeB;
    }

    public String getUserParams() {
        return userParams;
    }

    public void setUserParams(String userParams) {
        this.userParams = userParams;
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
}
