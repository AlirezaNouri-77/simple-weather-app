package com.example.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.weathertest.fragment.detail_fragment;
import com.example.weathertest.model.current_model;
import com.example.weathertest.model.minute_model;
import com.example.weathertest.model.forcast_model;
import com.example.weathertest.model.searchview_model;
import com.example.weathertest.recyckerview.ForcastRecyclerview;
import com.example.weathertest.recyckerview.Minute_forcastRecyclerview;
import com.example.weathertest.recyckerview.searchview_recyclerview;
import com.example.weathertest.util.local_json_city;
import com.example.weathertest.util.sharepreferenced_setting;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ForcastRecyclerview.forcastclicklistner, searchview_recyclerview.searchview_onclick {


    private List<forcast_model> sixtyday_forcastlist;
    private List<minute_model> minute_model_list;
    private List<current_model> current_list;
    private List<searchview_model> searchview_recyclerviewlist;

    private RecyclerView forcast_recyclerview, minute_recyclerview, searcheview_rv;

    private TextView cityname, currenttemp, condition, clouds, pressure;
    private ConstraintLayout constraintLayout;

    private sharepreferenced_setting sharepreferenced_setting;

    private searchview_recyclerview searchview_rv;

    private androidx.appcompat.widget.SearchView searchView;

    detail_fragment detail_fragment;
    FragmentManager fragmentManager;

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharepreferenced_setting = new sharepreferenced_setting(this);
        local_json_city local_json_city = new local_json_city(this);

        lottieAnimationView = findViewById(R.id.lottie_view);



        detail_fragment = new detail_fragment();

        sixtyday_forcastlist = new ArrayList<>();
        minute_model_list = new ArrayList<>();
        current_list = new ArrayList<>();
        searchview_recyclerviewlist = new ArrayList<>(local_json_city.get_searchview_list());

        constraintLayout = findViewById(R.id.linerlayout);
        searcheview_rv = findViewById(R.id.searchview_rv);

        fragmentManager = getSupportFragmentManager();

        searchview_rv = new searchview_recyclerview(searchview_recyclerviewlist , this);
        searcheview_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        searcheview_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searcheview_rv.setAdapter(searchview_rv);


        Current_Weather("");
        forcast_weather("");


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
                    searchView.clearFocus();
                    searchView.onActionViewCollapsed();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchview_rv.getFilter().filter(newText);
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searcheview_rv.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.INVISIBLE);
                } else {
                    searcheview_rv.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void Current_Weather(String name) {

        cityname = findViewById(R.id.cityname);
        currenttemp = findViewById(R.id.currenttemp);
        condition = findViewById(R.id.condition);
        pressure = findViewById(R.id.pressure);
        clouds = findViewById(R.id.clouds);

        ImageView imageView = findViewById(R.id.imageView);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(current_url_maker(name))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {

                if (response.isSuccessful()) {

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
                                    jsonobj.getString("pres")
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

                            Log.d("TAG", "run: " + current_list);
                            condition.setText(current_list.get(0).getDescription());
                            cityname.setText(current_list.get(0).getCityname());
                            clouds.setText("Cloud coverage " + current_list.get(0).getCloud() + "%");
                            pressure.setText("Air pressure " + current_list.get(0).getPressure());
                            currenttemp.setText(current_list.get(0).getTemp() + sharepreferenced_setting.getsymbol());
                            Picasso.get().load(current_list.get(0).getIcon_url()).fit().into(imageView);

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

    public void forcast_weather(String name) {

        constraintLayout.setVisibility(View.INVISIBLE);
       // lottieAnimationView.setAnimation("test.json");
        lottieAnimationView.playAnimation();

        minute_recyclerview = findViewById(R.id.minute_recyclerview);
        forcast_recyclerview = findViewById(R.id.forcast_recyclerview);
        forcast_recyclerview.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(forcast_url_maker(name))
                .build();

        Log.d("TAG", "forcast_weather: " + forcast_url_maker(name));

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
                                    j.getString("valid_date"),
                                    j.getString("max_temp"),
                                    j.getString("low_temp"),
                                    url)
                            );
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ForcastRecyclerview adapter = new ForcastRecyclerview(sixtyday_forcastlist, MainActivity.this, MainActivity.this);
                                forcast_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                forcast_recyclerview.setHasFixedSize(true);
                                forcast_recyclerview.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                                forcast_recyclerview.setAdapter(adapter);

                                constraintLayout.setVisibility(View.VISIBLE);
                                lottieAnimationView.pauseAnimation();

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
        bundle.putString("Temp", sixtyday_forcastlist.get(p).temp);
        bundle.putString("time", sixtyday_forcastlist.get(p).time);
        bundle.putString("min", sixtyday_forcastlist.get(p).min);
        bundle.putString("max", sixtyday_forcastlist.get(p).max);
        bundle.putString("iconurl", sixtyday_forcastlist.get(p).icon);
        detail_fragment.setArguments(bundle);

        fragmentManager.beginTransaction().show(detail_fragment).commit();

        searchView.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.GONE);

    }

    @Override
    public void searchview_onitemclick(int i) {

        sharepreferenced_setting.setdefualt(searchview_recyclerviewlist.get(i).getCity().trim());
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
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

    public String current_url_maker(String name) {
        if (name.length() != 0) {
            return "https://api.weatherbit.io/v2.0/current?city=" + name + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferenced_setting.temp_symbol() + "&include=minutely";
        }
        return "https://api.weatherbit.io/v2.0/current?city=" + sharepreferenced_setting.getdefault() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferenced_setting.temp_symbol() + "&include=minutely";
    }


    public String forcast_url_maker(String name) {
        if (name.length() != 0) {
            return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + name + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferenced_setting.temp_symbol();
        }
        return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + sharepreferenced_setting.getdefault() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferenced_setting.temp_symbol();
    }

    @Override
    public void onBackPressed() {
        constraintLayout.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction().remove(detail_fragment).commit();
    }
}