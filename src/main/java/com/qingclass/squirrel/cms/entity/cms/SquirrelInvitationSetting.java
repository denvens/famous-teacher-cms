package com.qingclass.squirrel.cms.entity.cms;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestParam;

public class SquirrelInvitationSetting {
    private Integer id;
    private String img;
    private String rule;
    private Integer validDays;
    
    private Integer invitationType;
    private BigDecimal bonusAmount;
    private String bonusImg;
    private BigDecimal offerAmount;
    private String offerImg;
    
    private Integer templateId;
    private Integer customId;
    private Integer levelId;
    private String createdAt;
    private Integer isOpen;
    private Integer shareId;


    //--temp
    private String levelName;
    private Integer pageNo;
    private Integer pageSize;
    private Date createdTime;

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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getCustomId() {
        return customId;
    }

    public void setCustomId(Integer customId) {
        this.customId = customId;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public Integer getInvitationType() {
		return invitationType;
	}

	public void setInvitationType(Integer invitationType) {
		this.invitationType = invitationType;
	}

	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public String getBonusImg() {
		return bonusImg;
	}

	public void setBonusImg(String bonusImg) {
		this.bonusImg = bonusImg;
	}

	public BigDecimal getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(BigDecimal offerAmount) {
		this.offerAmount = offerAmount;
	}

	public String getOfferImg() {
		return offerImg;
	}

	public void setOfferImg(String offerImg) {
		this.offerImg = offerImg;
	}
    
    
}
