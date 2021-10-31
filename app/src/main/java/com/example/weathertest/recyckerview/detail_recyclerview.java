package com.example.weathertest.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.detail_model;
import com.example.weathertest.model.forcast_model;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class detail_recyclerview extends RecyclerView.Adapter<detail_recyclerview.viewholder> {

    List<detail_model> mlist;

    public detail_recyclerview(List<detail_model> list) {
        this.mlist = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_recyclerview_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.textView.setText(mlist.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public static class viewholder extends RecyclerView.ViewHolder {

        TextView textView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
