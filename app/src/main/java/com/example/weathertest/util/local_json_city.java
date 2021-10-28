package com.example.weathertest.util;

import android.content.Context;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weathertest.MainActivity;
import com.example.weathertest.model.searchview_model;
import com.example.weathertest.recyckerview.searchview_recyclerview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class local_json_city {

    Context context;
    sharepreferenced_setting sharepreferencedSetting;

    public local_json_city(Context context) {
        this.context = context;
        sharepreferencedSetting = new sharepreferenced_setting(context);
    }

    public List<searchview_model> get_searchview_list() {

        List<searchview_model> mlist = new ArrayList<>();

        try {

            JSONObject jsonObject2 = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject2.getJSONArray("data");

            for (int i = 0; jsonArray.length() > i; i++) {

                String jsonObject1 = jsonArray.getJSONObject(i).getString("country");
                JSONArray test = jsonArray.getJSONObject(i).getJSONArray("cities");

                for (int j = 0; j < test.length(); j++) {
                    mlist.add(new searchview_model(test.get(j).toString(), jsonObject1));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mlist;
    }

    public String loadJSONFromAsset() {

        String json;

        try {

            InputStream is = context.getAssets().open("city.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

    public String current_url_maker(String name , String country) {
        if (name.length() != 0) {
            return "https://api.weatherbit.io/v2.0/current?city=" + name.toLowerCase() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol() + "&include=minutely";
        }
        return "https://api.weatherbit.io/v2.0/current?city=" + sharepreferencedSetting.getdefault() + "&country=" + country + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol() + "&include=minutely";
    }


    public String forcast_url_maker(String name, String country) {
        if (name.length() != 0) {
            return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + name.toLowerCase()  + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol();
        }
        return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + sharepreferencedSetting.getdefault() + "&country=" + country + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol();
    }

}
