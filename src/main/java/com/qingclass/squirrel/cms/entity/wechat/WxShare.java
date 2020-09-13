package com.qingclass.squirrel.cms.entity.wechat;

public class WxShare {
    private int id;
    private String url;
    private String spaceTitle;
    private String freTitle;
    private String content;
    private String img;
    private String channelQr;
    private String channelQrSite;
    private String type;
    private String shareContent;

    //--temp
    private Integer channelId;
    //---分页
    private Integer pageNo;

    private Integer pageTotal;

    private Integer pageSize;

    private Integer levelId;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getChannelQrSite() {
        return channelQrSite;
    }

    public void setChannelQrSite(String channelQrSite) {
        this.channelQrSite = channelQrSite;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelQr() {
        return channelQr;
    }

    public void setChannelQr(String channelQr) {
        this.channelQr = channelQr;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpaceTitle() {
        return spaceTitle;
    }

    public void setSpaceTitle(String spaceTitle) {
        this.spaceTitle = spaceTitle;
    }

    public String getFreTitle() {
        return freTitle;
    }

    public void setFreTitle(String freTitle) {
        this.freTitle = freTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
