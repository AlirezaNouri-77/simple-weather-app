package com.example.simple_weather.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class sharepreferenced {

    Context mcontext;

    public sharepreferenced(Context context) {
        this.mcontext = context;
    }

    public String get_sharepreference_city() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("city", Context.MODE_PRIVATE);
        return sharedPreferences.getString("cityname", "").toLowerCase();
    }

    public String get_sharepreference_country() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("city", Context.MODE_PRIVATE);
        return sharedPreferences.getString("countryname", "").toLowerCase();
    }

    public void setdefualt(String newcity, String newcountry) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("city", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (newcity != null || newcity.length() != 0) {
            editor.putString("cityname", newcity);
            editor.putString("countryname", newcountry);
            editor.apply();
        }
    }

    public String temp_symbol() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
        if (sharedPreferences.getBoolean("temp", false)) {
            return "I";
        }
        return "M";
    }

    public String getsymbol() {
        if (temp_symbol().equals("M")) {
            return "\u2103";
        }
        return "\u2109";
    }
}
