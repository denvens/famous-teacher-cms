package com.qingclass.squirrel.cms.entity.cms;

import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * squirrel_lesson
 * @author 
 */
public class SquirrelLesson implements Serializable, Comparable<SquirrelLesson> {
    private Integer id;

    private Integer levelid;

    private String name;

    private Integer order;

    private String lessonkey;

    private int star;

    private Integer audition;

    private Integer isOpen;

    private String title;

    private Date updateDate;

    private String image;

    private Integer relation;
    
    private String shareImage;

    //-----temp
    private String buySite;

    private int picId;

    private int pageNo;

    private int pageSize;

    private int part;

    private String levelName;

    private String picturebookName;

    private SquirrelPicturebook squirrelPicturebook;


    public String getPicturebookName() {
        return picturebookName;
    }

    public void setPicturebookName(String picturebookName) {
        this.picturebookName = picturebookName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public SquirrelPicturebook getSquirrelPicturebook() {
        return squirrelPicturebook;
    }

    public void setSquirrelPicturebook(SquirrelPicturebook squirrelPicturebook) {
        this.squirrelPicturebook = squirrelPicturebook;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getBuySite() {
        return buySite;
    }

    public void setBuySite(String buySite) {
        this.buySite = buySite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Integer getAudition() {
        return audition;
    }

    public void setAudition(Integer audition) {
        this.audition = audition;
    }

    private List<SquirrelQuestion> questionList;
    
    //--temp
    private List<SquirrelUnit> unitList;

    private List<SquirrelWord> wordList;
    
    public List<SquirrelQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<SquirrelQuestion> questionList) {
		this.questionList = questionList;
	}

	public List<SquirrelWord> getWordList() {
        return wordList;
    }

    public void setWordList(List<SquirrelWord> wordList) {
        this.wordList = wordList;
    }

    public List<SquirrelUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<SquirrelUnit> unitList) {
        this.unitList = unitList;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelid() {
        return levelid;
    }

    public void setLevelid(Integer levelid) {
        this.levelid = levelid;
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

    public String getLessonkey() {
        return lessonkey;
    }


    public SquirrelLesson() {
    }

    public SquirrelLesson(String name) {
        this.name = name;
    }

    public void setLessonkey(String lessonkey) {
        this.lessonkey = lessonkey;
    }

    public String getShareImage() {
		return shareImage;
	}

	public void setShareImage(String shareImage) {
		this.shareImage = shareImage;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SquirrelLesson other = (SquirrelLesson) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLevelid() == null ? other.getLevelid() == null : this.getLevelid().equals(other.getLevelid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getOrder() == null ? other.getOrder() == null : this.getOrder().equals(other.getOrder()))
            && (this.getLessonkey() == null ? other.getLessonkey() == null : this.getLessonkey().equals(other.getLessonkey()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLevelid() == null) ? 0 : getLevelid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getOrder() == null) ? 0 : getOrder().hashCode());
        result = prime * result + ((getLessonkey() == null) ? 0 : getLessonkey().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", levelid=").append(levelid);
        sb.append(", name=").append(name);
        sb.append(", order=").append(order);
        sb.append(", lessonkey=").append(lessonkey);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


    @Override
    public int compareTo(SquirrelLesson o) {
        return new Integer(this.getOrder().compareTo(o.getOrder()));
    }
}