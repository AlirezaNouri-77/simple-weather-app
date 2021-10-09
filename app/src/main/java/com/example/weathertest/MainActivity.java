package com.example.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weathertest.model.current_model;
import com.example.weathertest.model.minute_forcast;
import com.example.weathertest.model.recyclerview_item;
import com.example.weathertest.model.searchview_model;
import com.example.weathertest.recyckerview.ForcastRecyclerview;
import com.example.weathertest.recyckerview.Minute_forcastRecyclerview;
import com.example.weathertest.recyckerview.searchview_rv;
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

public class MainActivity extends AppCompatActivity implements ForcastRecyclerview.forcastclicklistner, searchview_rv.searchview_onclick {


    List<recyclerview_item> sixtyday_forcastlist;
    List<minute_forcast> minute_model_list;
    List<current_model> current_list;
    List<searchview_model> searchview_recyclerviewlist;

    RecyclerView forcast_recyclerview, minute_recyclerview, searcheview_rv;

    TextView cityname, currenttemp, condition;
    ConstraintLayout constraintLayout;

    local_json_city local_json_city;
    sharepreferenced_setting sharepreferenced_setting;

    searchview_rv searchview_rv;

    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharepreferenced_setting = new sharepreferenced_setting(this);
        local_json_city = new local_json_city(this);

        sixtyday_forcastlist = new ArrayList<>();
        minute_model_list = new ArrayList<>();
        current_list = new ArrayList<>();

        constraintLayout = findViewById(R.id.linerlayout);
        searcheview_rv = findViewById(R.id.searchview_rv);
        forcast_recyclerview = findViewById(R.id.forcast_recyclerview);

        Gson gson = new Gson();
        searchview_recyclerviewlist = gson.fromJson(local_json_city.loadJSONFromAsset(), new TypeToken<List<searchview_model>>() {}.getType());
        searchview_rv = new searchview_rv(searchview_recyclerviewlist, this);
        searcheview_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        searcheview_rv.setAdapter(searchview_rv);

        curent_weather();
        forcast_weather();

    }

    @Override
    protected void onStart() {
        sixtyday_forcastlist.clear();
        curent_weather();
        forcast_weather();
        super.onStart();
    }

    @Override
    protected void onResume() {
        sixtyday_forcastlist.clear();
        curent_weather();
        forcast_weather();
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
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


    public String current_url_maker() {
        return "https://api.weatherbit.io/v2.0/current?city=" + sharepreferenced_setting.getdefault() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferenced_setting.temp_symbol() + "&include=minutely";
    }


    public String forcast_url_maker() {
        return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + sharepreferenced_setting.getdefault() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferenced_setting.temp_symbol();
    }


    public void curent_weather() {

        cityname = findViewById(R.id.cityname);
        currenttemp = findViewById(R.id.currenttemp);
        condition = findViewById(R.id.condition);

        ImageView imageView = findViewById(R.id.imageView);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(current_url_maker())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {}

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    current_list.clear();
                    minute_model_list.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject s = jsonArray.getJSONObject(j);
                            current_list.clear();
                            current_list.add(new current_model(s.getJSONObject("weather").getString("description"),
                                    Math.round(Float.parseFloat(s.getString("temp"))) + "",
                                    s.getString("city_name").toUpperCase(),
                                    "https://www.weatherbit.io/static/img/icons/" + s.getJSONObject("weather").getString("icon") + ".png"));
                            break;
                        }
                        JSONArray jsonArray2 = jsonObject.getJSONArray("minutely");
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject j = jsonArray2.getJSONObject(i);
                            minute_model_list.add(new minute_forcast(j.getString("timestamp_local").substring(11, 16), j.getString("temp")));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String units;
                            condition.setText(current_list.get(0).getDescription());
                            cityname.setText(current_list.get(0).getCityname());
                            if (sharepreferenced_setting.temp_symbol().equals("M")) {
                                units = " \u2103";
                            } else {
                                units = "\u2109";
                            }
                            currenttemp.setText(current_list.get(0).getTemp() + units);
                            Picasso.get().load(current_list.get(0).getIcon_url()).into(imageView);
                            minute_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            minute_recyclerview.setHasFixedSize(true);
                            Minute_forcastRecyclerview minute_forcastRecyclerview = new Minute_forcastRecyclerview(minute_model_list);
                            minute_recyclerview.setAdapter(minute_forcastRecyclerview);


                        }
                    });
                }
            }
        });
    }

    public void forcast_weather() {

        minute_recyclerview = findViewById(R.id.minute_recyclerview);
        forcast_recyclerview = findViewById(R.id.forcast_recyclerview);

        forcast_recyclerview.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(forcast_url_maker())
                .build();

        okHttpClient1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject jsonObject;

                if (response.isSuccessful()) {
                    sixtyday_forcastlist.clear();
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            String url = "https://www.weatherbit.io/static/img/icons/" + j.getJSONObject("weather").getString("icon") + ".png";
                            sixtyday_forcastlist.add(new recyclerview_item(j.getString("temp"),
                                    j.getString("valid_date"),
                                    j.getString("max_temp"),
                                    j.getString("low_temp"),
                                    url)
                            );
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ForcastRecyclerview adapter = new ForcastRecyclerview( sixtyday_forcastlist , MainActivity.this, MainActivity.this);
                                forcast_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                forcast_recyclerview.setAdapter(adapter);

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

        Intent intent = new Intent(MainActivity.this, DetailActiviy.class);
        intent.putExtra("Temp", sixtyday_forcastlist.get(p).temp);
        intent.putExtra("time", sixtyday_forcastlist.get(p).time);
        intent.putExtra("min", sixtyday_forcastlist.get(p).min);
        intent.putExtra("max", sixtyday_forcastlist.get(p).max);
        intent.putExtra("iconurl", sixtyday_forcastlist.get(p).icon);
        startActivity(intent);

    }

    @Override
    public void searchview_onitemclick(int i) {
        sharepreferenced_setting.setdefualt(searchview_recyclerviewlist.get(i).getCity().trim());
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
        curent_weather();
        forcast_weather();

    }
}