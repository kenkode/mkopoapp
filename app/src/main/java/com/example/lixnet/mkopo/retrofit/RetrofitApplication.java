package com.example.lixnet.mkopo.retrofit;

/**
 * Created by Lixnet on 2017-08-29.
 */

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.lixnet.mkopo.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by Umesh Patel on 08-03-2016.
 */
public class RetrofitApplication extends Application {
    private static Context sStaticContext;
    private LoanAPI taskServiceAPI;
    //private static final String BASE_URL = "http://45.55.201.219/mkopo/public/";
    //private static final String BASE_URL = "http://10.0.2.2/mkopo/public";
    //public static final String BASE_URL = "http://192.168.56.1/mkopo/public";
    /**
     * Gets a reference to the application context
     */
    private static final String BASE_URL = "http://loans.prioritymobile.co.ke/public";

    public static Context getStaticContext() {
        if (sStaticContext != null) {
            return sStaticContext;
        }
        //Should NEVER hapen
        throw new RuntimeException("No static context instance");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sStaticContext = getApplicationContext();
        initLoginApi();
    }

    private void initLoginApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.MINUTES);
        client.setReadTimeout(5, TimeUnit.MINUTES);


        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        taskServiceAPI=retrofit.create(LoanAPI.class);
        Log.i("Application ","call");
    }

    public LoanAPI getMshoppinpApis() {
        return taskServiceAPI;
    }


}
