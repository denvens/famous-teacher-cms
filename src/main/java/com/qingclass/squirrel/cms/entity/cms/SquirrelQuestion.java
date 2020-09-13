package com.qingclass.squirrel.cms.entity.cms;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * squirrel_SquirrelQuestion
 * @author 
 */
@Data
public class SquirrelQuestion implements Serializable ,Comparable<SquirrelQuestion>{
    private Integer id;

    private Integer unitId;

    private String questionType;

    private String questionData;

    private Map<String, Object> DataMap;

    private Integer order;

    private String questionKey;
    
    private Integer lessonId;
    
    private Integer pageNo;
    private Integer pageSize;

    @Override
    public String toString() {
        return "SquirrelQuestion{" +
                "id=" + id +
                ", unitId=" + unitId +
                ", questionType='" + questionType + '\'' +
                ", questionData='" + questionData + '\'' +
                ", order=" + order +
                ", questionKey='" + questionKey + '\'' +
                '}';
    }

    public Map<String, Object> getDataMap() {
        return DataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        DataMap = dataMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionData() {
        return questionData;
    }

    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public SquirrelQuestion() {
    }

    public SquirrelQuestion(Integer unitId) {
        this.unitId = unitId;
    }

    public SquirrelQuestion(Integer id, Integer unitId, String questionType, String questionData, Integer order, String questionKey) {
        this.id = id;
        this.unitId = unitId;
        this.questionType = questionType;
        this.questionData = questionData;
        this.order = order;
        this.questionKey = questionKey;
    }

    @Override
    public int compareTo(SquirrelQuestion o) {
        return new Integer(this.getOrder().compareTo(o.getOrder()));
    }
}