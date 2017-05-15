package com.hb.happnissbilldemo.rest;

/**
 * Created by HB on 2017/5/15.
 */

public class RestRecord {

    private String userName;

    private String password;

    private float amount;

    private String type;

    private String comment;

    public RestRecord(String userName, String password, float amount, String type, String comment) {
        this.userName = userName;
        this.password = password;
        this.amount = amount;
        this.type = type;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
