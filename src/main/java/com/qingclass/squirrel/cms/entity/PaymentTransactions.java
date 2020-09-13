package com.qingclass.squirrel.cms.entity;

public class PaymentTransactions {
    private Integer id;
    private String wechatTransactionId;
    private String bigbayTranctionId;
    private String merchantionId;
    private String appId;
    private String totalFee;
    private String tradeType;
    private String bankType;
    private String openId;
    private String feeType;
    private String clientIp;
    private String sellPageItemConfig;
    private String timeEnd;

    @Override
    public String toString() {
        return "PaymentTransactions{" +
                "id=" + id +
                ", wechatTransactionId='" + wechatTransactionId + '\'' +
                ", bigbayTranctionId='" + bigbayTranctionId + '\'' +
                ", merchantionId='" + merchantionId + '\'' +
                ", appId='" + appId + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", bankType='" + bankType + '\'' +
                ", openId='" + openId + '\'' +
                ", feeType='" + feeType + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", sellPageItemConfig='" + sellPageItemConfig + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWechatTransactionId() {
        return wechatTransactionId;
    }

    public void setWechatTransactionId(String wechatTransactionId) {
        this.wechatTransactionId = wechatTransactionId;
    }

    public String getBigbayTranctionnId() {
        return bigbayTranctionId;
    }

    public void setBigbayTranctionnId(String bigbayTranctionnId) {
        this.bigbayTranctionId = bigbayTranctionnId;
    }

    public String getMerchantionId() {
        return merchantionId;
    }

    public void setMerchantionId(String merchantionId) {
        this.merchantionId = merchantionId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSellPageItemConfig() {
        return sellPageItemConfig;
    }

    public void setSellPageItemConfig(String sellPageItemConfig) {
        this.sellPageItemConfig = sellPageItemConfig;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
