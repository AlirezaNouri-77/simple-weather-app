package com.example.simple_weather.model;

import android.graphics.drawable.Drawable;

public class detail_model {

    int icon;
    String subject;
    String detail;

    public detail_model(String subject, String detail, int drawable) {
        this.subject = subject;
        this.detail = detail;
        this.icon = drawable;
    }

    public String getDetail() {
        return detail;
    }

    public String getSubject() {
        return subject;
    }

    public int getIcon() {
        return icon;
    }
}
