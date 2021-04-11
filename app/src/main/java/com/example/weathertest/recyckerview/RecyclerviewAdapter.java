package com.example.weathertest.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.recyclerview_item;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.viewholder> {

    List<recyclerview_item> list;


    public RecyclerviewAdapter(List<recyclerview_item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.temp.setText(list.get(position).temp);
        holder.test.setText(list.get(position).test);
//        if (list.get(position).test==1){
//            holder.test.setText("Cloudy");
//        }else if (list.get(position).test==2){
//            holder.test.setText("Rainy");
//        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        TextView temp;
        TextView test;


        public viewholder(@NonNull View itemView) {

            super(itemView);
            temp = itemView.findViewById(R.id.temp);
            test = itemView.findViewById(R.id.test);


        }
    }
}
