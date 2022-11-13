package com.example.uee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.uee.adapters.ViewScheduleAdapter;
import com.example.uee.databinding.ActivityViewScheduleBinding;
import com.example.uee.databinding.ItemScheduleEditBinding;
import com.example.uee.db.FirebaseDB;
import com.example.uee.models.Schedule;
import com.example.uee.models.SelectedSchedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewSchedule extends AppCompatActivity implements View.OnClickListener {

    private ActivityViewScheduleBinding viewScheduleBinding;
    private FirebaseDB dbUser;
    private ArrayList<Schedule> tempMatchingSchedule = new ArrayList<>();
    private ViewScheduleAdapter adapter;
    private String TAG = "View_Schedule";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewScheduleBinding = ActivityViewScheduleBinding.inflate(getLayoutInflater());
        View view = viewScheduleBinding.getRoot();
        setContentView(view);

        setupUI();
    }

    @Override
    public void onClick(View view) {

        if(viewScheduleBinding.appBtnEditSch == view){

            //updateValue();
        }
        else if(viewScheduleBinding.appBtnBack == view){

            onBackPressed();

        }
        else if(viewScheduleBinding.appBtnRemoveSch == view){

            deleteEntry();
        }

    }

    private void setupUI(){

        dbUser = new FirebaseDB();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        viewScheduleBinding.rclSelectSch.setLayoutManager(layoutManager);

        viewScheduleBinding.appBtnBack.setOnClickListener(this);
        viewScheduleBinding.appBtnRemoveSch.setOnClickListener(this);
        viewScheduleBinding.appBtnEditSch.setOnClickListener(this);


        getData();

    }

    private void getData(){

        //replace your user ID here to retrieve data
        dbUser.readValues("user1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    SelectedSchedule schedule = data.getValue(SelectedSchedule.class);

                    if(schedule != null){

                        setupRecycler(schedule);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupRecycler(SelectedSchedule tempObject){

        if(tempObject.getSchedules().size() == 0){

            viewScheduleBinding.txViewNoData.setVisibility(View.VISIBLE);
        }

        else {

            ArrayList<Schedule> tempSchedule = tempObject.getSchedules();
            tempMatchingSchedule.addAll(tempSchedule);
            adapter = new ViewScheduleAdapter(this, tempMatchingSchedule);
            viewScheduleBinding.rclSelectSch.setAdapter(adapter);

            viewScheduleBinding.rclSelectSch.setVisibility(View.VISIBLE);
            viewScheduleBinding.appBtnRemoveSch.setVisibility(View.VISIBLE);
            viewScheduleBinding.appBtnEditSch.setVisibility(View.GONE);

            updateValue();

        }
        viewScheduleBinding.progressBarViewer.setVisibility(View.GONE);
        viewScheduleBinding.appBtnBack.setVisibility(View.VISIBLE);
    }

    private void deleteEntry() {

        //update userID for key
        SweetAlertDialog pdialog = new SweetAlertDialog(ViewSchedule.this, SweetAlertDialog.WARNING_TYPE);
        pdialog.setTitleText("Delete Entry");
        pdialog.setContentText("Are you sure ?");
        pdialog.setCancelText("No");
        pdialog.setConfirmText("Yes");
        pdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                dbUser.removeValue("SelectedSchedule","user1").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        pdialog.cancel();
                        deleteSuccess();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                pdialog.cancel();
            }
        });
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.show();
    }

    private void deleteSuccess() {

        Toast.makeText(this, "Entry Deleted Successful", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    private void updateValue(){

        adapter.setOnItemClickListner(new ViewScheduleAdapter.onItemClickListner() {
            @Override
            public void onCardClick(int position) {

                Schedule schedule = tempMatchingSchedule.get(position);

                 updateScreen(schedule,position);
                Log.e(TAG, "onCardClick: "+schedule.getDay());

            }
        });
    }

    private void updateScreen(Schedule schedule,int position) {

        AlertDialog builder = new AlertDialog.Builder(this).create();
        ItemScheduleEditBinding view= ItemScheduleEditBinding.inflate(LayoutInflater.from(this));
        builder.setView(view.getRoot());
        builder.setTitle("Update Schedule");

        view.edTxtSchBridge.setText(String.valueOf(schedule.getBridge()));
        view.edTxtSchPush.setText(String.valueOf(schedule.getPushUps()));
        view.edTxtViewSchDay.setText(String.valueOf(schedule.getDay()));
        view.edTxtSchSquats.setText(String.valueOf(schedule.getSquats()));
        view.edTxtViewSchPress.setText(String.valueOf(schedule.getPress()));

        String tempPush =   view.edTxtSchPush.getEditableText().toString();
        String tempSquats =   view.edTxtSchSquats.getEditableText().toString();
        String tempBridge=   view.edTxtSchBridge.getEditableText().toString();
        String tempPress =   view.edTxtViewSchPress.getEditableText().toString();


        view.appBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.dismiss();
            }
        });

        view.appBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tempPush.isEmpty() || tempSquats.isEmpty() || tempBridge.isEmpty() || tempPress.isEmpty()){

                    Toast.makeText(ViewSchedule.this, "Please fill all the schedule details", Toast.LENGTH_SHORT).show();
                }else{

                    int tempPushNumber =   Integer.parseInt(view.edTxtSchPush.getEditableText().toString());
                    int tempSquatsNumber  =   Integer.parseInt(view.edTxtSchSquats.getEditableText().toString());
                    int tempBridgeNumber =   Integer.parseInt(view.edTxtSchBridge.getEditableText().toString());
                    int tempPressNumber  =   Integer.parseInt(view.edTxtViewSchPress.getEditableText().toString());

                    Log.e(TAG, "onClick: "+tempPushNumber);
                    Schedule object =
                            new Schedule(
                            schedule.getDay(),
                            tempPushNumber,tempSquatsNumber,
                            tempBridgeNumber,
                            tempPressNumber,
                            schedule.getBodyType());

                    //update the userID here
                    dbUser.updateValues(object,
                                    "SelectedSchedule",
                                    "user1",
                                    "schedules",
                                    String.valueOf(position)).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(ViewSchedule.this, "Schedule Update Success ", Toast.LENGTH_SHORT).show();
                            refreshData();
                            builder.cancel();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ViewSchedule.this, "Schedule Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        builder.show();
    }

    private void refreshRecycler() {

        refreshData();
    }

    private void refreshData() {
        tempMatchingSchedule.clear();

        //replace your user ID here to retrieve data
        dbUser.readUpdatedValues("SelectedSchedule","user1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    SelectedSchedule schedule = data.getValue(SelectedSchedule.class);

                    if(schedule != null){

                        adapter.updateData(schedule.getSchedules());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}