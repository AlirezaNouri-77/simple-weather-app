package com.example.simple_weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.simple_weather.R;
import com.example.simple_weather.util.url_maker;
import com.example.simple_weather.util.sharepreferenced;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Implementation of App Widget functionality.
 */
public class weather_widget extends AppWidgetProvider {

    private static url_maker local_json_city;
    private static sharepreferenced sharepreferenced;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        local_json_city = new url_maker(context);
        sharepreferenced = new sharepreferenced(context);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, weather_widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = new int[]{appWidgetId};
        intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            Log.d("TAG", "onReceive: " + "click");
            int[] id = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            new request(context, id[0]).execute();
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

    private static class request extends AsyncTask<Void, Void, Void> {

        Context mcontext;
        int mid;

        public request(Context context, int i) {
            this.mcontext = context;
            this.mid = i;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            get_weather();
            return null;
        }

        private void get_weather() {

            Request request = new Request.Builder()
                    .url(local_json_city.current_url_maker("", "")).build();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    RemoteViews views = new RemoteViews(mcontext.getPackageName(), R.layout.widget_layout);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mcontext.getApplicationContext());

                    Log.d("TAG", "onResponse: " + "call");

                    if (response.isSuccessful()) {
                        JSONObject jsonObject1 = new JSONObject();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                jsonObject1 = jsonArray1.getJSONObject(i);
                            }

                            Date date = new Date();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

                            Locale locale = new Locale("", jsonObject1.getString("country_code"));

                            String imageurl = "https://www.weatherbit.io/static/img/icons/" + jsonObject1.getJSONObject("weather").getString("icon") + ".png";
                            views.setTextViewText(R.id.city_widget, jsonObject1.getString("city_name"));
                            views.setTextViewText(R.id.country_widget, locale.getDisplayCountry());
                            views.setTextViewText(R.id.current_temp, jsonObject1.getString("temp") + sharepreferenced.getsymbol());
                            views.setTextViewText(R.id.last_check_widget, dateFormat.format(date));
                            views.setTextViewText(R.id.condition_widgeta, jsonObject1.getJSONObject("weather").getString("description"));

                            Bitmap bitmap = Glide.with(mcontext).asBitmap().fitCenter().load(imageurl).submit().get();
                            views.setImageViewBitmap(R.id.imageview_widget, bitmap);

                            appWidgetManager.updateAppWidget(mid, views);

                        } catch (JSONException | ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


    }


}