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

    public String current_url_maker(String name) {
        if (name.length() != 0) {
            return "https://api.weatherbit.io/v2.0/current?city=" + name.toLowerCase() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol() + "&include=minutely";
        }
        return "https://api.weatherbit.io/v2.0/current?city=" + sharepreferencedSetting.getdefault() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol() + "&include=minutely";
    }


    public String forcast_url_maker(String name) {
        if (name.length() != 0) {
            return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + name.toLowerCase()  + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol();
        }
        return "https://api.weatherbit.io/v2.0/forecast/daily?city=" + sharepreferencedSetting.getdefault() + "&key=8a8ec0d5af5f4806ada672017c6d44b5&" + "units=" + sharepreferencedSetting.temp_symbol();
    }

}
