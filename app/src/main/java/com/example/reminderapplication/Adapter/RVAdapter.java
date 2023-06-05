package com.example.reminderapplication.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapplication.R;
import com.example.reminderapplication.RDB.entity;

import java.util.Date;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    List<entity> data;

    public RVAdapter(List<entity> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        entity model=data.get(position);
        holder.ReminderDetails.setText(model.getReminderdetails());
        Date date=new Date(model.getAlaramtime());
        holder.time.setText(date.toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ReminderDetails,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ReminderDetails=itemView.findViewById(R.id.reminder_name);
            time=itemView.findViewById(R.id.timetv);

        }
    }
}
