package com.qingclass.squirrel.cms.entity.statistic;

import java.io.Serializable;
import java.util.Date;

/**
 * squirrel_kvalue_statistics
 * @author 
 */
public class SquirrelKvalueStatistic implements Serializable {
	
	public static final String INIT = "4";
	public static final String BUYSUCCESS = "6";
	public static final String ONREAD = "1";
	public static final String BUY = "5";
	public static final String CARE = "7";
	public static final String SHARE = "2";
	public static final String GOSHARE = "3";

	
    private Integer id;

    private Date date;

    private Integer onRead;

    private Integer share;

    private Integer goShare;

    private Integer care;

    private Integer init;

    private Integer buy;

    private Integer buySuccess;

    private Date createAt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getOnRead() {
        return onRead;
    }

    public void setOnRead(Integer onRead) {
        this.onRead = onRead;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getGoShare() {
        return goShare;
    }

    public void setGoShare(Integer goShare) {
        this.goShare = goShare;
    }

    public Integer getCare() {
        return care;
    }

    public void setCare(Integer care) {
        this.care = care;
    }

    public Integer getInit() {
        return init;
    }

    public void setInit(Integer init) {
        this.init = init;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public Integer getBuySuccess() {
        return buySuccess;
    }

    public void setBuySuccess(Integer buySuccess) {
        this.buySuccess = buySuccess;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}