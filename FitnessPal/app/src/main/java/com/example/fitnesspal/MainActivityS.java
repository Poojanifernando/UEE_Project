package com.example.uee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.uee.databinding.ActivityMainBinding;
import com.example.uee.models.Schedule;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mainBinding;
    private final ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    private final ArrayList<Schedule> tempMatchingSchedule = new ArrayList<>();
    private final String TAG = "Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bind using viewBinding
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        setUpUI();
    }

    @Override
    public void onClick(View view) {

        if(view == mainBinding.appBtnGetSch){

            getUserInput();
        }
        else if(view == mainBinding.appBtnViewSch){

            Intent i = new Intent(this,ViewSchedule.class);
            startActivity(i);
        }
    }

    private void setUpUI(){

        mainBinding.appBtnGetSch.setOnClickListener(this);
        mainBinding.appBtnViewSch.setOnClickListener(this);
    }

    private void getUserInput(){


        String weight = mainBinding.edTxtWeight.getEditableText().toString().trim();
        String height = mainBinding.edTxtHeight.getEditableText().toString().trim();
        String bodyType = mainBinding.spinnerBodyType.getSelectedItem().toString();
        int weightNumber = 0;

        if(weight.isEmpty()){
            Toast.makeText(this, "Please enter weight", Toast.LENGTH_SHORT).show();

        }
        else if(height.isEmpty()){
            Toast.makeText(this, "Please enter height", Toast.LENGTH_SHORT).show();
        }
        else if(bodyType.isEmpty()){
            Toast.makeText(this, "Please select body type", Toast.LENGTH_SHORT).show();
        }
        else{

            try {
                weightNumber = Integer.parseInt(mainBinding.edTxtWeight.getEditableText().toString());


            }catch (Exception e){

                Log.d(TAG, "getUserInput:WeightCastError: "+e.getMessage());
            }

            getSchedule(weight,height,bodyType,weightNumber);
        }

    }

    private void getSchedule(String weight, String height, String bodyType, int weightNumber) {

            //get schedule according to bodyType
            int tempSize =  enterScheduleData().size();
            Schedule tempObject;
            int tempBodyType = checkBodyType(weightNumber);

            for (int i=0; i < tempSize; i++){

                tempObject = enterScheduleData().get(i);

                if(tempObject.getBodyType() == tempBodyType){

                    tempMatchingSchedule.add(tempObject);
                }
            }

        displayMatchedSchedule(weight,height,bodyType,tempMatchingSchedule);

    }

    private int checkBodyType(int weightNumber) {
        int tempBodyType = 0;

        if(weightNumber < 50){

            tempBodyType = 1;
        }
        else if(weightNumber > 50 && weightNumber <= 80){

            tempBodyType = 2;
        }
        else if(weightNumber > 80){

            tempBodyType = 3;
        }

        return tempBodyType;

    }

    private ArrayList<Schedule> enterScheduleData(){

        //body type 1-slim , 2-normal , 3-high
        //define for body types less than 50kg
        scheduleArrayList.add(new Schedule(1,10,20,1,10,1));
        scheduleArrayList.add(new Schedule(2,20,30,2,20,1));

        //define for body types between 50-80kg
        scheduleArrayList.add(new Schedule(1,20,25,2,15,2));
        scheduleArrayList.add(new Schedule(2,30,35,3,25,2));

        //define for body types more than 80kg
        scheduleArrayList.add(new Schedule(1,25,30,5,30,3));
        scheduleArrayList.add(new Schedule(2,40,40,5,35,3));

        return scheduleArrayList;
    }

    private void displayMatchedSchedule(String weight, String height, String bodyType, ArrayList<Schedule> tempMatchingSchedule) {

        Intent i = new Intent(MainActivity.this,AddSchedule.class);
        i.putParcelableArrayListExtra("matchSchedule",( ArrayList<Schedule>)tempMatchingSchedule);
        i.putExtra("weight",weight);
        i.putExtra("height",height);
        i.putExtra("bodyType",bodyType);
        startActivity(i);
    }

}