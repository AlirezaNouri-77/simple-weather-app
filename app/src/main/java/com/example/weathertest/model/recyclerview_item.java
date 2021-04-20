package com.example.weathertest.model;

public class recyclerview_item {

    public String temp;
    public String icon;
    public String min;
    public String pressure;
    public String humidity;
    public String max;
    public String time;

    public recyclerview_item(String temp, String time, String max, String min , String pressure , String humidity ,String icon) {
        this.temp = temp;
        this.time = time;
        this.max = max;
        this.icon=icon;
        this.pressure=pressure;
        this.humidity=humidity;
        this.min = min;
    }

}
