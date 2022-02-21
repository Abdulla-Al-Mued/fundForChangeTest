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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class pendingDetails extends AppCompatActivity {

    TextView t1,t2,t3, t4, t5, t6, t7, u1, u2, u3, u4, role;
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

        u1 = findViewById(R.id.userNameHead);
        u2 = findViewById(R.id.user_name);
        u3 = findViewById(R.id.user_email);
        u4 = findViewById(R.id.user_phone);
        role = findViewById(R.id.role);


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

        // set user information

        setUserAttribute();


        // end
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

    private void setUserAttribute() {

        db.collection("users").whereEqualTo("email",ob.getuEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d : list){

                            u1.setText(d.getString("name"));
                            u2.setText(d.getString("name"));
                            u3.setText(d.getString("email"));
                            u4.setText(d.getString("phone"));
                            role.setText(d.getString("role"));

                        }


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