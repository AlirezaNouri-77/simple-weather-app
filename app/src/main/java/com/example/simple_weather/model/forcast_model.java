package com.example.simple_weather.model;

public class forcast_model {

    public String temp;
    public String icon;
    public String min;
    public String max;
    public String time;
    public String description;
    public String windspeed;
    public String pressure;
    public String uv;
    public String visibility;
    public String clouds;
    public String rainpossibilty;

    public forcast_model(String temp, String icon, String min, String max, String time, String description, String windspeed, String pressure, String uv, String visibility, String clouds, String rainpossibilty) {
        this.temp = temp;
        this.icon = icon;
        this.min = min;
        this.max = max;
        this.time = time;
        this.description = description;
        this.windspeed = windspeed;
        this.pressure = pressure;
        this.uv = uv;
        this.visibility = visibility;
        this.clouds = clouds;
        this.rainpossibilty = rainpossibilty;
    }

    public String getRainpossibilty() {
        return rainpossibilty;
    }

    public String getTemp() {
        return temp;
    }

    public String getIcon() {
        return icon;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String getPressure() {
        return pressure;
    }

    public String getUv() {
        return uv;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getClouds() {
        return clouds;
    }
}
