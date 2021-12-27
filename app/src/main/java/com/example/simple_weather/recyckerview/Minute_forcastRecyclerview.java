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
import com.example.simple_weather.util.sharepreferenced;

import java.util.List;

public class Minute_forcastRecyclerview extends RecyclerView.Adapter<Minute_forcastRecyclerview.Viewholder> {

    Context context;
    List<minute_model> list;
    sharepreferenced sharepreferencedSetting;

    public Minute_forcastRecyclerview(List<minute_model> list, Context context) {
        this.list = list;
        this.context = context;
        sharepreferencedSetting = new sharepreferenced(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_minutely_forcast, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.temp.setText(list.get(position).getTemp() + " "+sharepreferencedSetting.getsymbol());
        holder.time.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView temp;
        TextView time;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp);
            time = itemView.findViewById(R.id.time);
        }
    }
}
