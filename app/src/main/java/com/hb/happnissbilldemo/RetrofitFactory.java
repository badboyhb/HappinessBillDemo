package com.hb.happnissbilldemo;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import retrofit2.Retrofit;

/**
 * Created by 译丹 on 2017/5/13.
 */

public class RetrofitFactory {

    private static final RetrofitFactory mRetrofitFactory=new RetrofitFactory();

    private Retrofit mRetrofit;


    static RetrofitFactory getInstance(){
        return mRetrofitFactory;
    }
    private RetrofitFactory(){
        mRetrofit=null;
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }

    private static SSLSocketFactory getSSLSocketFactory() {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);

            InputStream is = getResources().openRawResource(R.raw.wildfly);
            try {
                ks.setCertificateEntry("wildfly", cf.generateCertificate(is));
            } finally {
                is.close();
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ks);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
