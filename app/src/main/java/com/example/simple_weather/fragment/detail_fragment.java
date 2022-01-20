package com.example.simple_weather.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simple_weather.R;
import com.example.simple_weather.model.detail_model;
import com.example.simple_weather.recyckerview.detail_recyclerview;
import com.example.simple_weather.util.Get_Weekname_FromDate;
import com.example.simple_weather.util.My_Sharepreferenced;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class detail_fragment extends Fragment {

    private TextView current_temp, current_weather, low_temp, high_temp, date, tomarrow;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Get_Weekname_FromDate get_weekname_fromDate;
    private My_Sharepreferenced sharepreferenced;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_layout, container, false);

        current_temp = view.findViewById(R.id.temp_detail);
        current_weather = view.findViewById(R.id.descreption);
        low_temp = view.findViewById(R.id.min_detail);
        high_temp = view.findViewById(R.id.max_detail);
        imageView = view.findViewById(R.id.imageView2);
        recyclerView = view.findViewById(R.id.detail_rv);
        date = view.findViewById(R.id.date_textview);
        tomarrow = view.findViewById(R.id.tomarrow_weather);

        sharepreferenced = new My_Sharepreferenced(getContext());
        get_weekname_fromDate = new Get_Weekname_FromDate();


        try {

            showview();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return view;

    }


    public void showview() throws ParseException {

        if (getArguments() != null) {

            List<detail_model> list = new ArrayList<>();

            Glide.with(getContext()).load(getArguments().get("iconurl").toString()).into(imageView);
            current_temp.setText(Math.round(Double.parseDouble(String.valueOf(getArguments().get("Temp")))) + sharepreferenced.getsymbol());
            low_temp.setText(Math.round(Double.parseDouble(String.valueOf(getArguments().get("min")))) + sharepreferenced.getsymbol());
            high_temp.setText(Math.round(Double.parseDouble(String.valueOf(getArguments().get("max")))) + sharepreferenced.getsymbol());
            current_weather.setText(getArguments().get("description") + "");
            date.setText(get_weekname_fromDate.get_week_name(getArguments().get("time").toString()));


            tomarrow.setText("Average temp is " + Math.round(Double.parseDouble(getArguments().get("tomarrow_temp").toString())) +
                    sharepreferenced.getsymbol() + "\n" + "Max Temp is " + Math.round(Double.parseDouble(getArguments().get("tomarrow_max_temp").toString())) + sharepreferenced.getsymbol() + "\n" +
                    "Min Temp is " + Math.round(Double.parseDouble(getArguments().get("tomarrow_min_temp").toString())) + sharepreferenced.getsymbol());


            list.add(new detail_model("Average Pressure", getArguments().get("pressure").toString() + " mb", R.drawable.icon_pressure));
            list.add(new detail_model("Range Visibility", getArguments().get("visibility").toString() + " KM", R.drawable.icon_visibility));
            list.add(new detail_model("Wind Speed ", getArguments().get("windspeed").toString() + " m/s", R.drawable.icon_wind_speed));
            list.add(new detail_model("UV", getArguments().get("uv").toString(), R.drawable.icon_uv_index));
            list.add(new detail_model("Clouds Coverage", getArguments().get("clouds").toString() + "%", R.drawable.icon_cloud));
            list.add(new detail_model("Probability of Precipitation", getArguments().get("Probability").toString() + "%", R.drawable.icon_precipitation));

            com.example.simple_weather.recyckerview.detail_recyclerview detail_recyclerview = new detail_recyclerview(list, getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(detail_recyclerview);


        }
    }

}
