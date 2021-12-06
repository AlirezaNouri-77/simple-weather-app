package com.example.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.weathertest.fragment.detail_fragment;
import com.example.weathertest.model.current_model;
import com.example.weathertest.model.minute_model;
import com.example.weathertest.model.forcast_model;
import com.example.weathertest.model.searchview_model;
import com.example.weathertest.recyckerview.forcast_recyclerview;
import com.example.weathertest.recyckerview.Minute_forcastRecyclerview;
import com.example.weathertest.recyckerview.searchview_recyclerview;
import com.example.weathertest.util.city_finder;
import com.example.weathertest.util.local_json_city;
import com.example.weathertest.util.sharepreferenced_setting;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements com.example.weathertest.recyckerview.forcast_recyclerview.forcastclicklistner, searchview_recyclerview.searchview_onclick {

    private List<forcast_model> sixtyday_forcastlist;
    private List<minute_model> minute_model_list;
    private List<current_model> current_list;

    private RecyclerView forcast_recyclerview, minute_recyclerview, searcheview_rv;

    private TextView cityname, currenttemp, condition, clouds, pressure, empty_city, country, error_textview;

    private ConstraintLayout main_consrtaintlayout, current_consrtaintlayout;

    private sharepreferenced_setting sharepreferenced_setting;

    private searchview_recyclerview searchview_rv;

    private androidx.appcompat.widget.SearchView searchView;

    private detail_fragment detail_fragment;
    private FragmentManager fragmentManager;

    private LottieAnimationView lottieAnimationView1, lottieAnimationView2, lottieAnimationView3, lottieAnimationView_noconnection;

    private LinearLayout minute_layout, forcast_layout;

    private local_json_city local_json_city;

    private com.example.weathertest.util.city_finder city_finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empty_city = findViewById(R.id.empty_recyclerview);

        sharepreferenced_setting = new sharepreferenced_setting(this);
        local_json_city = new local_json_city(this);

        lottieAnimationView1 = findViewById(R.id.lottie_one);
        lottieAnimationView2 = findViewById(R.id.lottie_two);
        lottieAnimationView3 = findViewById(R.id.lottie_three);
        lottieAnimationView_noconnection = findViewById(R.id.no_connection);

        current_consrtaintlayout = findViewById(R.id.constraint_one);
        minute_layout = findViewById(R.id.minute_layout);
        forcast_layout = findViewById(R.id.forcast_layout);
        error_textview = findViewById(R.id.errortextview);

        detail_fragment = new detail_fragment();
        city_finder = new city_finder();

        sixtyday_forcastlist = new ArrayList<>();
        minute_model_list = new ArrayList<>();
        current_list = new ArrayList<>();

        main_consrtaintlayout = findViewById(R.id.main_constraint);
        searcheview_rv = findViewById(R.id.searchview_rv);

        fragmentManager = getSupportFragmentManager();

        searchview_rv = new searchview_recyclerview(this);
        searcheview_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        searcheview_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searcheview_rv.setAdapter(searchview_rv);

        Current_Weather("");
        forcast_weather("");

        error_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Current_Weather("");
                forcast_weather("");
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
                    Current_Weather(query);
                    forcast_weather(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    city_finder.call_request(newText).observe(MainActivity.this, new Observer<List<searchview_model>>() {
                        @Override
                        public void onChanged(List<searchview_model> searchview_models) {
                            searchview_rv.submitList(searchview_models);
                        }
                    });
                }
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                test(menu, menuItem, false);
                searcheview_rv.setVisibility(View.VISIBLE);
                main_consrtaintlayout.setVisibility(View.INVISIBLE);
            } else {
                test(menu, menuItem, true);
                searcheview_rv.setVisibility(View.GONE);
                main_consrtaintlayout.setVisibility(View.VISIBLE);
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void test(Menu menu, MenuItem menuItem, boolean visibile) {
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
            Current_Weather("");
            forcast_weather("");
        }
        return super.onOptionsItemSelected(item);
    }

    public void Current_Weather(String name) {

        cityname = findViewById(R.id.cityname);
        currenttemp = findViewById(R.id.currenttemp);
        condition = findViewById(R.id.condition);
        pressure = findViewById(R.id.pressure);
        clouds = findViewById(R.id.clouds);
        country = findViewById(R.id.country);

        ImageView imageView = findViewById(R.id.imageView);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(local_json_city.current_url_maker(name))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                play_noconnection();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {

                    Log.d("TAG", "onResponse: " + "calling");

                    current_list.clear();
                    minute_model_list.clear();

                    if (name.length() != 0) {
                        sharepreferenced_setting.setdefualt(name);
                    }
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());
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
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (current_list.size() != 0) {
                                condition.setText(current_list.get(0).getDescription());
                                cityname.setText(current_list.get(0).getCityname());
                                clouds.setText("Cloud coverage " + current_list.get(0).getCloud() + "%");
                                pressure.setText("Average Pressure " + current_list.get(0).getPressure());
                                currenttemp.setText(current_list.get(0).getTemp() + sharepreferenced_setting.getsymbol());

                                Glide.with(MainActivity.this).load(current_list.get(0).getIcon_url()).into(imageView);
                                Locale locale = new Locale("", current_list.get(0).getCountry());
                                country.setText(locale.getDisplayCountry());

                                minute_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                minute_recyclerview.setHasFixedSize(true);
                                Minute_forcastRecyclerview minute_forcastRecyclerview = new Minute_forcastRecyclerview(minute_model_list, MainActivity.this);
                                minute_recyclerview.setAdapter(minute_forcastRecyclerview);

                            }
                        }
                    });
                }
            }
        });
    }

    public void forcast_weather(String name) {

        Log.d("TAG", "Current_Weather: " + "secend");

        start_lottie_animation();
        if (lottieAnimationView_noconnection.isAnimating()) {
            stop_noconnection();
        }

        minute_recyclerview = findViewById(R.id.minute_recyclerview);
        forcast_recyclerview = findViewById(R.id.forcast_recyclerview);

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(local_json_city.forcast_url_maker(name))
                .build();

        okHttpClient1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject jsonObject;

                if (response.isSuccessful()) {
                    if (name.length() != 0) {
                        sharepreferenced_setting.setdefualt(name);
                    }
                    sixtyday_forcastlist.clear();
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            String url = "https://www.weatherbit.io/static/img/icons/" + j.getJSONObject("weather").getString("icon") + ".png";
                            sixtyday_forcastlist.add(new forcast_model(j.getString("temp"),
                                    url,
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
                        }
                        Log.d("TAG", "onResponse: " + sixtyday_forcastlist.size());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                com.example.weathertest.recyckerview.forcast_recyclerview adapter = new forcast_recyclerview(sixtyday_forcastlist, MainActivity.this, MainActivity.this);
                                forcast_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                forcast_recyclerview.setHasFixedSize(true);
                                forcast_recyclerview.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                                forcast_recyclerview.setAdapter(adapter);

                                stop_lottie_animation();

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onclick(int p) {

        fragmentManager.beginTransaction().add(R.id.fragmentlayout, detail_fragment).addToBackStack("test").commit();
        Bundle bundle = new Bundle();
        bundle.putString("Temp", sixtyday_forcastlist.get(p).getTemp());
        bundle.putString("time", sixtyday_forcastlist.get(p).getTime());
        bundle.putString("min", sixtyday_forcastlist.get(p).getMin());
        bundle.putString("max", sixtyday_forcastlist.get(p).getMax());
        bundle.putString("iconurl", sixtyday_forcastlist.get(p).getIcon());
        bundle.putString("description", sixtyday_forcastlist.get(p).getDescription());
        bundle.putString("windspeed", sixtyday_forcastlist.get(p).getWindspeed());
        bundle.putString("uv", sixtyday_forcastlist.get(p).getUv());
        bundle.putString("clouds", sixtyday_forcastlist.get(p).getClouds());
        bundle.putString("visibility", sixtyday_forcastlist.get(p).getVisibility());
        bundle.putString("pressure", sixtyday_forcastlist.get(p).getPressure());
        bundle.putString("Probability", sixtyday_forcastlist.get(p).getRainpossibilty());
        bundle.putString("tomarrow_temp", sixtyday_forcastlist.get(p + 1).getTemp());
        bundle.putString("tomarrow_max_temp", sixtyday_forcastlist.get(p + 1).getMax());
        bundle.putString("tomarrow_min_temp", sixtyday_forcastlist.get(p + 1).getMin());

        detail_fragment.setArguments(bundle);
        searchView.setVisibility(View.GONE);
        main_consrtaintlayout.setVisibility(View.GONE);
        fragmentManager.beginTransaction().show(detail_fragment).commit();

    }

    @Override
    public void searchview_onitemclick(int i) {

        sharepreferenced_setting.setdefualt(searchview_rv.getCurrentList().get(i).getCity().trim());
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
        searcheview_rv.setVisibility(View.GONE);
        Current_Weather("");
        forcast_weather("");

    }

    @Override
    protected void onRestart() {
        sixtyday_forcastlist.clear();
        minute_model_list.clear();
        current_list.clear();
        Current_Weather("");
        forcast_weather("");
        super.onRestart();
    }


    @Override
    public void onBackPressed() {
        main_consrtaintlayout.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction().remove(detail_fragment).commit();
    }

    public void start_lottie_animation() {
        lottieAnimationView1.setVisibility(View.VISIBLE);
        lottieAnimationView2.setVisibility(View.VISIBLE);
        lottieAnimationView3.setVisibility(View.VISIBLE);
        current_consrtaintlayout.setVisibility(View.GONE);
        minute_layout.setVisibility(View.GONE);
        forcast_layout.setVisibility(View.GONE);
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
        lottieAnimationView1.pauseAnimation();
        lottieAnimationView2.pauseAnimation();
        lottieAnimationView3.pauseAnimation();
    }

    public void stop_noconnection() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                main_consrtaintlayout.setVisibility(View.VISIBLE);
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
}