package com.hb.happnissbilldemo.rest;

/**
 * Created by HB on 2017/5/16.
 */

public class UserFamily {

    private String userName;

    private String password;

    private String familyName;

    private String parentName;

    private String code;

    private String types;

    private String[] members;

    public UserFamily(String userName, String password, String familyName, String code) {
        this.userName = userName;
        this.password = password;
        this.familyName = familyName;
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }
}
