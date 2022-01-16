package com.example.simple_weather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

public class Check_Connection {

    Context context;

    public Check_Connection(Context context) {
        this.context = context;
    }

    public Boolean is_connect() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }
}
