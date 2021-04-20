package com.example.weathertest.recyckerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.recyclerview_item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.viewholder> {

    List<recyclerview_item> list;
    private Onitemclick monitemclicl;


    public RecyclerviewAdapter(List<recyclerview_item> list, Onitemclick onitemclick) {
        this.list = list;
        this.monitemclicl = onitemclick;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new viewholder(view, monitemclicl);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.temp.setText(Math.round(Double.parseDouble(list.get(position).temp)) + "\u2103");
        holder.min.setText("Min" + "\n" + Math.round(Double.parseDouble(list.get(position).min)) + "\u2103");
        holder.max.setText("Max" + "\n" + Math.round(Double.parseDouble(list.get(position).max)) + "\u2103");
        Picasso.get().load(list.get(position).icon).fit().into(holder.imageView);
        holder.test.setText(list.get(position).time);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView temp, test, min, max;
        ImageView imageView;
        Onitemclick onitemclick;

        public viewholder(@NonNull View itemView, Onitemclick onitemclick) {

            super(itemView);

            this.onitemclick = onitemclick;
            temp = itemView.findViewById(R.id.temp);
            test = itemView.findViewById(R.id.test);
            min = itemView.findViewById(R.id.min_temp);
            max = itemView.findViewById(R.id.max_temp);
            imageView = itemView.findViewById(R.id.weathericon);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onitemclick.onclick(getAdapterPosition());
        }
    }

    public interface Onitemclick {
        void onclick(int p);
    }

}
