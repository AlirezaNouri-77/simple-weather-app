package com.example.simple_weather.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.simple_weather.broadcast_receiver.notification_receiver;

public class my_alarmmanager {

    AlarmManager alarmManager;
    Context context;

    public my_alarmmanager(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
    }

    public void Setup_Alarmanager() {
        Intent intent = new Intent(context, notification_receiver.class);
        intent.setAction("com.shermanrex_weather_notification");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 10000, pendingIntent);
    }

    public Boolean alarm_manager_isalarm() {
        Intent intent = new Intent(context, notification_receiver.class);
        intent.setAction("com.shermanrex_weather_notification");
        Boolean isalarm = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE) != null;
        return isalarm;
    }

    public void cancel_alarmmanager() {
        Intent intent = new Intent(context, notification_receiver.class);
        intent.setAction("com.shermanrex_weather_notification");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        alarmManager.cancel(pendingIntent);
    }
}
