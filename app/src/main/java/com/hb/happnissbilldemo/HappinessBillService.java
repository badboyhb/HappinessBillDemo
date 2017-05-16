package com.hb.happnissbilldemo;

import com.hb.happnissbilldemo.rest.Record;
import com.hb.happnissbilldemo.rest.RestRecord;
import com.hb.happnissbilldemo.rest.UserFamily;
import com.hb.happnissbilldemo.rest.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by 译丹 on 2017/5/13.
 */

public interface HappinessBillService {
    @PUT("register")
    Call<UserInfo> register(@Body UserInfo user);

    @POST("check")
    Call<UserInfo> check(@Body UserInfo user);

    @POST("changeUserInfo")
    Call<UserInfo> changeUserInfo(@Body UserInfo user);

    @POST("createFamily")
    Call<UserInfo> createFamily(@Body UserInfo user);

    @POST("addRecord")
    Call<Record> addRecord(@Body RestRecord user);

    @POST("joinFamily")
    Call<UserFamily> joinFamily(@Body UserFamily uf);

    @POST("unjoinFamily")
    Call<UserFamily> unjoinFamily(@Body UserFamily uf);

    @POST("getFamilyInfo")
    Call<UserFamily> getFamilyInfo(@Body UserFamily uf);
}

