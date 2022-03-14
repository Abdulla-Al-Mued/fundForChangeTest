package com.example.fundforchangetest.activities.user.finishEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.finishEventModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.activities.user.eventRequest.pendingDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class finishEventDetails extends AppCompatActivity {

    TextView t1,t2,t3, t4, t5, t6, t7;
    finishEventModel ob;
    Button finish, delete;
    FirebaseFirestore db  = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_event_details);


        t1 = (TextView) findViewById(R.id.event_name);
        t2 = (TextView) findViewById(R.id.event_desc);
        t3 = (TextView) findViewById(R.id.event_location);
        t4 = (TextView) findViewById(R.id.event_goal);
        t5 = (TextView) findViewById(R.id.total_donation);
        t6 = (TextView) findViewById(R.id.event_email);
        t7 = (TextView) findViewById(R.id.event_phone);
        finish = findViewById(R.id.finish_btn);
        delete = findViewById(R.id.delete_btn);


        ob = (finishEventModel) getIntent().getSerializableExtra("product");

        t1.setText(ob.getName());
        t2.setText(ob.getDescription());
        t3.setText(ob.getLocation());
        t4.setText(String.valueOf(ob.getGoal()));
        t5.setText(String.valueOf(ob.getDonated()));
        t6.setText(ob.getEmail());
        t7.setText(ob.getPhone());


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.collection("event").document(ob.getId()).update("status","finish")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(finishEventDetails.this, "The event is finished successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(finishEventDetails.this, UserMainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(finishEventDetails.this,"Error finishing event",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(finishEventDetails.this, UserMainActivity.class);
                                startActivity(intent);
                            }
                        });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("event").document(ob.getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(finishEventDetails.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(finishEventDetails.this, UserMainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(finishEventDetails.this, "event deletion is not successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(finishEventDetails.this, UserMainActivity.class);
                                startActivity(intent);

                            }
                        });
            }
        });


    }
}