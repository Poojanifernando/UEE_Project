package com.example.uee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uee.databinding.ItemScheduleBinding;
import com.example.uee.databinding.ItemViewScheduleBinding;
import com.example.uee.models.Schedule;

import java.util.ArrayList;

public class ViewScheduleAdapter extends RecyclerView.Adapter<ViewScheduleAdapter.ViewHolder> {

    private ArrayList<Schedule> scheduleArrayList;
    private Context context;
    private onItemClickListner mlistner;
    public static ItemScheduleBinding binding;

    public ViewScheduleAdapter(Context context, ArrayList<Schedule> scheduleList) {
        this.context = context;
        this.scheduleArrayList = scheduleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        ItemScheduleBinding view= ItemScheduleBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewScheduleAdapter.ViewHolder holder, int position) {

        Schedule schedule = scheduleArrayList.get(position);

        binding.txViewSchDay.setText("Day "+schedule.getDay());
        binding.txViewSchPress.setText(schedule.getPress()+" Push ups");
        binding.txViewSchPushups.setText(schedule.getPushUps()+" Squats");
        binding.txViewSchSquats.setText(schedule.getSquats()+ " minute glute bridge");
        binding.txViewSchBridge.setText(schedule.getBridge()+ " weighted dumbell press");


        binding.appBtnEditSch.setVisibility(View.VISIBLE);
        binding.appBtnEditSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mlistner != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mlistner.onCardClick(position);

                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {

        return scheduleArrayList.size();
    }

    public void updateData(ArrayList<Schedule> schedules){
        scheduleArrayList.clear();
        scheduleArrayList.addAll(schedules);
        notifyDataSetChanged();
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
