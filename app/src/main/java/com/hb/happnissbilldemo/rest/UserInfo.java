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

    private String oldPassword;

    private String password;

    private int role;

    private String family;

    public UserInfo(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.oldPassword = password;
        this.role = -1;
        this.family = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}