package com.example.simple_weather.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simple_weather.R;
import com.example.simple_weather.model.searchview_model;

public class searchview_recyclerview extends ListAdapter<searchview_model, searchview_recyclerview.searchview_viewholder> {

    searchview_onclick searchview_onclick;

    public searchview_recyclerview(searchview_onclick searchview_onclick) {
        super(diffCallback);
        this.searchview_onclick = searchview_onclick;
    }

    private static final DiffUtil.ItemCallback<searchview_model> diffCallback = new DiffUtil.ItemCallback<searchview_model>() {
        @Override
        public boolean areItemsTheSame(@NonNull searchview_model oldItem, @NonNull searchview_model newItem) {
            return oldItem.getCountry().equals(newItem.getCountry());
        }

        @Override
        public boolean areContentsTheSame(@NonNull searchview_model oldItem, @NonNull searchview_model newItem) {
            return oldItem.getCountry().equals(newItem.getCountry());
        }
    };

    @NonNull
    @Override
    public searchview_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_searchview, parent, false);
        return new searchview_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchview_viewholder holder, int position) {
        holder.city_textview.setText(getItem(position).getCity());
        holder.country_textview.setText(getItem(position).getCountry());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchview_onclick.searchview_onitemclick(holder.getAdapterPosition());
            }
        });
    }

    public static class searchview_viewholder extends RecyclerView.ViewHolder {

        TextView city_textview, country_textview;

        public searchview_viewholder(@NonNull View itemView) {
            super(itemView);
            city_textview = itemView.findViewById(R.id.city);
            country_textview = itemView.findViewById(R.id.country);
        }

    }

    public interface searchview_onclick {
        void searchview_onitemclick(int i);
    }

}
