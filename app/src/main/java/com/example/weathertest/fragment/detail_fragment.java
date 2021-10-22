package com.example.weathertest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weathertest.R;
import com.squareup.picasso.Picasso;

public class detail_fragment extends Fragment {

    TextView temp, test, min, max, pressure, humidity;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_layout, container, false);

        temp = view.findViewById(R.id.temp_detail);
        test = view.findViewById(R.id.test2);
        min = view.findViewById(R.id.min_detail);
        max = view.findViewById(R.id.max_detail);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        imageView = view.findViewById(R.id.imageView2);

        showview();

        return view;

    }


    public void showview() {

        if (getArguments() != null){

            Log.d("TAG", "showview: " + getArguments());


            temp.setText(Math.round(Double.parseDouble(String.valueOf(getArguments().get("Temp")))) + "\u2103");
            min.setText("Min" + "\n" + Math.round(Double.parseDouble(String.valueOf(getArguments().get("min")))) + "\u2103");
            max.setText("Max" + "\n" + Math.round(Double.parseDouble(String.valueOf(getArguments().get("max")))) + "\u2103");
          //  pressure.setText("Prssure : " + getArguments().get("pressure"));
           // humidity.setText("humidity : " + getArguments().get("humidity"));
            test.setText(String.valueOf(getArguments().get("time")));
            Picasso.get().load(String.valueOf(getArguments().get("iconurl"))).into(imageView);


        }

    }
}
