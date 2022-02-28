package com.example.fundforchangetest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class detailsActivity extends AppCompatActivity {


    TextView t1,t2,t3, t4, t5, t6, t7;
    Button back_btn, donate_btn;
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



}