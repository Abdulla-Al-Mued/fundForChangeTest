package com.example.fundforchangetest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.activities.user.eventRequest.pendingDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class detailsActivity extends AppCompatActivity {


    TextView t1,t2,t3, t4, t5, t6, t7;
    Button back_btn, donate_btn, deleteBtn;
    Model ob;
    String EventName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        t1 = (TextView) findViewById(R.id.event_name);
        t2 = (TextView) findViewById(R.id.event_desc);
        t3 = (TextView) findViewById(R.id.event_location);
        t4 = (TextView) findViewById(R.id.event_goal);
        t5 = (TextView) findViewById(R.id.total_donation);
        t6 = (TextView) findViewById(R.id.event_email);
        t7 = (TextView) findViewById(R.id.event_phone);
        deleteBtn = findViewById(R.id.remove_btn);


        back_btn = findViewById(R.id.view_back_btn);
        donate_btn = findViewById(R.id.view_donate_btn);
        ob = (Model) getIntent().getSerializableExtra("product");


        // set all attributes

        t1.setText(ob.getName());
        t2.setText(ob.getDescription());
        t3.setText(ob.getLocation());
        t4.setText(String.valueOf(ob.getGoal()));
        t5.setText(String.valueOf(ob.getDonated()));
        t6.setText(ob.getEmail());
        t7.setText(ob.getPhone());

        EventName = ob.getName();


        SharedPreferences sp3 = getSharedPreferences("datafile3",MODE_PRIVATE);
        String role = sp3.getString("role","");

        if(role.equals("admin")){
            deleteBtn.setVisibility(View.VISIBLE);

        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });


        donate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {donation();}
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {goBack();}
        });


    }

    public void donation() {
        Intent intent = new Intent(this, DonationActivity.class);
        intent.putExtra("DonationID", ob.getId());
        startActivity(intent);
    }

    public void goBack() {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void deleteEvent(){
        db.collection("event").document(ob.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(detailsActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(detailsActivity.this, home.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(detailsActivity.this, "event deletion is not successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(detailsActivity.this, home.class);
                        startActivity(intent);

                    }
                });
    }



}