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
import com.example.weathertest.model.recyclerview_item;
import com.example.weathertest.util.sharepreferenced_setting;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForcastRecyclerview extends RecyclerView.Adapter<ForcastRecyclerview.viewholder> {

    List<recyclerview_item> list;
    sharepreferenced_setting sharepreferenced_setting;
    private forcastclicklistner monitemclick;
    

    public ForcastRecyclerview(List<recyclerview_item> list, forcastclicklistner forcastclicklistner, Context context) {
        this.list = list;
        this.monitemclick = forcastclicklistner;
        sharepreferenced_setting = new sharepreferenced_setting(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitemclick.onclick(holder.getAdapterPosition());
            }
        });

        String units;
        if(position==0){
            holder.test.setText("Tomorrow");
        } else {
            holder.test.setText(list.get(position).time);
        }
        if (sharepreferenced_setting.temp_symbol().equals("M")){
            units="\u2103";
        } else {
            units="\u2109";
        }
        holder.temp.setText(Math.round(Double.parseDouble(list.get(position).temp)) + " "+units);
        holder.min.setText(Math.round(Double.parseDouble(list.get(position).min)) + " "+units);
        holder.max.setText(Math.round(Double.parseDouble(list.get(position).max)) + " "+units);
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
