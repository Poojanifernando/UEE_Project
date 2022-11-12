package com.example.uee.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uee.databinding.ItemScheduleBinding;
import com.example.uee.models.Schedule;

import java.util.ArrayList;

public class AddScheduleAdapter extends RecyclerView.Adapter<AddScheduleAdapter.ViewHolder> {

    private ArrayList<Schedule> scheduleArrayList;
    private Context context;
    private onItemClickListner mlistner;
    public static ItemScheduleBinding binding;

    public AddScheduleAdapter(Context context,ArrayList<Schedule> scheduleList) {
        this.context = context;
        this.scheduleArrayList = scheduleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        ItemScheduleBinding view= ItemScheduleBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AddScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AddScheduleAdapter.ViewHolder holder, int position) {

        Schedule schedule = scheduleArrayList.get(position);

        binding.txViewSchDay.setText("Day "+schedule.getDay());
        binding.txViewSchPress.setText(schedule.getPress()+" Push ups");
        binding.txViewSchPushups.setText(schedule.getPushUps()+" Squats");
        binding.txViewSchSquats.setText(schedule.getSquats()+ " minute glute bridge");
        binding.txViewSchBridge.setText(schedule.getBridge()+ " weighted dumbell press");

    }

    @Override
    public int getItemCount() {

        return scheduleArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull ItemScheduleBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }



    }

    public interface onItemClickListner{
        void onCardClick(int position);
    }

    public void setOnItemClickListner(onItemClickListner listner){
        mlistner = listner;
    }
}
