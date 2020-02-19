package com.example.myapplication.NetworkLayer;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Webservice {
    private static final String MAIN_URL = "http://imaging-atelier.cat-sw.com/api/";
    private static Webservice instance;
    private ApiServices api;

    public Webservice() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(160, TimeUnit.SECONDS);
        httpClient.readTimeout(160, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(MAIN_URL)
                .build();

        api = retrofit.create(ApiServices.class);
        Log.i("hhhh", "" + api.toString());

    }

    public static Webservice getInstance() {
        if (instance == null) {
            instance = new Webservice();
        }
        return instance;
    }

    public ApiServices getApi() {
        Log.i("hhhh", "" + api.toString());
        return api;
    }


}
