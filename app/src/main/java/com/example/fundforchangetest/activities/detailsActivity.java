package com.example.fundforchangetest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;

public class detailsActivity extends AppCompatActivity {


    TextView t1,t2,t3;
    Button btn;
    Model ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        t1 = findViewById(R.id.user_txt);
        t2 = findViewById(R.id.email_txt);
        t3 = findViewById(R.id.tv);
        btn = findViewById(R.id.btn_back);
        ob = (Model) getIntent().getSerializableExtra("product");


        t1.setText(ob.getName());
        t2.setText(ob.getDescription());
    }
}