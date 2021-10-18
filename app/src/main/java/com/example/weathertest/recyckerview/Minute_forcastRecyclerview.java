package com.example.weathertest.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.minute_model;

import java.util.List;

public class Minute_forcastRecyclerview extends RecyclerView.Adapter<Minute_forcastRecyclerview.Viewholder> {

    List<minute_model> list;

    public Minute_forcastRecyclerview(List<minute_model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minute_forcastrecyclerview, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.temp.setText(list.get(position).getTemp());
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
