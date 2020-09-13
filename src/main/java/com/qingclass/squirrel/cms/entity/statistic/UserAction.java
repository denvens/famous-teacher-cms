package com.qingclass.squirrel.cms.entity.statistic;

import java.io.Serializable;
import java.util.Date;

/**
 * user_actions
 * @author 
 */
public class UserAction implements Serializable {
    private Integer id;

    private String openId;

    private Date createAt;

    /**
     * 行为类型
     */
    private String type;

    /**
     * 埋点信息
     */
    private String note;

    private static final long serialVersionUID = 1L;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}