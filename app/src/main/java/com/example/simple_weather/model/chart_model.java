package com.example.simple_weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class chart_model implements Parcelable {
    String y_axis;
    String x_axis;

    public chart_model(String x_axis, String y_axis) {
        this.y_axis = y_axis;
        this.x_axis = x_axis;
    }

    public String getY_axis() {
        return y_axis;
    }

    public String getX_axis() {
        return x_axis;
    }

    protected chart_model(Parcel in) {
        y_axis = in.readString();
        x_axis = in.readString();
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
        dest.writeString(y_axis);
        dest.writeString(x_axis);
    }
}
