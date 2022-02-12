package com.example.fundforchangetest.activities.user.eventRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class pendingDetails extends AppCompatActivity {

    TextView t1,t2,t3, t4, t5, t6, t7;
    Button acBtn,rejBtn;
    pendingModel ob;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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


        // set all attributes
        t1.setText(ob.getName());
        t2.setText(ob.getDescription());
        t3.setText(ob.getLocation());
        t4.setText(String.valueOf(ob.getGoal()));
        t5.setText(ob.getEmail());
        t6.setText(ob.getPhone());
        t7.setText(ob.getNID());


        acBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               acceptEvent();
            }
        });

        rejBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectEvent();
            }
        });


    }


    public void acceptEvent(){

        db.collection("event").document(ob.getId()).update("status","accepted")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(pendingDetails.this, "The event is added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(pendingDetails.this, UserMainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pendingDetails.this,"Error Accepting Request",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(pendingDetails.this, UserMainActivity.class);
                        startActivity(intent);
                    }
                });

    }

    public void rejectEvent(){
        db.collection("event").document(ob.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(pendingDetails.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(pendingDetails.this, UserMainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(pendingDetails.this, "event deletion is not successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(pendingDetails.this, UserMainActivity.class);
                        startActivity(intent);

                    }
                });
    }
}