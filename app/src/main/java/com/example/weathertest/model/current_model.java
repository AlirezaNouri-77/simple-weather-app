package com.example.weathertest.model;

public class current_model {
    String description;
    String temp;
    String cityname;
    String icon_url;
    public current_model(String description, String temp, String cityname, String icon_url) {
        this.description = description;
        this.temp = temp;
        this.cityname = cityname;
        this.icon_url = icon_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
