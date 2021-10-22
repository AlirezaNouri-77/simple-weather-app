package com.example.weathertest.model;

public class current_model {

    String description;
    String temp;
    String cityname;
    String icon_url;
    String cloud;
    String pressure;

    public current_model(String description, String temp, String cityname, String icon_url, String cloud, String pressure) {

        this.description = description;
        this.temp = temp;
        this.cityname = cityname;
        this.icon_url = icon_url;
        this.cloud = cloud;
        this.pressure = pressure;
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

    public String getPressure() {
        return pressure;
    }
}
