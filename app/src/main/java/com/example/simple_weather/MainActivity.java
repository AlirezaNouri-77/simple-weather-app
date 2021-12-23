package com.example.simple_weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.simple_weather.fragment.chart_fragment;
import com.example.simple_weather.fragment.detail_fragment;
import com.example.simple_weather.model.chart_model;
import com.example.simple_weather.model.current_model;
import com.example.simple_weather.model.minute_model;
import com.example.simple_weather.model.forcast_model;
import com.example.simple_weather.model.searchview_model;
import com.example.simple_weather.recyckerview.forcast_recyclerview;
import com.example.simple_weather.recyckerview.Minute_forcastRecyclerview;
import com.example.simple_weather.recyckerview.searchview_recyclerview;
import com.example.simple_weather.util.city_finder;
import com.example.simple_weather.util.url_maker;
import com.example.simple_weather.util.sharepreferenced;
import com.example.simple_weather.widget.weather_widget;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements com.example.simple_weather.recyckerview.forcast_recyclerview.forcastclicklistner, searchview_recyclerview.searchview_onclick {

    private List<forcast_model> sixteen_weatherforcast_list;
    private List<minute_model> minute_model_list;
    private List<current_model> current_list;
    private ArrayList<chart_model> temp_chart_list;
    private ArrayList<chart_model> twotemp_chart_list;

    private RecyclerView forecast_recyclerview, minute_recyclerview, searcheview_rv;

    private TextView cityname, currenttemp, condition, clouds, pressure, country, error_textview, chart_textview;

    private ConstraintLayout main_consrtaintlayout, current_consrtaintlayout;

    private sharepreferenced sharepreferenced;

    private searchview_recyclerview searchview_rv;

    private androidx.appcompat.widget.SearchView searchView;

    private detail_fragment detail_fragment;
    private chart_fragment chart_fragment;
    private Fragment fragment;

    private LottieAnimationView lottieAnimationView1, lottieAnimationView2, lottieAnimationView3, lottieAnimationView_noconnection;

    private LinearLayout minute_layout, forcast_layout;

    private url_maker local_json_city;

    private com.example.simple_weather.util.city_finder city_finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = null;

        chart_textview = findViewById(R.id.chart_textview);

        sharepreferenced = new sharepreferenced(this);
        local_json_city = new url_maker(this);

        lottieAnimationView1 = findViewById(R.id.lottie_one);
        lottieAnimationView2 = findViewById(R.id.lottie_two);
        lottieAnimationView3 = findViewById(R.id.lottie_three);
        lottieAnimationView_noconnection = findViewById(R.id.no_connection);

        current_consrtaintlayout = findViewById(R.id.constraint_one);
        minute_layout = findViewById(R.id.minute_layout);
        forcast_layout = findViewById(R.id.forcast_layout);
        error_textview = findViewById(R.id.errortextview);

        detail_fragment = new detail_fragment();
        chart_fragment = new chart_fragment();

        city_finder = new city_finder();

        sixteen_weatherforcast_list = new ArrayList<>();
        minute_model_list = new ArrayList<>();
        current_list = new ArrayList<>();
        temp_chart_list = new ArrayList<>();
        twotemp_chart_list = new ArrayList<>();

        main_consrtaintlayout = findViewById(R.id.main_constraint);
        searcheview_rv = findViewById(R.id.searchview_rv);

        searchview_rv = new searchview_recyclerview(this);
        searcheview_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        searcheview_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searcheview_rv.setAdapter(searchview_rv);

        Current_Weather("", "");
        forcast_weather("", "");

        error_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Current_Weather("", "");
                forcast_weather("", "");
            }
        });

        chart_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("temp_data_chart", temp_chart_list);
                bundle.putParcelableArrayList("two_data_chart", twotemp_chart_list);
                chart_fragment.setArguments(bundle);
                fragment = chart_fragment;
                searchView.setVisibility(View.GONE);
                main_consrtaintlayout.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout, fragment).commit();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() != 0) {
                    Current_Weather(query, "");
                    forcast_weather(query, "");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Handler mh = new Handler();
                mh.removeCallbacksAndMessages(null);

                mh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        city_finder.call_request(newText).observe(MainActivity.this, new Observer<List<searchview_model>>() {
                            @Override
                            public void onChanged(List<searchview_model> searchview_models) {
                                searchview_rv.submitList(searchview_models);
                            }
                        });
                    }
                }, 500);
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hide_actionbar_items(menu, menuItem, false);
                searcheview_rv.setVisibility(View.VISIBLE);
                main_consrtaintlayout.setVisibility(View.INVISIBLE);
            } else {
                hide_actionbar_items(menu, menuItem, true);
                searcheview_rv.setVisibility(View.GONE);
                main_consrtaintlayout.setVisibility(View.VISIBLE);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void hide_actionbar_items(Menu menu, MenuItem menuItem, boolean visibile) {
        getSupportActionBar().setDisplayShowTitleEnabled(visibile);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item != menuItem) item.setVisible(visibile);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.refresh) {
            Current_Weather("", "");
            forcast_weather("", "");
        }
        return super.onOptionsItemSelected(item);
    }

    public void Current_Weather(String city_name, String counrtry_name) {

        cityname = findViewById(R.id.cityname);
        currenttemp = findViewById(R.id.currenttemp);
        condition = findViewById(R.id.condition);
        pressure = findViewById(R.id.pressure);
        clouds = findViewById(R.id.clouds);
        country = findViewById(R.id.country);
        ImageView imageView = findViewById(R.id.imageView);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(local_json_city.current_url_maker(city_name, counrtry_name))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                play_noconnection();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                if (response.isSuccessful()) {

                    String res = response.body().string();
                    current_list.clear();
                    minute_model_list.clear();

                    try {

                        JSONObject jsonObject = new JSONObject(res);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobj = jsonArray.getJSONObject(i);
                            current_list.add(new current_model(jsonobj.getJSONObject("weather").getString("description"),
                                    String.valueOf(Math.round(Float.parseFloat(jsonobj.getString("temp")))),
                                    jsonobj.getString("city_name").toUpperCase(),
                                    "https://www.weatherbit.io/static/img/icons/" + jsonobj.getJSONObject("weather").getString("icon") + ".png",
                                    jsonobj.getString("clouds"),
                                    jsonobj.getString("pres"),
                                    jsonobj.getString("country_code")
                            ));
                        }

                        JSONArray jsonArray2 = jsonObject.getJSONArray("minutely");
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject j = jsonArray2.getJSONObject(i);
                            minute_model_list.add(new minute_model(j.getString("timestamp_local").substring(11, 16), j.getString("temp")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> {

                        if (current_list.size() != 0) {

                            condition.setText(current_list.get(0).getDescription());
                            cityname.setText(current_list.get(0).getCityname());
                            clouds.setText("Cloud coverage " + current_list.get(0).getCloud() + "%");
                            pressure.setText("Average Pressure " + current_list.get(0).getPressure());
                            currenttemp.setText(current_list.get(0).getTemp() + sharepreferenced.getsymbol());

                            Glide.with(MainActivity.this).load(current_list.get(0).getIcon_url()).into(imageView);
                            Locale locale = new Locale("", current_list.get(0).getCountry());
                            country.setText(locale.getDisplayCountry(Locale.US));

                            minute_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            minute_recyclerview.setHasFixedSize(true);
                            Minute_forcastRecyclerview minute_forcastRecyclerview = new Minute_forcastRecyclerview(minute_model_list, MainActivity.this);
                            minute_recyclerview.setAdapter(minute_forcastRecyclerview);

                        }
                    });
                }
            }
        });
    }

    public void forcast_weather(String city_name, String country_name) {

        start_lottie_animation();
        if (lottieAnimationView_noconnection.isAnimating()) {
            stop_noconnection();
        }

        minute_recyclerview = findViewById(R.id.minute_recyclerview);
        forecast_recyclerview = findViewById(R.id.forcast_recyclerview);

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Request request1 = new Request.Builder().url(local_json_city.forcast_url_maker(city_name, country_name)).build();

        okHttpClient1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject jsonObject;

                if (response.isSuccessful()) {
                    String res = response.body().string();

                    temp_chart_list.clear();
                    twotemp_chart_list.clear();
                    sixteen_weatherforcast_list.clear();

                    try {
                        jsonObject = new JSONObject(res);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            String icon_url = "https://www.weatherbit.io/static/img/icons/" + j.getJSONObject("weather").getString("icon") + ".png";
                            sixteen_weatherforcast_list.add(new forcast_model(j.getString("temp"),
                                    icon_url,
                                    j.getString("low_temp"),
                                    j.getString("max_temp"),
                                    j.getString("datetime"),
                                    j.getJSONObject("weather").getString("description"),
                                    j.getString("wind_spd"),
                                    j.getString("pres"),
                                    j.getString("uv"),
                                    j.getString("vis"),
                                    j.getString("clouds"),
                                    j.getString("pop")));

                            temp_chart_list.add(new chart_model(j.getString("temp"), j.getString("datetime")));
                            twotemp_chart_list.add(new chart_model(j.getString("low_temp"), j.getString("max_temp")));

                        }

                        runOnUiThread(() -> {

                            forcast_recyclerview adapter = new forcast_recyclerview(sixteen_weatherforcast_list, MainActivity.this, MainActivity.this);
                            forecast_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            forecast_recyclerview.setHasFixedSize(true);
                            forecast_recyclerview.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                            forecast_recyclerview.setAdapter(adapter);

                            stop_lottie_animation();
                            widget_update_broadcast();

                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void sixteenday_forcast_onclick(int p) {

        fragment = detail_fragment;

        Bundle bundle = new Bundle();
        bundle.putString("Temp", sixteen_weatherforcast_list.get(p).getTemp());
        bundle.putString("time", sixteen_weatherforcast_list.get(p).getTime());
        bundle.putString("min", sixteen_weatherforcast_list.get(p).getMin());
        bundle.putString("max", sixteen_weatherforcast_list.get(p).getMax());
        bundle.putString("iconurl", sixteen_weatherforcast_list.get(p).getIcon());
        bundle.putString("description", sixteen_weatherforcast_list.get(p).getDescription());
        bundle.putString("windspeed", sixteen_weatherforcast_list.get(p).getWindspeed());
        bundle.putString("uv", sixteen_weatherforcast_list.get(p).getUv());
        bundle.putString("clouds", sixteen_weatherforcast_list.get(p).getClouds());
        bundle.putString("visibility", sixteen_weatherforcast_list.get(p).getVisibility());
        bundle.putString("pressure", sixteen_weatherforcast_list.get(p).getPressure());
        bundle.putString("Probability", sixteen_weatherforcast_list.get(p).getRainpossibilty());
        bundle.putString("tomarrow_temp", sixteen_weatherforcast_list.get(p + 1).getTemp());
        bundle.putString("tomarrow_max_temp", sixteen_weatherforcast_list.get(p + 1).getMax());
        bundle.putString("tomarrow_min_temp", sixteen_weatherforcast_list.get(p + 1).getMin());

        detail_fragment.setArguments(bundle);
        searchView.setVisibility(View.GONE);
        main_consrtaintlayout.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout, fragment).commit();

    }

    @Override
    public void searchview_onitemclick(int i) {

        sharepreferenced.setdefualt(searchview_rv.getCurrentList().get(i).getCity(), searchview_rv.getCurrentList().get(i).getCountry());
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
        searcheview_rv.setVisibility(View.GONE);
        Current_Weather(searchview_rv.getCurrentList().get(i).getCity().trim(), searchview_rv.getCurrentList().get(i).getCountry().trim());
        forcast_weather(searchview_rv.getCurrentList().get(i).getCity().trim(), searchview_rv.getCurrentList().get(i).getCountry().trim());

    }

    @Override
    protected void onRestart() {
        sixteen_weatherforcast_list.clear();
        minute_model_list.clear();
        current_list.clear();
        Current_Weather("", "");
        forcast_weather("", "");
        super.onRestart();
    }


    @Override
    public void onBackPressed() {
        main_consrtaintlayout.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        fragment = null;
        Log.d("TAG2", "onCreate: " + fragment);
    }

    public void start_lottie_animation() {
        lottieAnimationView1.setVisibility(View.VISIBLE);
        lottieAnimationView2.setVisibility(View.VISIBLE);
        lottieAnimationView3.setVisibility(View.VISIBLE);
        current_consrtaintlayout.animate().alpha(0.0f);
        minute_layout.animate().alpha(0.0f);
        forcast_layout.animate().alpha(0.0f);

//        current_consrtaintlayout.setVisibility(View.GONE);
//        minute_layout.setVisibility(View.GONE);
//        forcast_layout.setVisibility(View.GONE);
        lottieAnimationView1.playAnimation();
        lottieAnimationView2.playAnimation();
        lottieAnimationView3.playAnimation();
    }

    public void stop_lottie_animation() {
        lottieAnimationView1.setVisibility(View.GONE);
        lottieAnimationView2.setVisibility(View.GONE);
        lottieAnimationView3.setVisibility(View.GONE);

        current_consrtaintlayout.setVisibility(View.VISIBLE);
        minute_layout.setVisibility(View.VISIBLE);
        forcast_layout.setVisibility(View.VISIBLE);
        current_consrtaintlayout.animate().alpha(1.0f);
        minute_layout.animate().alpha(1.0f);
        forcast_layout.animate().alpha(1.0f);

        lottieAnimationView1.pauseAnimation();
        lottieAnimationView2.pauseAnimation();
        lottieAnimationView3.pauseAnimation();
    }

    public void stop_noconnection() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                main_consrtaintlayout.animate().alpha(1.0f);
                // main_consrtaintlayout.setVisibility(View.VISIBLE);
                lottieAnimationView_noconnection.setAnimation("noconnection.json");
                lottieAnimationView_noconnection.pauseAnimation();
                lottieAnimationView_noconnection.setVisibility(View.GONE);
                error_textview.setVisibility(View.GONE);
            }
        });
    }


    public void play_noconnection() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                main_consrtaintlayout.setVisibility(View.GONE);
                lottieAnimationView_noconnection.setAnimation("noconnection.json");
                lottieAnimationView_noconnection.playAnimation();
                lottieAnimationView_noconnection.setVisibility(View.VISIBLE);
                error_textview.setVisibility(View.VISIBLE);
            }
        });

    }

    public void widget_update_broadcast() {

        Intent intent = new Intent(MainActivity.this, weather_widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] id = AppWidgetManager.getInstance(MainActivity.this).getAppWidgetIds(new ComponentName(getApplication(), weather_widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, id);
        if (id.length != 0) {
            sendBroadcast(intent);
        }
    }
}