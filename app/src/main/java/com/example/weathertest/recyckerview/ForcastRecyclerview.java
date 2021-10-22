package com.example.weathertest.recyckerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.model.forcast_model;
import com.example.weathertest.util.sharepreferenced_setting;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForcastRecyclerview extends RecyclerView.Adapter<ForcastRecyclerview.viewholder> {

    List<forcast_model> list;
    sharepreferenced_setting sharepreferenced_setting;
    private final forcastclicklistner monitemclick;
    

    public ForcastRecyclerview(List<forcast_model> list, forcastclicklistner forcastclicklistner, Context context) {
        this.list = list;
        this.monitemclick = forcastclicklistner;
        sharepreferenced_setting = new sharepreferenced_setting(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forcast_recyclerview_layout, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitemclick.onclick(holder.getAdapterPosition());
            }
        });

        if(position==0){
            holder.test.setText("Tomorrow");
        } else {
            holder.test.setText(list.get(position).time);
        }

        holder.temp.setText(Math.round(Double.parseDouble(list.get(position).temp)) + " "+sharepreferenced_setting.getsymbol());
        holder.min.setText(Math.round(Double.parseDouble(list.get(position).min)) + " "+sharepreferenced_setting.getsymbol());
        holder.max.setText(Math.round(Double.parseDouble(list.get(position).max)) + " "+sharepreferenced_setting.getsymbol());
        Picasso.get().load(list.get(position).icon).fit().into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        TextView temp, test, min, max;
        ImageView imageView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            temp = itemView.findViewById(R.id.temp);
            test = itemView.findViewById(R.id.test);
            min = itemView.findViewById(R.id.min_temp);
            max = itemView.findViewById(R.id.max_temp);
            imageView = itemView.findViewById(R.id.weathericon);

        }
    }

    public interface forcastclicklistner {
        void onclick(int p);
    }

}
