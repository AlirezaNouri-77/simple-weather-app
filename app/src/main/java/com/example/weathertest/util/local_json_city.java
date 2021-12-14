package com.example.weathertest.util;

import android.content.Context;

public class local_json_city {

    Context context;
    sharepreferenced_setting sharepreferencedSetting;

    public local_json_city(Context context) {
        this.context = context;
        sharepreferencedSetting = new sharepreferenced_setting(context);
    }

    public String current_url_maker(String city_text, String country_text) {
        if (city_text.length() != 0) {
            return "https://api.weatherbit.io/v2.0/current?"
                    + "&city=" + city_text
                    +"&country=" + country_text
                    +"&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                    + "units=" + sharepreferencedSetting.temp_symbol()
                    + "&include=minutely";
        }
        return "https://api.weatherbit.io/v2.0/current?"
                + "&city=" + sharepreferencedSetting.get_sharepreference_city()
                + "&country=" + sharepreferencedSetting.get_sharepreference_country()
                + "&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                + "units=" + sharepreferencedSetting.temp_symbol()
                + "&include=minutely";
    }


    public String forcast_url_maker(String city_text, String country_text) {
        if (city_text.length() != 0) {

            return "https://api.weatherbit.io/v2.0/forecast/daily?" +
                    "&city=" + city_text
                    +"&country=" + country_text
                    +"&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                    + "units=" + sharepreferencedSetting.temp_symbol();

        }
        return "https://api.weatherbit.io/v2.0/forecast/daily?" +
                "&city=" + sharepreferencedSetting.get_sharepreference_city()
                + "&country=" + sharepreferencedSetting.get_sharepreference_country()
                + "&key=8a8ec0d5af5f4806ada672017c6d44b5&"
                + "units=" + sharepreferencedSetting.temp_symbol();
    }

}
