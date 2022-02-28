package com.example.fundforchangetest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class updateProfile extends AppCompatActivity {

    TextInputLayout name, email, phone, uName;
    Button update;
    String n,un,e,p;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        name = findViewById(R.id.name);
        uName = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userPhone);

        update = findViewById(R.id.update);


        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);

        db.collection("users")
                .whereEqualTo("email",sp.getString("email",""))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d : list){


                            name.getEditText().setText(d.getString("name"));
                            phone.getEditText().setText(d.getString("phone"));
                            email.getEditText().setText(d.getString("email"));
                            uName.getEditText().setText(d.getString("user"));

                        }

                    }
                });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    public void update(){

        n = name.getEditText().getText().toString().trim();
        e = email.getEditText().getText().toString().trim();
        un = uName.getEditText().getText().toString().trim();
        p = phone.getEditText().getText().toString().trim();

        if(n.isEmpty()){
            name.setError("Empty Field");
            return;
        }
        if(e.isEmpty()){
            email.setError("Empty Field");
            return;
        }
        if(un.isEmpty()){
            uName.setError("Empty field");
            return;
        }
        if(p.length()!=14){
            phone.setError("Invalid phone number");
        }


        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);

        Map<String, Object> ud = new HashMap<>();
        ud.put("name",name.getEditText().getText().toString().trim());
        ud.put("user",uName.getEditText().getText().toString().trim());
        ud.put("email",email.getEditText().getText().toString().trim());
        ud.put("phone",phone.getEditText().getText().toString().trim());


        db.collection("users")
                .whereEqualTo("email",sp.getString("email",""))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String docId = documentSnapshot.getId();
                            db.collection("users")
                                    .document(docId)
                                    .update(ud)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(updateProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), profile.class);
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(updateProfile.this, "Error in updating data", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            Toast.makeText(updateProfile.this, "Error Updating data", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}