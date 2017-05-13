package com.hb.happnissbilldemo;

import com.hb.happnissbilldemo.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by 译丹 on 2017/5/13.
 */

public interface HappinessBillService {
    @PUT("register")
    Call<User> register(@Body User user);
}

