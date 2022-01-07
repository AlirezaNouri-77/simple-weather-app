package com.example.simple_weather.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.simple_weather.notification.current_notification;

public class notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.shermanrex_weather_notification")) {
            current_notification current_notification = new current_notification(context);
            current_notification.request_notification();
        }
    }
}
