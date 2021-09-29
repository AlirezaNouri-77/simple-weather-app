package com.example.weathertest.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class sharepreferenced_setting {

    Context mc;
    public sharepreferenced_setting(Context context) {
        this.mc = context;
    }

    public String getlocation() {
        //get city name in setting page (user enter this city )
        String location = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mc);
        if (sharedPreferences.getString("location", null) != null) {
            location = sharedPreferences.getString("location", "");
        }
        return location;
    }

    public String temp_symbol() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mc);
        if (sharedPreferences.getBoolean("temp", false)) {
            return "I";
        }
        return "M";
    }
}
