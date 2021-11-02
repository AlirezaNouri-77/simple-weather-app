package com.example.weathertest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.detail_model;
import com.example.weathertest.recyckerview.detail_recyclerview;
import com.example.weathertest.util.sharepreferenced_setting;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class detail_fragment extends Fragment {

    TextView current_temp, date_time, low_temp, high_temp;
    ImageView imageView;
    com.example.weathertest.recyckerview.detail_recyclerview detail_recyclerview;
    RecyclerView recyclerView;
    com.example.weathertest.util.sharepreferenced_setting sharepreferenced_setting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_layout, container, false);

        current_temp = view.findViewById(R.id.temp_detail);
        date_time = view.findViewById(R.id.descreption);
        low_temp = view.findViewById(R.id.min_detail);
        high_temp = view.findViewById(R.id.max_detail);
        imageView = view.findViewById(R.id.imageView2);
        recyclerView = view.findViewById(R.id.detail_rv);

        sharepreferenced_setting = new sharepreferenced_setting(getContext());


        showview();

        return view;

    }


    public void showview() {

        if (getArguments() != null) {

            List<detail_model> list = new ArrayList<>();

            Picasso.get().load(String.valueOf(getArguments().get("iconurl"))).into(imageView);
            current_temp.setText(Math.round(Double.parseDouble(String.valueOf(getArguments().get("Temp")))) + sharepreferenced_setting.getsymbol());
            low_temp.setText("L" + "\n\n" + Math.round(Double.parseDouble(String.valueOf(getArguments().get("min")))) + sharepreferenced_setting.getsymbol());
            high_temp.setText("H" + "\n\n" + Math.round(Double.parseDouble(String.valueOf(getArguments().get("max")))) + sharepreferenced_setting.getsymbol());
            date_time.setText(getArguments().get("description") + "");

            list.clear();

            list.add(new detail_model("Average Pressure", getArguments().get("pressure").toString() + " mb"));
            list.add(new detail_model("Range Visibility", getArguments().get("visibility").toString() + " KM"));
            list.add(new detail_model("Wind Speed ", getArguments().get("windspeed").toString() + " m/s"));
            list.add(new detail_model("UV", getArguments().get("uv").toString()));
            list.add(new detail_model("Clouds Coverage", getArguments().get("clouds").toString() + "%"));
            list.add(new detail_model("Probability of Precipitation", getArguments().get("Probability").toString() + "%"));

            detail_recyclerview = new detail_recyclerview(list);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(detail_recyclerview);


        }

    }

}
