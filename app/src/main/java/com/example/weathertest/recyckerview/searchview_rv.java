package com.example.weathertest.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.searchview_model;

import java.util.List;

public class searchview_rv extends RecyclerView.Adapter<searchview_rv.searchview_viewholder> {

    List<searchview_model> list;

    public searchview_rv(List<searchview_model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public searchview_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchview_recyclerview, parent, false);
        return new searchview_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchview_viewholder holder, int position) {
        holder.textView.setText(list.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class searchview_viewholder extends RecyclerView.ViewHolder {

        TextView textView;

        public searchview_viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.city);
        }
    }
}
