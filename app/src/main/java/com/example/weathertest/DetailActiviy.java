package com.example.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activiy);

        Intent intent = this.getIntent();
        TextView temp = findViewById(R.id.temp_detail);
        TextView test = findViewById(R.id.test2);
        TextView min = findViewById(R.id.min_detail);
        TextView max = findViewById(R.id.max_detail);
        TextView pressure = findViewById(R.id.prssure);
        TextView humidity = findViewById(R.id.humidity);
        ImageView imageView = findViewById(R.id.imageView2);

        temp.setText(Math.round(Double.parseDouble(intent.getStringExtra("Temp"))) + "\u2103");
        min.setText("Min" + "\n" + Math.round(Double.parseDouble(intent.getStringExtra("min"))) + "\u2103");
        max.setText("Max" + "\n" + Math.round(Double.parseDouble(intent.getStringExtra("max"))) + "\u2103");
        pressure.setText("Prssure : " + intent.getStringExtra("pressure"));
        humidity.setText("humidity : " + intent.getStringExtra("humidity"));
        test.setText(intent.getStringExtra("time"));
        Picasso.get().load(intent.getStringExtra("iconurl")).into(imageView);

    }
}