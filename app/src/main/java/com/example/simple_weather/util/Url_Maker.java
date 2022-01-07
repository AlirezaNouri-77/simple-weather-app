package com.example.simple_weather.util;

import static com.example.simple_weather.util.Constant.API_KEY_WEATHERBIT;

import android.content.Context;

public class Url_Maker {

    Context context;
    My_Sharepreferenced sharepreferencedSetting;

    public Url_Maker(Context context) {
        this.context = context;
        sharepreferencedSetting = new My_Sharepreferenced(context);
    }

    public String current_url_maker(String city_text, String country_text) {
        if (city_text.length() == 0) {
            city_text = sharepreferencedSetting.get_sharepreference_city().toLowerCase();
            country_text = sharepreferencedSetting.get_sharepreference_country().toLowerCase();
        }
        return "https://api.weatherbit.io/v2.0/current?"
                + "&city=" + city_text
                + "&country=" + country_text
                + "&key=" + API_KEY_WEATHERBIT
                + "&units=" + sharepreferencedSetting.temp_symbol()
                + "&include=minutely";
    }

    public String forcast_url_maker(String city_text, String country_text) {

        if (city_text.length() == 0) {
            city_text = sharepreferencedSetting.get_sharepreference_city().toLowerCase();
            country_text = sharepreferencedSetting.get_sharepreference_country().toLowerCase();
        }
        return "https://api.weatherbit.io/v2.0/forecast/daily?" +
                "&city=" + city_text
                + "&country=" + country_text
                + "&key=" + API_KEY_WEATHERBIT
                + "&units=" + sharepreferencedSetting.temp_symbol();
    }

}
