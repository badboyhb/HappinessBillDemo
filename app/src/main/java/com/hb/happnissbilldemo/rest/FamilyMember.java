package com.hb.happnissbilldemo.rest;

/**
 * Created by HB on 2017/5/17.
 */

public class FamilyMember {

    private String userName;

    private String password;

    private String memberName;

    public FamilyMember(String userName, String password, String memberName) {
        this.userName = userName;
        this.password = password;
        this.memberName = memberName;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
