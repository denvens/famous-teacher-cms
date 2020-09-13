package com.qingclass.squirrel.cms.entity.cms;

import com.qingclass.squirrel.cms.entity.wechat.WxShare;

import java.io.Serializable;
import java.util.Date;

/**
 * squirrel_SquirrelLevel
 * @author 
 */
public class SquirrelLevel implements Serializable {
    private Integer id;

    private Integer subjectId;

    private String name;

    private Integer order;

    private Integer minWord;

    private Integer maxWord;

    private Date updateDate;

    private String image;

    private Integer isOpen;

    private String buySite;

    private Integer shareId;

    private String skin;
    
    private int lessonDay;

    private Integer channelId;
    
    private String introduction;
    
    
    private Integer returnFeeDay;

    //---temp

    private WxShare wxShare;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLessonDay() {
		return lessonDay;
	}

	public void setLessonDay(int lessonDay) {
		this.lessonDay = lessonDay;
	}

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public WxShare getWxShare() {
        return wxShare;
    }

    public void setWxShare(WxShare wxShare) {
        this.wxShare = wxShare;
    }

    public String getBuySite() {
        return buySite;
    }

    public void setBuySite(String buySite) {
        this.buySite = buySite;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getMinWord() {
        return minWord;
    }

    public void setMinWord(Integer minWord) {
        this.minWord = minWord;
    }

    public Integer getMaxWord() {
        return maxWord;
    }

    public void setMaxWord(Integer maxWord) {
        this.maxWord = maxWord;
    }

    public SquirrelLevel(Integer id, Integer subjectId, String name, Integer order) {
        this.id = id;
        this.subjectId = subjectId;
        this.name = name;
        this.order = order;
    }

    public SquirrelLevel() {
    }

    @Override
    public String toString() {
        return "SquirrelLevel{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getReturnFeeDay() {
		return returnFeeDay;
	}

	public void setReturnFeeDay(Integer returnFeeDay) {
		this.returnFeeDay = returnFeeDay;
	}

}