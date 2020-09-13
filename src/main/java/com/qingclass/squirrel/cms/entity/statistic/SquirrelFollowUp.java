package com.qingclass.squirrel.cms.entity.statistic;

import java.util.Date;

public class SquirrelFollowUp {
    private Integer id;
    private Integer levelId;
    private String openId;
    private Integer clickWordUp;
    private Integer clickPicbookUp;
    private Integer finishWordUp;
    private Integer finishPicbookUp;
    private Integer shareWordUp;
    private Integer sharePicbookUp;
    private Date createdAt;
    private Integer finishAllUp;
    private Integer purchaseCount;

    private Integer entryShare;
    private Integer onRead;
    private String date;

    //--temp
    private Integer pageNo;
    private Integer pageSize;
    private Date startTime;
    private Date endTime;

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Integer getFinishAllUp() {
        return finishAllUp;
    }

    public void setFinishAllUp(Integer finishAllUp) {
        this.finishAllUp = finishAllUp;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public Integer getOnRead() {
        return onRead;
    }

    public void setOnRead(Integer onRead) {
        this.onRead = onRead;
    }

    public Integer getEntryShare() {
        return entryShare;
    }

    public void setEntryShare(Integer entryShare) {
        this.entryShare = entryShare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getClickWordUp() {
        return clickWordUp;
    }

    public void setClickWordUp(Integer clickWordUp) {
        this.clickWordUp = clickWordUp;
    }

    public Integer getClickPicbookUp() {
        return clickPicbookUp;
    }

    public void setClickPicbookUp(Integer clickPicbookUp) {
        this.clickPicbookUp = clickPicbookUp;
    }

    public Integer getFinishWordUp() {
        return finishWordUp;
    }

    public void setFinishWordUp(Integer finishWordUp) {
        this.finishWordUp = finishWordUp;
    }

    public Integer getFinishPicbookUp() {
        return finishPicbookUp;
    }

    public void setFinishPicbookUp(Integer finishPicbookUp) {
        this.finishPicbookUp = finishPicbookUp;
    }

    public Integer getShareWordUp() {
        return shareWordUp;
    }

    public void setShareWordUp(Integer shareWordUp) {
        this.shareWordUp = shareWordUp;
    }

    public Integer getSharePicbookUp() {
        return sharePicbookUp;
    }

    public void setSharePicbookUp(Integer sharePicbookUp) {
        this.sharePicbookUp = sharePicbookUp;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
