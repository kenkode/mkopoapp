package com.example.lixnet.mkopo.core;

import android.app.Application;

import com.example.lixnet.mkopo.helpers.Internet;

public class ApplicationConfiguration extends Application {

    private static ApplicationConfiguration mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized ApplicationConfiguration getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(Internet.ConnectivityReceiverListener listener) {
        Internet.connectivityReceiverListener = listener;
    }
}