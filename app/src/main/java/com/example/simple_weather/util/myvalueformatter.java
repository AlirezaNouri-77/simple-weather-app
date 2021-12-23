package com.example.simple_weather.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class myvalueformatter extends ValueFormatter {

    String[] data;

    public myvalueformatter(String[] data) {
        this.data = data;
    }

    @Override
    public String getFormattedValue(float value) {
        return data[(int) value];
    }
}
