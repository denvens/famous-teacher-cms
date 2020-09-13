package com.qingclass.squirrel.cms.entity.cms;

public class LessonMidPicturebook {
    private Integer id;
    private Integer picId;
    private Integer lessonId;
    private Integer part;

    //--temp
    private String name;


    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LessonMidPicturebook{" +
                "id=" + id +
                ", picId=" + picId +
                ", lessonId=" + lessonId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public LessonMidPicturebook() {
    }

    public LessonMidPicturebook(Integer id, Integer picId, Integer lessonId) {
        this.id = id;
        this.picId = picId;
        this.lessonId = lessonId;
    }
}
