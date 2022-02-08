package com.example.fundforchangetest.activities.user.eventRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;

public class pendingDetails extends AppCompatActivity {

    TextView t1,t2,t3, t4, t5, t6, t7;
    Button acBtn,rejBtn;
    pendingModel ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_details);


        t1 = (TextView) findViewById(R.id.event_name);
        t2 = (TextView) findViewById(R.id.event_desc);
        t3 = (TextView) findViewById(R.id.event_location);
        t4 = (TextView) findViewById(R.id.event_goal);
        t5 = (TextView) findViewById(R.id.event_email);
        t6 = (TextView) findViewById(R.id.event_phone);
        t7 = (TextView) findViewById(R.id.event_nid);


        acBtn = findViewById(R.id.accept_btn);
        rejBtn = findViewById(R.id.reject_btn);
        ob = (pendingModel) getIntent().getSerializableExtra("product");




        //t1.setText(getIntent().getStringExtra("name"));
        t1.setText(ob.getName());
        t2.setText(ob.getDescription());
        t3.setText(ob.getLocation());
        t4.setText(ob.getGoal());
        t5.setText(ob.getEmail());
        t6.setText(ob.getPhone());
        t7.setText(ob.getNID());
    }
}