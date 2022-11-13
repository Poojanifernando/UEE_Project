package com.example.uee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.uee.R;
import com.example.uee.adapters.AddScheduleAdapter;
import com.example.uee.databinding.ActivityAddScheduleBinding;
import com.example.uee.databinding.ActivityMainBinding;
import com.example.uee.db.FirebaseDB;
import com.example.uee.models.Schedule;
import com.example.uee.models.SelectedSchedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddSchedule extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddScheduleBinding addScheduleBinding;
    private ArrayList<Schedule> tempMatchingSchedule = new ArrayList<>();
    private String weight,height,bodyType;
    private String TAG = "Add_Sch_Activity";
    private AddScheduleAdapter adapter;
    private FirebaseDB dbUser;
    private SelectedSchedule tempAddSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        addScheduleBinding = ActivityAddScheduleBinding.inflate(getLayoutInflater());
        View view = addScheduleBinding.getRoot();
        setContentView(view);

        setUpUI();
    }


    @Override
    public void onClick(View view) {

        if(addScheduleBinding.appBtnBack == view){
            onBackPressed();

        }else if(addScheduleBinding.appBtnAddSch == view){
            insertData();
        }
    }

    private void setUpUI(){

        getData();
        dbUser = new FirebaseDB();
        addScheduleBinding.appBtnAddSch.setOnClickListener(this);
        addScheduleBinding.appBtnBack.setOnClickListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        addScheduleBinding.rclSelectSch.setLayoutManager(layoutManager);
        adapter = new AddScheduleAdapter(this,tempMatchingSchedule);
        addScheduleBinding.rclSelectSch.setAdapter(adapter);

    }

    private void getData(){

        Intent i = getIntent();
        tempMatchingSchedule = i.getParcelableArrayListExtra("matchSchedule");
        height = i.getStringExtra("height");
        weight = i.getStringExtra("weight");
        bodyType = i.getStringExtra("bodyType");

        setUpRecycler(tempMatchingSchedule,weight,height,bodyType);

    }

    private void setUpRecycler(ArrayList<Schedule> tempMatchingSchedule, String weight, String height, String bodyType) {

        if(tempMatchingSchedule.size() > 0){


            addScheduleBinding.txViewNoData.setVisibility(View.GONE);
            addScheduleBinding.rclSelectSch.setVisibility(View.VISIBLE);
            addScheduleBinding.appBtnBack.setVisibility(View.VISIBLE);
            addScheduleBinding.appBtnAddSch.setVisibility(View.VISIBLE);

            Log.e(TAG, "setUpRecycler: "+tempMatchingSchedule.get(0).getDay());
            inserting(tempMatchingSchedule,weight,height,bodyType);

        }else{
            addScheduleBinding.rclSelectSch.setVisibility(View.GONE);
            addScheduleBinding.appBtnBack.setVisibility(View.GONE);
            addScheduleBinding.appBtnAddSch.setVisibility(View.GONE);
            addScheduleBinding.txViewNoData.setVisibility(View.VISIBLE);
        }
    }

    private void inserting(ArrayList<Schedule> tempMatchingSchedule, String weight, String height, String bodyType) {

        tempAddSchedule = new SelectedSchedule(weight,height,bodyType,tempMatchingSchedule);

    }

    private void insertData() {

        //replace userID here
        dbUser.insertValue(tempAddSchedule,"user1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AddSchedule.this, "New entry created successfully", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onComplete: "+task);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddSchedule.this, "New entry creating failed", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onComplete: "+e.getMessage());
            }
        });

    }
}