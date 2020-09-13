package com.qingclass.squirrel.cms.entity.cms;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

/**
 * squirrel_SquirrelWord
 * @author 
 */
public class SquirrelWord implements Serializable {
    private Integer id;

    private String word;

    private String translation; //释义

    private String voice;   //音频路径

    private String keyImage;    //唯一图片路径

    private String baseImage;   //基础图片：不是唯一路径

    private String confusionImage;  //混搅图片路径

    //--temp
    private int isKey;

    //---分页
    private Integer pageNo;

    private Integer pageTotal;

    private Integer pageSize;

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

    @Override
    public String toString() {
        return "SquirrelWord{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", voice='" + voice + '\'' +
                ", keyImage='" + keyImage + '\'' +
                ", baseImage='" + baseImage + '\'' +
                ", confusionImage='" + confusionImage + '\'' +
                '}';
    }

    public int getIsKey() {
        return isKey;
    }

    public void setIsKey(int isKey) {
        this.isKey = isKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getKeyImage() {
        return keyImage;
    }

    public void setKeyImage(String keyImage) {
        this.keyImage = keyImage;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }

    public String getConfusionImage() {
        return confusionImage;
    }

    public void setConfusionImage(String confusionImage) {
        this.confusionImage = confusionImage;
    }

    public SquirrelWord() {
    }

    public SquirrelWord(Integer id, String word, String translation, String voice, String keyImage, String baseImage, String confusionImage) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.voice = voice;
        this.keyImage = keyImage;
        this.baseImage = baseImage;
        this.confusionImage = confusionImage;
    }
}