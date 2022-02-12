package com.example.fundforchangetest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class detailsActivity extends AppCompatActivity {


    TextView t1,t2,t3, t4, t5, t6;
    Button back_btn, donate_btn;
    Model ob;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        t1 = (TextView) findViewById(R.id.event_name);
        t2 = (TextView) findViewById(R.id.event_desc);
        t3 = (TextView) findViewById(R.id.event_location);
        t4 = (TextView) findViewById(R.id.event_goal);
        t5 = (TextView) findViewById(R.id.event_email);
        t6 = (TextView) findViewById(R.id.event_phone);


        back_btn = findViewById(R.id.view_back_btn);
        donate_btn = findViewById(R.id.view_donate_btn);
        ob = (Model) getIntent().getSerializableExtra("product");


        // set all attributes

        t1.setText(ob.getName());
        t2.setText(ob.getDescription());
        t3.setText(ob.getLocation());
        t4.setText(String.valueOf(ob.getGoal()));
        t5.setText(ob.getEmail());
        t6.setText(ob.getPhone());


//        donate_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {donate();}
//        });
//
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {goBack();}
//        });


    }



}