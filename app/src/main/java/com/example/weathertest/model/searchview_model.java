package com.example.weathertest.model;

import com.google.gson.annotations.SerializedName;

public class searchview_model {

    @SerializedName("name")
    String city;

    public searchview_model() {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
