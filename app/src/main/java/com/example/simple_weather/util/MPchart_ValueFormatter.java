package com.example.simple_weather.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class MPchart_ValueFormatter extends ValueFormatter {

    String[] data;

    public MPchart_ValueFormatter(String[] data) {
        this.data = data;
    }

    @Override
    public String getFormattedValue(float value) {
        return data[(int) value];
    }
}
