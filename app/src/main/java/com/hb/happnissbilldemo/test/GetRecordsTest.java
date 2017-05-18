package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;
import com.hb.happnissbilldemo.rest.Record;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/18.
 */

public class GetRecordsTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        HappinessBillService service = retrofit.create(HappinessBillService.class);

        Call<List<Record>> c = service.getRecords("hb1234", "hb5678"
                , null, null, new Timestamp(0), new Timestamp(new Date().getTime())
                , 1, 100);

        Log.v(LOG_TAG, "getRecords user=" + "hb1234");

        c.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "getRecords succeed.");
                } else {
                    Log.e(LOG_TAG, "getRecords FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                Log.e(LOG_TAG, "getRecords FAILED!");
            }
        });
    }
}
