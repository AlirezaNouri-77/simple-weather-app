package com.example.weathertest.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class sharepreferenced_setting {

    Context mcontext;
    public sharepreferenced_setting(Context context) {
        this.mcontext = context;
    }

    public String getdefault() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("city", Context.MODE_PRIVATE);
        return sharedPreferences.getString("cityname", "").toLowerCase();
    }

    public void setdefualt(String newcity) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("city", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (newcity != null || newcity.length() != 0) {
            editor.putString("cityname", newcity);
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
