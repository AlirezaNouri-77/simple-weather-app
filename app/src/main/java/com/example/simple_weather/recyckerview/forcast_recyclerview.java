package com.example.simple_weather.recyckerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simple_weather.R;
import com.example.simple_weather.model.forcast_model;
import com.example.simple_weather.util.Get_Weekname_FromDate;
import com.example.simple_weather.util.My_Sharepreferenced;

import java.text.ParseException;
import java.util.List;

public class forcast_recyclerview extends RecyclerView.Adapter<forcast_recyclerview.viewholder> {

    Context context;
    List<forcast_model> list;
    My_Sharepreferenced sharepreferenced;
    private final forcastclicklistner monitemclick;


    public forcast_recyclerview(List<forcast_model> list, forcastclicklistner forcastclicklistner, Context context) {
        this.list = list;
        this.context = context;
        this.monitemclick = forcastclicklistner;
        sharepreferenced = new My_Sharepreferenced(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_forcast_layout, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitemclick.sixteenday_forcast_onclick(holder.getAdapterPosition());
            }
        });

        if (position == 0) {
            holder.time.setText("Tomorrow");
        } else if (position <= 7) {
            try {
                Get_Weekname_FromDate get_weekname_fromDate = new Get_Weekname_FromDate();
                holder.time.setText(get_weekname_fromDate.get_week_name(list.get(position).time));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            holder.time.setText(list.get(position).time);
        }

        holder.temp.setText(String.valueOf(Math.round(Double.parseDouble(list.get(position).getTemp())) + " " + sharepreferenced.getsymbol()));
        holder.max_min_temp.setText(Math.round(Double.parseDouble(list.get(position).getMax())) + "\u00B0" + "/" + Math.round(Double.parseDouble(list.get(position).getMin())) + "\u00B0" );
        holder.rain.setText(list.get(position).rainpossibilty + "%");
        Glide.with(context).load(list.get(position).icon).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {

        TextView temp, time, max_min_temp, rain;
        ImageView imageView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            temp = itemView.findViewById(R.id.temp);
            time = itemView.findViewById(R.id.test);
            max_min_temp = itemView.findViewById(R.id.min_max_temp);
            rain = itemView.findViewById(R.id.rain);
            imageView = itemView.findViewById(R.id.weathericon);

        }
    }

    public interface forcastclicklistner {
        void sixteenday_forcast_onclick(int p);
    }

}
