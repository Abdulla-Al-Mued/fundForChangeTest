package com.example.fundforchangetest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.activities.user.eventRequest.pendingDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DonationActivity extends AppCompatActivity {
    RadioButton rb1, rb2, rb3;
    RadioGroup rg;
    String prioramount, getID, donatorName, donatorPhone, donatorEmail, Account;
    int amount;
    Button backfrmdonate, continuedonation;
    TextInputLayout t1, t2, t3, t4;
    CheckBox cb;
    Model ob;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_donate);
        getID = getIntent().getStringExtra("DonationID");

        t1 = findViewById(R.id.DonatorName);
        t2 = findViewById(R.id.DonatorPhone);
        t3 = findViewById(R.id.DonatorEmail);
        cb = findViewById(R.id.anonymousdonatecheck);
//        rb1 = findViewById(R.id.bkash);
//        rb2 = findViewById(R.id.nogod);
//        rb3 = findViewById(R.id.rocket);
        rg = findViewById(R.id.radiogrp);
        t4 = (TextInputLayout) findViewById(R.id.DonationAmount);
        backfrmdonate = findViewById(R.id.backfrmdonate);
        continuedonation = findViewById(R.id.continuedonate);
        //ob = (Model) getIntent().getSerializableExtra("product");

         //prioramount = donationamount.getEditText().getText().toString();
        //Log.d("input value", "value of amount: " +prioramount);





        backfrmdonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backfrmdonate();
            }
        });

        continuedonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuedonation();
            }
        });
    }

    public void insert() {

        donatorName = t1.getEditText().getText().toString().trim();
        donatorPhone = t2.getEditText().getText().toString().trim();
        donatorEmail = t3.getEditText().getText().toString().trim();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.bkash:
                        Account = "Bkash";
                        break;
                    case R.id.rocket:
                        Account = "Rocket";
                        break;
                    case R.id.nogod:
                        Account = "Nogod";
                        break;
                    default:
                        Account = "No Account type provided";
                }
            }
        });

        if(cb.isChecked()) {
            Map<String, Object> items = new HashMap<>();

            items.put("Name", "Anonymous");
            items.put("Phone No.", "Anonymous");
            items.put("Email", "Anonymous");
            items.put("Account Type", Account);
            items.put("Donated To", getID);
            items.put("Amount", amount);

            db.collection("Donations").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DonationActivity.this, "Thank you for anonymous donation", Toast.LENGTH_SHORT).show();
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DonationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");
                        }
                    });
        }
        else {
            Map<String, Object> items = new HashMap<>();

            items.put("Name", donatorName);
            items.put("Phone No.", donatorPhone);
            items.put("Email", donatorEmail);
            items.put("Account Type", Account);
            items.put("Donated To", getID);
            items.put("Amount", amount);

            db.collection("Donations").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DonationActivity.this, "Your Info Saved Successfully", Toast.LENGTH_SHORT).show();
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DonationActivity.this, "Something Went Wrong Saving Your Data", Toast.LENGTH_SHORT).show();
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");
                        }
                    });
        }



    }

    public void continuedonation() {
        amount = Integer.parseInt(t4.getEditText().getText().toString());
        db.collection("event").document(getID).update("donated", FieldValue.increment(amount))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DonationActivity.this, "Donation Successful", Toast.LENGTH_SHORT).show();
                        t4.getEditText().setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DonationActivity.this,"Donation Failed! Please Try Again",Toast.LENGTH_SHORT);
                        t4.getEditText().setText("");
                    }
                });

        insert();


    }

    public void backfrmdonate() {
        Intent intent = new Intent(DonationActivity.this, home.class);
        startActivity(intent);
    }
}
