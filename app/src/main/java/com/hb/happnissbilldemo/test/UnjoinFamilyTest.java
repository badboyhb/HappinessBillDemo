package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;
import com.hb.happnissbilldemo.rest.UserFamily;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/16.
 */

public class UnjoinFamilyTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        HappinessBillService service = retrofit.create(HappinessBillService.class);

        UserFamily uf = new UserFamily("hb1235", "hb5678", "", "");

        Call<UserFamily> c = service.unjoinFamily(uf);

        Log.v(LOG_TAG, "unjoinFamily user=" + uf.getUserName());

        c.enqueue(new Callback<UserFamily>() {
            @Override
            public void onResponse(Call<UserFamily> call, Response<UserFamily> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "unjoinFamily succeed.");
                } else {
                    Log.e(LOG_TAG, "unjoinFamily FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserFamily> call, Throwable t) {
                Log.e(LOG_TAG, "unjoinFamily FAILED!");
            }
        });
    }
}
