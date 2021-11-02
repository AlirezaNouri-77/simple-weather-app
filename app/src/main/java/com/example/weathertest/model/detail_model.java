package com.example.weathertest.model;

public class detail_model {
    String subject;
    String detail;

    public detail_model(String subject, String detail) {
        this.subject = subject;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public String getSubject() {
        return subject;
    }
}
