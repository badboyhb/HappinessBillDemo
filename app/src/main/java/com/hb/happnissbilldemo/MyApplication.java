package com.hb.happnissbilldemo;

import android.app.Application;

import com.hb.happnissbilldemo.rest.RetrofitFactory;

/**
 * Created by HB on 2017/5/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitFactory.init(this);
    }
}
