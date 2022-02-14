package com.example.fundforchangetest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity {
    TextView email, pass;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.uid);
        logOut = findViewById(R.id.logout);


        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);
        email.setText(sp.getString("email",""));

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.contains("email")){
                    SharedPreferences.Editor ed = sp.edit();
                    ed.remove("email");
                    ed.commit();

            Intent intent  = new Intent(getApplicationContext(), home.class);
            startActivity(intent);
                }

            }
        });


    }
}
