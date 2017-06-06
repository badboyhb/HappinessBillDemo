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
 * Created by HB on 2017/5/15.
 */

public class CreateFamilyTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();

        Call<ResponseBody> c = service.createFamily("hb1234","hb5678");

        Log.v(LOG_TAG, "createFamily user=" + "hb1234");

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "createFamily succeed.");
                } else {
                    Log.e(LOG_TAG, "createFamily FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, "createFamily FAILED!");
            }
        });
    }
}
