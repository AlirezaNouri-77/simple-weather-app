package com.example.simple_weather.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.simple_weather.notification.current_notification;

public class notification_receiver extends BroadcastReceiver {

//    this receiver for when alarmmanager repeat is happened when this methode call app will request to api and get current
//    weather and send notification

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.shermanrex_weather_notification")) {
            Log.d("TAG", "onReceive: " + "alarm fire");
            current_notification current_notification = new current_notification(context);
            current_notification.request_notification();
        }
    }
}
