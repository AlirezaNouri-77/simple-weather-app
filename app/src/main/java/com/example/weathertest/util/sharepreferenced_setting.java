package com.example.weathertest.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class sharepreferenced_setting {

    Context mc;
    public sharepreferenced_setting(Context context) {
        this.mc = context;
    }

    public String getdefault (){
        SharedPreferences sharedPreferences = mc.getSharedPreferences("city" , Context.MODE_PRIVATE);
        return sharedPreferences.getString("cityname" , "");
    }

    public void setdefualt (String newcity){
        SharedPreferences sharedPreferences = mc.getSharedPreferences("city" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (newcity != null || newcity.length() != 0){
            editor.putString("cityname" , newcity);
            editor.apply();
        }
    }


    public String temp_symbol() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mc);
        if (sharedPreferences.getBoolean("temp", false)) {
            return "I";
        }
        return "M";
    }

    public String getsymbol (){
        if (temp_symbol().equals("M")){
            return "\u2103";
        }
        return "\u2109";
    }
}
