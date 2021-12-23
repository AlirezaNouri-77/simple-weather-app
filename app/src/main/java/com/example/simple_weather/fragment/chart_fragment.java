package com.example.simple_weather.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple_weather.R;
import com.example.simple_weather.model.chart_model;
import com.example.simple_weather.util.myvalueformatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class chart_fragment extends Fragment {

    LineChart lineChart;
    LineChart lineChart2;

    String[] data_date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_layout, container, false);

        List<chart_model> temp = new ArrayList<>(getArguments().getParcelableArrayList("temp_data_chart"));

        try {
            data_date = converter(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lineChart = view.findViewById(R.id.temp_chart);
        lineChart2 = view.findViewById(R.id.max_min_linechart);

        try {
            initial_temp_chart();
            initial_2tmep_chart();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void initial_temp_chart() throws ParseException {

        List<chart_model> temp_chart_mydata = new ArrayList<>();

        if (getArguments().getParcelableArrayList("two_data_chart") != null) {
            temp_chart_mydata = getArguments().getParcelableArrayList("two_data_chart");

        }

        List<Entry> temp_data = new ArrayList<>();
        for (int index = 0; index < temp_chart_mydata.size(); index++) {
            temp_data.add(new Entry(index, Float.parseFloat(temp_chart_mydata.get(index).getWeek())));
        }

        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setMaxHighlightDistance(300);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);


        XAxis x = lineChart.getXAxis();
        x.setEnabled(true);
        x.disableAxisLineDashedLine();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setValueFormatter(new myvalueformatter(data_date));

        YAxis yLeft = lineChart.getAxisLeft();
        yLeft.setEnabled(true);
        yLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yLeft.setDrawAxisLine(false);
        yLeft.setDrawGridLines(false);
        yLeft.setXOffset(15);

        LineDataSet lineDataSet = new LineDataSet(temp_data, "Temp");
        LineData lineData = new LineData(lineDataSet);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.1f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(Color.parseColor("#e53935"));

        lineChart.setDescription(null);
        lineChart.getLegend().setEnabled(true);
        lineChart.setData(lineData);
        lineChart.setData(lineData);

    }


    public void initial_2tmep_chart() throws ParseException {

        List<chart_model> two_temp_chart_mydata = new ArrayList<>();

        if (getArguments().getParcelableArrayList("two_data_chart") != null) {
            two_temp_chart_mydata = getArguments().getParcelableArrayList("two_data_chart");
        }

        List<Entry> min_temp = new ArrayList<>();
        List<Entry> max_temp = new ArrayList<>();
        for (int index = 0; index < two_temp_chart_mydata.size(); index++) {
            min_temp.add(new Entry(index, Float.parseFloat(two_temp_chart_mydata.get(index).getX())));
            max_temp.add(new Entry(index, Float.parseFloat(two_temp_chart_mydata.get(index).getWeek())));
        }

        lineChart2.setDrawGridBackground(false);
        lineChart2.setTouchEnabled(true);
        lineChart2.setDragEnabled(true);
        lineChart2.setMaxHighlightDistance(300);
        lineChart2.getLegend().setEnabled(false);
        lineChart2.getAxisRight().setEnabled(false);


        XAxis x = lineChart2.getXAxis();
        x.setEnabled(true);
        x.disableAxisLineDashedLine();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setValueFormatter(new myvalueformatter(data_date));

        YAxis yLeft = lineChart2.getAxisLeft();
        yLeft.setEnabled(true);
        yLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yLeft.setDrawAxisLine(false);
        yLeft.setDrawGridLines(false);
        yLeft.setXOffset(15);

        LineDataSet lineDataSet = new LineDataSet(min_temp, "Min Temp");
        LineDataSet lineDataSet1 = new LineDataSet(max_temp, "Max Temp");
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineData.addDataSet(lineDataSet1);


        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.1f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(Color.parseColor("#2979ff"));

        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setCubicIntensity(0.1f);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setLineWidth(2f);
        lineDataSet1.setValueTextSize(10f);
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setColor(Color.parseColor("#ff1744"));

        lineChart2.setDescription(null);
        lineChart2.getLegend().setEnabled(true);
        lineChart2.setData(lineData);


    }

    public String[] converter(List<chart_model> list) throws ParseException {

        String[] date_set = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = simpleDateFormat.parse(list.get(i).getX());
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EE", Locale.getDefault());
            simpleDateFormat1.format(date);
            date_set[i] = simpleDateFormat1.format(date);

        }

        return date_set;
    }
}
