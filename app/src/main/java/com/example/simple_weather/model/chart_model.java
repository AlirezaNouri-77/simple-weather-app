package com.example.simple_weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class chart_model implements Parcelable {
    String x;
    String week;

    public chart_model(String week, String x) {
        this.x = x;
        this.week = week;
    }

    public String getX() {
        return x;
    }

    public String getWeek() {
        return week;
    }

    protected chart_model(Parcel in) {
        x = in.readString();
        week = in.readString();
    }

    public static final Creator<chart_model> CREATOR = new Creator<chart_model>() {
        @Override
        public chart_model createFromParcel(Parcel in) {
            return new chart_model(in);
        }

        @Override
        public chart_model[] newArray(int size) {
            return new chart_model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(x);
        dest.writeString(week);
    }
}
