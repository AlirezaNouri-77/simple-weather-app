package com.example.weathertest.model;

public class forcast_model {

    public String temp;
    public String icon;
    public String min;
    public String max;
    public String time;
    public String description;
    public String windspeed;
    public String pressure;
    public String humidty;


    public forcast_model(String temp, String time, String max, String min , String icon) {
        this.temp = temp;
        this.time = time;
        this.max = max;
        this.icon=icon;
        this.min = min;
    }

}
