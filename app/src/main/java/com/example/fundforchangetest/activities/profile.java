package com.example.fundforchangetest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fundforchangetest.R;
import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity {
    TextView email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.uid);

        email.setText(getIntent().getStringExtra("email"));
        pass.setText(getIntent().getStringExtra("uid"));

        FirebaseAuth.getInstance().signOut();

    }
}
