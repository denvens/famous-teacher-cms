package com.qingclass.squirrel.cms.entity.cms;

import java.io.Serializable;

public class CmsAdmin implements Serializable {

    public static final String SESSION_SQUIRREL_ADMIN_KEY = "SESSION_SQUIRREL_ADMIN_KEY";
    private int id;
    private String loginName;
    private String password;
    private String userName;
    private String plain;
    private String roleName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public CmsAdmin() {
    }

    public CmsAdmin(String loginName, String password, String userName, String plain) {
        this.loginName = loginName;
        this.password = password;
        this.userName = userName;
        this.plain = plain;
    }

    public CmsAdmin(String loginName, String password, String userName, String plain, String roleName) {
        this.loginName = loginName;
        this.password = password;
        this.userName = userName;
        this.plain = plain;
        this.roleName = roleName;
    }
}
