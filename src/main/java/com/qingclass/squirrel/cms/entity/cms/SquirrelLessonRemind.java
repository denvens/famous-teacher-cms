package com.qingclass.squirrel.cms.entity.cms;


public class SquirrelLessonRemind {
    private Integer id;
    private String remindRate;
    private String firstRemind;
    private String secondRemind;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemindRate() {
        return remindRate;
    }

    public void setRemindRate(String remindRate) {
        this.remindRate = remindRate;
    }

    public String getFirstRemind() {
        return firstRemind;
    }

    public void setFirstRemind(String firstRemind) {
        this.firstRemind = firstRemind;
    }

    public String getSecondRemind() {
        return secondRemind;
    }

    public void setSecondRemind(String secondRemind) {
        this.secondRemind = secondRemind;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
