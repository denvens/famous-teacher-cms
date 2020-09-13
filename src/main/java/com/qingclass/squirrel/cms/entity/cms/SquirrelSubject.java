package com.qingclass.squirrel.cms.entity.cms;

import java.io.Serializable;

/**
 * squirrel_SquirrelSubject
 * @author 
 */
public class SquirrelSubject implements Serializable {

    private Integer id;

    private String name;

    public SquirrelSubject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SquirrelSubject{" +
                "id=" + id +
                ", name='" + name + '\'' +
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

    public SquirrelSubject() {
    }
}