package com.example.weathertest.model;

public class recyclerview_item {

    public String temp;
    public String icon;
    public String min;
    public String max;
    public String time;

    public recyclerview_item(String temp, String time, String max, String min ,String icon) {
        this.temp = temp;
        this.time = time;
        this.max = max;
        this.icon=icon;
        this.min = min;
    }

}
