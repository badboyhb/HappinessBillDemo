package com.hb.happnissbilldemo.rest;

/**
 * Created by 译丹 on 2017/5/13.
 */

public class UserInfo {

    public static final int ROLE_USER = 0;
    public static final int ROLE_PARENT = 1;
    public static final int ROLE_ADMIN = 2;

    private String name;

    private String email;

    private String phoneNumber;

    private int role;

    private String family;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}