package com.example.simple_weather.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_weather.R;
import com.example.simple_weather.model.detail_model;

import java.util.List;

public class current_recyclerview extends RecyclerView.Adapter<current_recyclerview.viewholder> {

    List<detail_model> mlist;

    public current_recyclerview(List<detail_model> list) {
        this.mlist = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_detail_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.textView.setText(mlist.get(position).getSubject());
        holder.imageView.setImageResource(mlist.get(position).getIcon());
        holder.textView2.setText(mlist.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView2;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_icon);
            textView = itemView.findViewById(R.id.subject);
            textView2 = itemView.findViewById(R.id.detail);
        }
    }
}
