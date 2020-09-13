package com.qingclass.squirrel.cms.entity.cms;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.List;

/**
 * squirrel_SquirrelUnit
 * @author 
 */
public class SquirrelUnit implements Serializable ,Comparable<SquirrelUnit>{

    private Integer id;

    private Integer lessonId;

    private String name;

    private Integer order;

    private Integer isOpen;

    //---temp
    private Integer nextUnitId;

    public Integer getNextUnitId() {
        return nextUnitId;
    }

    public void setNextUnitId(Integer nextUnitId) {
        this.nextUnitId = nextUnitId;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    //--temp
    private List<SquirrelQuestion> questionList;

    public List<SquirrelQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<SquirrelQuestion> questionList) {
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return "SquirrelUnit{" +
                "id=" + id +
                ", lessonId=" + lessonId +
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

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
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

    public SquirrelUnit() {
    }

    public SquirrelUnit(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public SquirrelUnit(Integer id, Integer lessonId, String name, Integer order) {
        this.id = id;
        this.lessonId = lessonId;
        this.name = name;
        this.order = order;
    }

    //排序改造
    @Override
    public int compareTo(SquirrelUnit o) {
        return new Integer(this.getOrder().compareTo(o.getOrder()));
    }
}