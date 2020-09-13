package com.qingclass.squirrel.cms.entity.cms;

import java.util.List;

public class RequestInfo {
    private Boolean denied; //  登录状态,丢失返回true

    private Boolean success;    //  请求状态，成功返回true

    private String message;     //  请求状态为false时，返回错误信息

    private Object dataObject;  //  返回对象数据

    private Object dataList;  //  返回集合数据

    private int orderMax;   // order最大值

    public int getOrderMax() {
        return orderMax;
    }

    public void setOrderMax(int orderMax) {
        this.orderMax = orderMax;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
    }

    public RequestInfo() {
        this.denied = false;
        this.success = true;
        this.message = null;
    }

    /**
     * @param denied:登录状态
     *        success:请求状态
     *        message:错误信息
     * */
    public RequestInfo(Boolean denied, Boolean success, String message){
        this.denied = denied;
        this.success = success;
        this.message = message;
    }
}
