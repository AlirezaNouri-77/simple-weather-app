package com.example.simple_weather.util;

import android.content.Context;

import java.util.Locale;

public class url_maker {

    Context context;
    sharepreferenced sharepreferencedSetting;

    public url_maker(Context context) {
        this.context = context;
        sharepreferencedSetting = new sharepreferenced(context);
    }

    public String current_url_maker(String city_text, String country_text) {
        if (city_text.length() != 0) {
            return "https://api.weatherbit.io/v2.0/current?"
                    + "&city=" + city_text.toLowerCase()
                    + "&country=" + country_text.toLowerCase()
                    + "&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                    + "units=" + sharepreferencedSetting.temp_symbol()
                    + "&include=minutely";
        }
        return "https://api.weatherbit.io/v2.0/current?"
                + "&city=" + sharepreferencedSetting.get_sharepreference_city().toLowerCase()
                + "&country=" + sharepreferencedSetting.get_sharepreference_country().toLowerCase()
                + "&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                + "units=" + sharepreferencedSetting.temp_symbol()
                + "&include=minutely";
    }


    public String forcast_url_maker(String city_text, String country_text) {
        if (city_text.length() != 0) {

            return "https://api.weatherbit.io/v2.0/forecast/daily?" +
                    "&city=" + city_text.toLowerCase()
                    + "&country=" + country_text.toLowerCase()
                    + "&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                    + "units=" + sharepreferencedSetting.temp_symbol();

        }
        return "https://api.weatherbit.io/v2.0/forecast/daily?" +
                "&city=" + sharepreferencedSetting.get_sharepreference_city().toLowerCase()
                + "&country=" + sharepreferencedSetting.get_sharepreference_country().toLowerCase()
                + "&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                + "units=" + sharepreferencedSetting.temp_symbol();
    }

}
