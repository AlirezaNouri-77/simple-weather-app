package com.example.weathertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.weathertest.model.recyclerview_item;
import com.example.weathertest.recyckerview.RecyclerviewAdapter;

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

public class MainActivity extends AppCompatActivity {

    String url = "https://api.openweathermap.org/data/2.5/weather?q=tehran&units=metric&appid=e5085a29070720b8ee6224e489e9a63a";
    String url2 = "https://api.openweathermap.org/data/2.5/forecast?q=tehran&units=metric&cnt=10&appid=e5085a29070720b8ee6224e489e9a63a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<recyclerview_item> list = new ArrayList<>();
        TextView cityname = findViewById(R.id.cityname);
        TextView currenttemp = findViewById(R.id.currenttemp);
        TextView mintemp = findViewById(R.id.mintemp);
        TextView higttemp = findViewById(R.id.hightemp);


        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient okHttpClient1 = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Request request1 = new Request.Builder()
                .url(url2)
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


                                double min = Math.round(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_min")));
                                double current = Math.round(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp")));
                                double max = Math.round(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_max")));

                                currenttemp.setText(current + "\u2103");
                                mintemp.setText(min + "\u2103");
                                higttemp.setText(max + "\u2103");
                                cityname.setText(jsonObject.getString("name").toUpperCase());


                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }


            }
        });

        okHttpClient1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                JSONObject jsonObject;

                try {

                    jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject j = jsonArray.getJSONObject(i);

                        String n = j.getJSONObject("main").getString("temp");
                        // String n1 = j.getJSONObject("main").getString("dt_txt");
                        String n1 = j.getString("dt_txt");
                        list.add(new recyclerview_item(n, n1));

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView recyclerView = findViewById(R.id.RV);
                            RecyclerviewAdapter adapter = new RecyclerviewAdapter(list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(adapter);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }
}