package com.hb.happnissbilldemo;

import com.hb.happnissbilldemo.rest.RegisterUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Created by 译丹 on 2017/5/13.
 */

public interface HappinessBillService {
    @PUT("register")
    Call<RegisterUser> register(@Body RegisterUser user);
}

