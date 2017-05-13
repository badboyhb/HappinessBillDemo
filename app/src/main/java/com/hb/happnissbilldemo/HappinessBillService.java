package com.hb.happnissbilldemo;

import com.hb.happnissbilldemo.model.Member;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 译丹 on 2017/5/13.
 */

public interface HappinessBillService {
    @GET("members/{id}")
    Call<Member> lookupMemberById(@Path("id") long id);
}

