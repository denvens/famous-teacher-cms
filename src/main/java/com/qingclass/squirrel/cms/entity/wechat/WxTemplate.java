package com.qingclass.squirrel.cms.entity.wechat;

import java.util.Map;

public class WxTemplate {
    private Integer id;
    private String type;
    private String content;
    private Integer isOpen;
    private String url;

    //--temp
    private Map<String,Object> contentMap;

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, Object> contentMap) {
        this.contentMap = contentMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
