package com.example.uee.db;

import com.example.uee.models.Schedule;
import com.example.uee.models.SelectedSchedule;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseDB {

    private DatabaseReference databaseReference;


    public FirebaseDB() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(SelectedSchedule.class.getSimpleName());

    }

    public Task<Void> insertValue(SelectedSchedule selectedSchedule,String userID){

        return databaseReference.child(userID).setValue(selectedSchedule);
    }

    public Query readValues(String key){

        return databaseReference.orderByKey();
    }

    public Query readUpdatedValues(String ref,String userID){

        databaseReference = FirebaseDatabase.getInstance().getReference(ref);
        return databaseReference;
    }

    public Task<Void> removeValue(String ref,String key){

        databaseReference = FirebaseDatabase.getInstance().getReference(ref).child(key);
        return databaseReference.removeValue() ;
    }

    public Task<Void> updateValues(Object object, String ref, String usreID, String objectName, String position){

        //change according to your path
        databaseReference = FirebaseDatabase.getInstance().getReference(ref).child(usreID).child(objectName).child(position);

        return databaseReference.setValue(object);
    }


}


