package com.qingclass.squirrel.cms.entity.cms;

public class SquirrelPicturebook {
    private Integer id;
    private String name;
    private Integer part;
    private String image;
    private String updateTime;

    //---分页
    private Integer pageNo;

    private Integer pageTotal;

    private Integer pageSize;

    //--temp
    private String usePart;

    private int lessonId;

    private int levelId;

    private boolean none = false;

    public boolean isNone() {
        return none;
    }

    public void setNone(boolean none) {
        this.none = none;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getUsePart() {
        return usePart;
    }

    public void setUsePart(String usePart) {
        this.usePart = usePart;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    @Override
    public String toString() {
        return "SquirrelPicturebookService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", part=" + part +
                ", image='" + image + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SquirrelPicturebook() {
    }

    public SquirrelPicturebook(Integer id, String name, Integer part, String image) {
        this.id = id;
        this.name = name;
        this.part = part;
        this.image = image;
    }
}
