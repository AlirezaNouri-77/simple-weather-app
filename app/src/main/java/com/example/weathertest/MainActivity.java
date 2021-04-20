package com.example.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathertest.model.recyclerview_item;
import com.example.weathertest.recyckerview.RecyclerviewAdapter;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements RecyclerviewAdapter.Onitemclick {

//    String url = "https://api.openweathermap.org/data/2.5/weather?q=tehran&units=metric&appid=e5085a29070720b8ee6224e489e9a63a";
//    String url2 = "https://api.openweathermap.org/data/2.5/forecast?q=tehran&units=metric&cnt=10&appid=e5085a29070720b8ee6224e489e9a63a";


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

        forcast();
        currentweather();

    }

    @Override
    protected void onStart() {
        list.clear();
        forcast();
        currentweather();
        super.onStart();


    }

    @Override
    protected void onResume() {
         list.clear();
        forcast();
        currentweather();
        super.onResume();

    }

    public String getlocation() {
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
        intent.putExtra("pressure", list.get(p).pressure);
        intent.putExtra("humidity", list.get(p).humidity);
        intent.putExtra("iconurl", list.get(p).icon);

        startActivity(intent);

    }

    public String urlmaker() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + getlocation() + "&units=metric&appid=e5085a29070720b8ee6224e489e9a63a";
        return url;
    }

    public String urlmaker2() {
        return "https://api.openweathermap.org/data/2.5/forecast?q=" + getlocation() + "&units=metric&cnt=10&appid=e5085a29070720b8ee6224e489e9a63a";
    }


    public void forcast() {

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
                                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject s = jsonArray.getJSONObject(j);
                                    iconcode = s.getString("icon");
                                    condition.setText(s.get("main") + "");

                                    break;
                                }

                                Picasso.get().load("https://openweathermap.org/img/wn/" + iconcode + "@2x.png").into(imageView);

                                currenttemp.setText(Math.round(Float.parseFloat(jsonObject.getJSONObject("main").getString("temp"))) + " \u2103");
                                mintemp.setText("Min : " + Math.round(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_min"))) + " \u2103");
                                higttemp.setText("Max : " + Math.round(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_max"))) + " \u2103");

                                cityname.setText(jsonObject.getString("name").toUpperCase());

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

    public void currentweather() {


        cardView = findViewById(R.id.cardView2);
        recyclerView = findViewById(R.id.RV);


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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            fail = findViewById(R.id.fail);
                            fail.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);

                        }
                    });



                    try {

                        jsonObject = new JSONObject(response.body().string());


                        JSONArray jsonArray = jsonObject.getJSONArray("list");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            String code = "";

                            JSONObject j = jsonArray.getJSONObject(i);
                            JSONArray array = j.getJSONArray("weather");
                            for (int s = 0 ; s < array.length() ; s++){
                                JSONObject as = array.getJSONObject(s);
                             code = as.getString("icon");
                            }


                            String url = "https://openweathermap.org/img/wn/"+code+"@2x.png";
                            list.add(new recyclerview_item(j.getJSONObject("main").getString("temp"),
                                            j.getString("dt_txt"),
                                            j.getJSONObject("main").getString("temp_min"),
                                            j.getJSONObject("main").getString("temp_max"),
                                            j.getJSONObject("main").getString("pressure"),
                                            j.getJSONObject("main").getString("humidity"),
                                            url

                                    )

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