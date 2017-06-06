package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.rest.HappinessBillService;
import com.hb.happnissbilldemo.rest.RetrofitFactory;
import com.hb.happnissbilldemo.rest.FamilyInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/16.
 */

public class GetFamilyInfoTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();

        Call<FamilyInfo> c = service.getFamilyInfo("hb1236", "hb5678");

        Log.v(LOG_TAG, "getFamilyInfo user=" + "hb1234");

        c.enqueue(new Callback<FamilyInfo>() {
            @Override
            public void onResponse(Call<FamilyInfo> call, Response<FamilyInfo> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "getFamilyInfo succeed.");
                } else {
                    Log.e(LOG_TAG, "getFamilyInfo FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<FamilyInfo> call, Throwable t) {
                Log.e(LOG_TAG, "joinFamily FAILED!");
            }
        });
    }
}
