package com.example.simple_weather.model;

public class current_model {
    String date;
    String description;
    String temp;
    String cityname;
    String icon_url;
    String country;

    public current_model(String date, String description, String temp, String cityname, String icon_url, String country) {

        this.date = date;
        this.description = description;
        this.temp = temp;
        this.cityname = cityname;
        this.icon_url = icon_url;
        this.country = country;

    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public String getCityname() {
        return cityname;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }
}
