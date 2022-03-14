package com.example.fundforchangetest.activities.user.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.userModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.activities.user.eventRequest.pendingDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class userDetails extends AppCompatActivity {

    TextView Name, name, role, email, phone;
    Button mAdmin, remove;
    userModel ob;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        Name = (TextView) findViewById(R.id.NAME);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        role = (TextView) findViewById(R.id.role);
        phone = (TextView) findViewById(R.id.phone);

        mAdmin = findViewById(R.id.make_admin);
        remove = findViewById(R.id.remove_member);

        ob = (userModel) getIntent().getSerializableExtra("product");

        Name.setText(ob.getName());
        name.setText(ob.getName());
        role.setText("user");
        phone.setText(ob.getPhone());
        email.setText(ob.getEmail());


        mAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").document(ob.getId()).update("role","admin")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(userDetails.this, ob.getName()+" is admin now", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(userDetails.this, UserMainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(userDetails.this,"Error",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(userDetails.this, UserMainActivity.class);
                                startActivity(intent);
                            }
                        });
            }

        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.collection("users").document(ob.getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(userDetails.this, ob.getName()+" Member Removed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(userDetails.this, UserMainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(userDetails.this, "Error removing member", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(userDetails.this, UserMainActivity.class);
                                startActivity(intent);

                            }
                        });


            }
        });




    }
}