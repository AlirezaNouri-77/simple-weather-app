package com.example.simple_weather.model;

public class minute_model {
    String time;
    String temp;
    public minute_model(String time, String temp) {
        this.time = time;
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
