package com.example.simple_weather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

public class check_connection {
    Context context;
    public check_connection(Context context) {
        this.context = context;
    }

    public Boolean is_connect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
