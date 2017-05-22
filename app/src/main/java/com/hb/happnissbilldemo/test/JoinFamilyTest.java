package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/16.
 */

public class JoinFamilyTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();

        Call<ResponseBody> c = service.joinFamily("hb1234", "hb1235", "hb5678", "10518385");

        Log.v(LOG_TAG, "joinFamily user=" + "hb1235");

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "joinFamily succeed.");
                } else {
                    Log.e(LOG_TAG, "joinFamily FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, "joinFamily FAILED!");
            }
        });
    }
}
