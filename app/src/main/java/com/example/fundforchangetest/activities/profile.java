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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class profile extends AppCompatActivity {
    TextView email, name, Name, phone, role;
    Button logOut;
    String mail;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email);
        logOut = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        Name = findViewById(R.id.NAME);
        phone = findViewById(R.id.phone);
        role = findViewById(R.id.role);




        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);
        mail = sp.getString("email","");
        email.setText(mail);


        SharedPreferences sp3 = getSharedPreferences("datafile3",MODE_PRIVATE);




        setProfileDetails();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.contains("email")){
                    SharedPreferences.Editor ed = sp.edit();
                    ed.remove("email");
                    ed.commit();

                    SharedPreferences.Editor ed3 = sp3.edit();
                    ed3.remove("role");
                    ed3.commit();

                    Intent intent  = new Intent(getApplicationContext(), home.class);
                    startActivity(intent);
                }

            }
        });


    }

    private void setProfileDetails() {

        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);

        db.collection("users").whereEqualTo("email", sp.getString("email",""))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d : list){

                            Name.setText(d.getString("name"));
                            name.setText(d.getString("name"));
                            phone.setText(d.getString("phone"));
                            role.setText(d.getString("role"));

                        }


                    }
                });


    }
}
