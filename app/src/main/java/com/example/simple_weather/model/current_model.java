package com.example.simple_weather.model;

public class current_model {
    String date;
    String description;
    String temp;
    String cityname;
    String icon_url;
    String cloud;
    String country;
    String visibility;
    String uv;

    public current_model(String date, String description, String temp, String cityname, String icon_url, String cloud, String visibility, String country, String uv) {

        this.date = date;
        this.description = description;
        this.temp = temp;
        this.cityname = cityname;
        this.icon_url = icon_url;
        this.cloud = cloud;
        this.visibility = visibility;
        this.country = country;
        this.uv = uv;
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

    public String getCloud() {
        return cloud;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getUv() {
        return uv;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }
}
