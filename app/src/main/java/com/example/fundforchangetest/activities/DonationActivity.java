package com.example.fundforchangetest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.activities.user.eventRequest.pendingDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class DonationActivity extends AppCompatActivity {
    RadioButton rb1, rb2, rb3, rb4, rb5;
    String prioramount, getID;
    int amount;
    Button backfrmdonate, continuedonation;
    TextInputLayout donationamount;
    Model ob;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_donate);
        getID = getIntent().getStringExtra("DonationID");

        donationamount = (TextInputLayout) findViewById(R.id.DonationAmount);
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

    public void continuedonation() {
        amount = Integer.parseInt(donationamount.getEditText().getText().toString());
        db.collection("event").document(getID).update("donated", FieldValue.increment(amount))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DonationActivity.this, "Donation Successful", Toast.LENGTH_SHORT).show();
                        donationamount.getEditText().setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(DonationActivity.this,"Donation Failed! Please Try Again",Toast.LENGTH_SHORT);
                        donationamount.getEditText().setText("");
                    }
                });
    }

    public void backfrmdonate() {
        Intent intent = new Intent(DonationActivity.this, home.class);
        startActivity(intent);
    }
}
