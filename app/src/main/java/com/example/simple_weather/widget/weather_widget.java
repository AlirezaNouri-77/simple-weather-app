package com.example.simple_weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.simple_weather.R;
import com.example.simple_weather.util.Url_Maker;
import com.example.simple_weather.util.My_Sharepreferenced;
import com.example.simple_weather.util.my_alarmmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class weather_widget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, weather_widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = new int[]{appWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_MUTABLE);
        views.setOnClickPendingIntent(R.id.refresh_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        req req = new req(context);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            Log.d("TAG", "onReceive: " + "click");
            int[] id = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            req.request(id[0]);

        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            my_alarmmanager my_alarmmanager = new my_alarmmanager(context);
            My_Sharepreferenced my_sharepreferenced = new My_Sharepreferenced(context);
            if (!my_sharepreferenced.notification_setting()) {
                my_alarmmanager.Setup_Alarmanager();
            }
            Log.d("TAG", "onReceive: " + "boot");
            int[] id = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            req.request(id[0]);
            // new request(context, id[0]).execute();
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static class req {

        Context context;

        public req(Context context) {
            this.context = context;
        }

        public void request(int id) {
            Url_Maker local_json_city = new Url_Maker(context);
            My_Sharepreferenced mysharepreferenced = new My_Sharepreferenced(context);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executorService.execute(() -> {
                Request request = new Request.Builder()
                        .url(local_json_city.current_url_maker("", "")).build();

                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
                        handler.post(() -> {
                            views.setTextViewText(R.id.last_check_widget, "No Internet Connection");
                            appWidgetManager.updateAppWidget(id, views);
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
                        JSONObject jsonObject1 = new JSONObject();

                        if (response.isSuccessful()) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    jsonObject1 = jsonArray1.getJSONObject(i);
                                }

                                String imageurl = "https://www.weatherbit.io/static/img/icons/" + jsonObject1.getJSONObject("weather").getString("icon") + ".png";
                                Bitmap bitmap = Glide.with(context).asBitmap().fitCenter().load(imageurl).submit().get();
                                Date date = new Date();

                                JSONObject finalJsonObject = jsonObject1;
                                handler.post(() -> {
                                    try {

                                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                        Locale locale = new Locale("", finalJsonObject.getString("country_code"));
                                        views.setTextViewText(R.id.city_widget, finalJsonObject.getString("city_name"));
                                        views.setTextViewText(R.id.country_widget, locale.getDisplayCountry());
                                        views.setTextViewText(R.id.current_temp, finalJsonObject.getString("temp") + " " + mysharepreferenced.getsymbol());
                                        views.setTextViewText(R.id.last_check_widget, dateFormat.format(date));
                                        views.setTextViewText(R.id.condition_widgeta, finalJsonObject.getJSONObject("weather").getString("description"));
                                        views.setImageViewBitmap(R.id.imageview_widget, bitmap);
                                        appWidgetManager.updateAppWidget(id, views);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                });

                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            });
        }

    }

}