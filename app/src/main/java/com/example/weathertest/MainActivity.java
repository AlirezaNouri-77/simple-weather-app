package com.example.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weathertest.model.recyclerview_item;
import com.example.weathertest.recyckerview.RecyclerviewAdapter;
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

public class MainActivity extends AppCompatActivity implements RecyclerviewAdapter.Onitemclick {


    List<recyclerview_item> list;
    RecyclerView recyclerView;
    CardView cardView;
    TextView cityname, currenttemp, mintemp, higttemp, condition, fail;
    String iconcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = new ArrayList<>();

        curent_weather();
        forcast_weather();

    }

    @Override
    protected void onStart() {
        list.clear();
        curent_weather();
        forcast_weather();
        super.onStart();


    }

    @Override
    protected void onResume() {
        list.clear();
        curent_weather();
        forcast_weather();
        super.onResume();

    }

    public String getlocation() {
        //get city name in setting page (user enter this city )
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPreferences.getString("location", "");

//        if (location.isEmpty()) {
//
//        }

        return location;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
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

    @Override
    public void onclick(int p) {

        Intent intent = new Intent(MainActivity.this, DetailActiviy.class);


        intent.putExtra("Temp", list.get(p).temp);
        intent.putExtra("time", list.get(p).time);
        intent.putExtra("min", list.get(p).min);
        intent.putExtra("max", list.get(p).max);
        intent.putExtra("iconurl", list.get(p).icon);

        startActivity(intent);

    }

    public String urlmaker() {
        return "https://api.weatherbit.io/v2.0/current?city=" + getlocation() + "&key=8a8ec0d5af5f4806ada672017c6d44b5";
    }

    public String urlmaker2() {
        return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + getlocation() + "&key=8a8ec0d5af5f4806ada672017c6d44b5";
    }


    public void curent_weather() {

        cityname = findViewById(R.id.cityname);
        currenttemp = findViewById(R.id.currenttemp);
        mintemp = findViewById(R.id.mintemp);
        higttemp = findViewById(R.id.hightemp);
        condition = findViewById(R.id.condition);

        ImageView imageView = findViewById(R.id.imageView);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlmaker())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject s = jsonArray.getJSONObject(j);
                                    iconcode = s.getJSONObject("weather").getString("icon");
                                    condition.setText(String.valueOf(s.getJSONObject("weather").getString("description")));
                                    cityname.setText(s.getString("city_name").toUpperCase());
                                    currenttemp.setText(Math.round(Float.parseFloat(s.getString("temp"))) + " \u2103");
                                    Picasso.get().load("https://www.weatherbit.io/static/img/icons/" + s.getJSONObject("weather").getString("icon") + ".png").into(imageView);
                                    break;
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                call.cancel();
            }

        });
    }

    public void forcast_weather() {

        cardView = findViewById(R.id.cardView2);
        recyclerView = findViewById(R.id.RV);
        fail = findViewById(R.id.fail);
        fail.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(urlmaker2())
                .build();

        okHttpClient1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                JSONObject jsonObject;

                if (response.isSuccessful()) {

                    try {

                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            String url = "https://www.weatherbit.io/static/img/icons/"+ j.getJSONObject("weather").getString("icon") + ".png";
                            list.add(new recyclerview_item(j.getString("temp"),
                                    j.getString("valid_date"),
                                    j.getString("max_temp"),
                                    j.getString("low_temp"),
                                    url)
                            );
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerView = findViewById(R.id.RV);
                                RecyclerviewAdapter adapter = new RecyclerviewAdapter(list, MainActivity.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                recyclerView.setAdapter(adapter);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            fail = findViewById(R.id.fail);
                            fail.setVisibility(View.VISIBLE);
                            fail.setText("Your entred City " + getlocation() + " not found please enter other city");
                            recyclerView.setVisibility(View.GONE);
                            cardView.setVisibility(View.GONE);

                        }
                    });


                }

                call.cancel();
            }

        });

    }
}