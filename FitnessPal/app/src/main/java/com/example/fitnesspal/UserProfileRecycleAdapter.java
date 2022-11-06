package com.example.uee_app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserProfileRecycleAdapter extends RecyclerView.Adapter<UserProfileRecycleAdapter.ViewHolder> {

    Context context;
    ArrayList<UserProfileEntity> userProfileEntityArrayList;
    DatabaseReference databaseReference;


    public UserProfileRecycleAdapter(Context context, ArrayList<UserProfileEntity> userProfileEntityArrayList) {
        this.context = context;
        this.userProfileEntityArrayList = userProfileEntityArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.userprofile_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        UserProfileEntity users = userProfileEntityArrayList.get(position);

        holder.textName.setText("Full Name : " + users.getName());
        holder.textAge.setText("Age : " + users.getAge());
        holder.textGender.setText("Gender : " + users.getGender());
        holder.textOccupation.setText("Occupation : " + users.getOccupation());
        holder.textEmail.setText("Email : " + users.getEmail());


        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, users.getId(),users.getName(), users.getAge(), users.getGender(), users.getOccupation(), users.getEmail());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context, users.getId());
            }
        });

    }



    @Override
    public int getItemCount() {

        return userProfileEntityArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        TextView textAge;
        TextView textGender;
        TextView textOccupation;
        TextView textEmail;


        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textName = itemView.findViewById(R.id.textName);
            textAge = itemView.findViewById(R.id.textAge);
            textGender = itemView.findViewById(R.id.textGender);
            textOccupation = itemView.findViewById(R.id.textOccupation);
            textEmail = itemView.findViewById(R.id.textEmail);


            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String name, String age, String gender, String occupation, String email) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_users);

            EditText textName = dialog.findViewById(R.id.textName);
            EditText textAge = dialog.findViewById(R.id.textAge);
            EditText textGender = dialog.findViewById(R.id.textGender);
            EditText textOccupation = dialog.findViewById(R.id.textOccupation);
            EditText textEmail = dialog.findViewById(R.id.textEmail);

            textName.setText(name);
            textAge.setText(age);
            textGender.setText(gender);
            textOccupation.setText(occupation);
            textEmail.setText(email);


            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }


            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newName = textName.getText().toString();
                    String newAge = textAge.getText().toString();
                    String newGender = textGender.getText().toString();
                    String newOccupation = textOccupation.getText().toString();
                    String newEmail = textEmail.getText().toString();


                    if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || occupation.isEmpty() || email.isEmpty()) {
                        Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newName.equals(name) && newAge.equals(age) && newGender.equals(gender)&& newOccupation.equals(occupation)&& newEmail.equals(email)) {
                            Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("USERS").child(id).setValue(new UserProfileEntity(id, newName, newAge, newGender,newOccupation,newEmail));
                            Toast.makeText(context, "Users Updated successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        }
    }

    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_delete);

            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseReference.child("USERS").child(id).removeValue();
                    Toast.makeText(context, "users Deleted successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}

