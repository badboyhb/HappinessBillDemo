package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;
import com.hb.happnissbilldemo.rest.Record;
import com.hb.happnissbilldemo.rest.RestRecord;
import com.hb.happnissbilldemo.rest.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/15.
 */

public class AddRecordTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        HappinessBillService service = retrofit.create(HappinessBillService.class);

        RestRecord record = new RestRecord("hb1234", "zxcvbnm", 100.5F, "food", "buy an apple");

        Call<Record> c = service.addRecord(record);

        Log.v(LOG_TAG, "addRecord user=" + record.getUserName());

        c.enqueue(new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "addRecord user succeed.");
                } else {
                    Log.e(LOG_TAG, "addRecord user FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {
                Log.e(LOG_TAG, "addRecord user FAILED!");
            }
        });
    }
}
