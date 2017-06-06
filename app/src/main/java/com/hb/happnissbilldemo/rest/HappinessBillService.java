package com.hb.happnissbilldemo.rest;

import com.hb.happnissbilldemo.rest.Record;
import com.hb.happnissbilldemo.rest.FamilyInfo;
import com.hb.happnissbilldemo.rest.UserInfo;

import java.sql.Timestamp;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 译丹 on 2017/5/13.
 */

public interface HappinessBillService {
    @POST("user/{name}")
    Call<ResponseBody> register(@Path("name") String name
            , @Query("password") String password
            , @Query("email") String email
            , @Query("phonenumber") String phonenumber);

    @GET("user/{name}")
    Call<UserInfo> getUserInfo(@Path("name") String name
            , @Query("password") String password);

    @PUT("user/{name}")
    Call<ResponseBody> changeUserInfo(@Path("name") String name
            , @Query("password") String password
            , @Query("newpassword") String newpassword
            , @Query("email") String email
            , @Query("phonenumber") String phonenumber);

    @POST("family/{name}")
    Call<ResponseBody> createFamily(@Path("name") String name
            , @Query("password") String password);

    @PUT("family/{name}/{username}")
    Call<ResponseBody> joinFamily(@Path("name") String familyname
            , @Path("username") String username
            , @Query("password") String password
            , @Query("code") String code);

    @PUT("family/{username}")
    Call<ResponseBody> unjoinFamily(@Path("username") String username
            , @Query("password") String password);

    @GET("family/{username}")
    Call<FamilyInfo> getFamilyInfo(@Path("username") String username
            , @Query("password") String password);

    @DELETE("family/{name}/{username}")
    Call<ResponseBody> deleteMember(@Path("name") String parentname
            , @Path("username") String username
            , @Query("password") String password);

    @POST("type/{name}")
    Call<ResponseBody> addType(@Path("name") String type
            , @Query("parentname") String parentname
            , @Query("password") String password);

    @POST("record/{username}")
    Call<ResponseBody> addRecord(@Path("username") String username
            , @Query("password") String password
            , @Query("amount") float amount
            , @Query("type") String type
            , @Query("comment") String comment);

    @GET("record/{username}")
    Call<List<Record>> getRecords(@Path("username") String username
            , @Query("password") String password
            , @Query("members") String[] members
            , @Query("types") String[] types
            , @Query("start") Timestamp start
            , @Query("end") Timestamp end
            , @Query("first") int first
            , @Query("count") int count);
}

