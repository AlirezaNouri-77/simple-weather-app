package com.example.weathertest.model;

public class searchview_model {


    String city;
    String country;

    public searchview_model(String city , String country) {
        this.city = city;
        this.country = country;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
