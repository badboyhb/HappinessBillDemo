package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;
import com.hb.happnissbilldemo.rest.FamilyMember;
import com.hb.happnissbilldemo.rest.UserFamily;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by HB on 2017/5/17.
 */

public class DeleteMemberTest {
    static private final String LOG_TAG = "TEST";

    static public void test() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        HappinessBillService service = retrofit.create(HappinessBillService.class);

        FamilyMember fm = new FamilyMember("hb1234", "hb5678", "hb1235");

        Call<UserFamily> c = service.deleteMember(fm);

        Log.v(LOG_TAG, "deleteMember user=" + fm.getUserName());

        c.enqueue(new Callback<UserFamily>() {
            @Override
            public void onResponse(Call<UserFamily> call, Response<UserFamily> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "deleteMember succeed.");
                } else {
                    Log.e(LOG_TAG, "deleteMember FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserFamily> call, Throwable t) {
                Log.e(LOG_TAG, "joinFamily FAILED!");
            }
        });
    }
}
