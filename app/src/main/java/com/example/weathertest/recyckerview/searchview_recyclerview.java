package com.example.weathertest.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.searchview_model;

import java.util.ArrayList;
import java.util.List;

public class searchview_recyclerview extends RecyclerView.Adapter<searchview_recyclerview.searchview_viewholder> implements Filterable {

    List<searchview_model> list;
    List<searchview_model> fulllist;
    List<searchview_model> filterlist;
    searchview_onclick searchview_onclick;

    public searchview_recyclerview(List<searchview_model> list , searchview_onclick searchview_onclick) {
        this.list = list;
        this.searchview_onclick = searchview_onclick;
        this.fulllist = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public searchview_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_searchview, parent, false);
        return new searchview_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchview_viewholder holder, int position) {
        holder.textView.setText(list.get(position).getCity() + " / " + list.get(position).getCountry() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchview_onclick.searchview_onitemclick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class searchview_viewholder extends RecyclerView.ViewHolder{

        TextView textView;

        public searchview_viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.city);
        }

    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filterlist = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filterlist.addAll(fulllist);
                } else {
                    String text = constraint.toString().toLowerCase().trim();
                    for (searchview_model searchview_model : fulllist) {
                        if (searchview_model.getCity().toLowerCase().contains(text)||searchview_model.getCountry().toLowerCase().contains(text)) {
                            filterlist.add(searchview_model);
                        } else {

                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filterlist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((List) results.values);
                notifyDataSetChanged();
                ;
            }
        };

        return filter;
    }

    public interface searchview_onclick {
        void searchview_onitemclick(int i);
    }

}
