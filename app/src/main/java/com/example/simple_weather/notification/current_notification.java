package com.example.simple_weather.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.simple_weather.R;
import com.example.simple_weather.util.Check_Connection;
import com.example.simple_weather.util.My_Sharepreferenced;
import com.example.simple_weather.util.Url_Maker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class current_notification {

    Context context;
    Url_Maker url_maker;
    My_Sharepreferenced my_sharepreferenced;
    Check_Connection check_connection;


    public current_notification(Context context) {
        this.context = context;
        check_connection = new Check_Connection(context);
        my_sharepreferenced = new My_Sharepreferenced(context);
        url_maker = new Url_Maker(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("weather", "Weather Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void request_notification() {
        new Request().execute();
        if (check_connection.is_connect()) {
        }
    }

    private class Request extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            String url = url_maker.current_url_maker("", "");

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url).build();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.isSuccessful()) {
                        JSONObject jsonObject1 = new JSONObject();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                jsonObject1 = jsonArray1.getJSONObject(i);
                            }

                            String temp = jsonObject1.getString("temp");
                            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context, "weather")
                                    .setSmallIcon(R.drawable.notification_small_icon)
                                    .setContentTitle("Current Temp is " + Math.round(Double.parseDouble(temp)) + " " + my_sharepreferenced.getsymbol())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                            notificationManager.notify(1, notificationCompat.build());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return null;
        }
    }
}
