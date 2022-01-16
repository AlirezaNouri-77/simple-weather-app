package com.example.simple_weather.recyckerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_weather.R;
import com.example.simple_weather.model.detail_model;

import java.util.List;

public class detail_recyclerview extends RecyclerView.Adapter<detail_recyclerview.viewholder> {

    List<detail_model> mlist;
    Context context;

    public detail_recyclerview(List<detail_model> list, Context context) {
        this.mlist = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_detail_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.subject.setText(mlist.get(position).getSubject());
        holder.detail.setText(mlist.get(position).getDetail());
        holder.icon_imageview.setImageResource(mlist.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public static class viewholder extends RecyclerView.ViewHolder {

        TextView subject, detail;
        ImageView icon_imageview;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            icon_imageview = itemView.findViewById(R.id.detail_icon);
            subject = itemView.findViewById(R.id.subject);
            detail = itemView.findViewById(R.id.detail);
        }
    }
}
