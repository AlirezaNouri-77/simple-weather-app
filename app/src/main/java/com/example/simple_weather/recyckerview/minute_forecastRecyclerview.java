package com.example.simple_weather.recyckerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_weather.R;
import com.example.simple_weather.model.minute_model;
import com.example.simple_weather.util.My_Sharepreferenced;

import java.util.List;

public class minute_forecastRecyclerview extends RecyclerView.Adapter<minute_forecastRecyclerview.ViewHolder> {

    Context context;
    private final List<minute_model> list;
    private final My_Sharepreferenced my_sharepreferenced;

    public minute_forecastRecyclerview(List<minute_model> list, Context context) {
        this.list = list;
        this.context = context;
        my_sharepreferenced = new My_Sharepreferenced(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_minutely_forcast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.temp.setText(list.get(position).getTemp() + " " + my_sharepreferenced.getsymbol());
        holder.time.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView temp;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp);
            time = itemView.findViewById(R.id.time);

        }
    }
}
