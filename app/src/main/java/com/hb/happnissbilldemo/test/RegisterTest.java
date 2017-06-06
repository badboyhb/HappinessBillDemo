package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.rest.HappinessBillService;
import com.hb.happnissbilldemo.rest.RetrofitFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by 译丹 on 2017/5/13.
 */

public class RegisterTest {

    static private final String LOG_TAG = "TEST";

    static public void test() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();

        Call<ResponseBody> c = service.register("hb1236", "hb5678", "hb@yinhe.com", "123456789");

        Log.v(LOG_TAG, "register user=" + "hb1236");

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "register user succeed.");
                } else {
                    Log.e(LOG_TAG, "register user FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, "register user FAILED!");
            }
        });
    }
}
