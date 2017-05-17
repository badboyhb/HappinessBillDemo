package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;
import com.hb.happnissbilldemo.rest.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/15.
 */

public class CreateFamilyTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        HappinessBillService service = retrofit.create(HappinessBillService.class);

        UserInfo user = new UserInfo("hb1235", "", "", "hb5678");

        Call<UserInfo> c = service.createFamily(user);

        Log.v(LOG_TAG, "createFamily user=" + user.getName());

        c.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "createFamily succeed.");
                } else {
                    Log.e(LOG_TAG, "createFamily FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(LOG_TAG, "createFamily FAILED!");
            }
        });
    }
}
