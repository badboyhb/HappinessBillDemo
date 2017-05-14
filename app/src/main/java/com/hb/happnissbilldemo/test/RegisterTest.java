package com.hb.happnissbilldemo.test;

import android.util.Log;

import com.hb.happnissbilldemo.HappinessBillService;
import com.hb.happnissbilldemo.RetrofitFactory;
import com.hb.happnissbilldemo.rest.RegisterUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by 译丹 on 2017/5/13.
 */

public class RegisterTest {

    static private final String LOG_TAG = "TEST";

    static public void test() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        HappinessBillService service = retrofit.create(HappinessBillService.class);

        RegisterUser user = new RegisterUser("hb1234", "hb@yinhe.com", "123456789", "hb5678");

        Call<RegisterUser> c = service.register(user);

        Log.v(LOG_TAG, "register user=" + user.getName());

        c.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                if (response.code() == HTTP_OK) {
                    Log.v(LOG_TAG, "register user succeed.");
                } else {
                    Log.e(LOG_TAG, "register user FAILED!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                Log.e(LOG_TAG, "register user FAILED!");
            }
        });
    }
}
