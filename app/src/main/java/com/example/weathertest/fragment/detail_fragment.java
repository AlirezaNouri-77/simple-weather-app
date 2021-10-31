package com.example.weathertest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.detail_model;
import com.example.weathertest.recyckerview.detail_recyclerview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class detail_fragment extends Fragment {

    TextView temp, date_time, min, max, pressure, visibility_textview;
    ImageView imageView;
    com.example.weathertest.recyckerview.detail_recyclerview detail_recyclerview;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_layout, container, false);

        temp = view.findViewById(R.id.temp_detail);
        date_time = view.findViewById(R.id.descreption);
        min = view.findViewById(R.id.min_detail);
        max = view.findViewById(R.id.max_detail);
        imageView = view.findViewById(R.id.imageView2);
        recyclerView = view.findViewById(R.id.detail_rv);


        showview();

        return view;

    }


    public void showview() {

        if (getArguments() != null) {

            List<detail_model> list = new ArrayList<>();

            temp.setText(Math.round(Double.parseDouble(String.valueOf(getArguments().get("Temp")))) + "\u2103");
            min.setText("L" + "\n" + Math.round(Double.parseDouble(String.valueOf(getArguments().get("min")))) + "\u2103");
            max.setText("H" + "\n" + Math.round(Double.parseDouble(String.valueOf(getArguments().get("max")))) + "\u2103");
            date_time.setText(getArguments().get("description") + "");

            Picasso.get().load(String.valueOf(getArguments().get("iconurl"))).into(imageView);

            list.clear();

            list.add(new detail_model("pressure : " + getArguments().get("pressure")));
            list.add(new detail_model("visibility : " + getArguments().get("visibility")));
            list.add(new detail_model("Wind Speed :" + getArguments().get("windspeed")));
            list.add(new detail_model("UV :" + getArguments().get("uv")));
            list.add(new detail_model("Clouds :" + getArguments().get("clouds")));
            list.add(new detail_model("UV :" + getArguments().get("uv")));

            detail_recyclerview = new detail_recyclerview(list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(detail_recyclerview);


        }

    }
}
