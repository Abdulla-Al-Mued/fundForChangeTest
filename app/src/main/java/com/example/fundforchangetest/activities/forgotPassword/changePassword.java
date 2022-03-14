package com.example.fundforchangetest.activities.forgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.profile;
import com.example.fundforchangetest.activities.security.PasswordEncryptor;
import com.example.fundforchangetest.activities.updateProfile;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class changePassword extends AppCompatActivity {

    TextInputLayout newPass;
    Button btnUpdate;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPass = findViewById(R.id.new_pass);
        btnUpdate = findViewById(R.id.btnUpdate);

        SharedPreferences sp = getSharedPreferences("datafile5",MODE_PRIVATE);
        email = sp.getString("email","");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String np = newPass.getEditText().getText().toString().trim();

                if(!(np.length() > 5)){
                    newPass.getEditText().setError("password should contain at lest 6 character");
                    return;
                }

                Map<String, Object> ud = new HashMap<>();
                try {
                    ud.put("password",PasswordEncryptor.getInstance().encriptPassword(newPass.getEditText().getText().toString().trim()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                db.collection("users")
                        .whereEqualTo("email",email)
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
                                                    SharedPreferences sp = getSharedPreferences("datafile5",MODE_PRIVATE);
                                                    SharedPreferences sp1 = getSharedPreferences("datafile",MODE_PRIVATE);
                                                    SharedPreferences.Editor ed = sp1.edit();
                                                    ed.putString("email",sp.getString("email",""));
                                                    ed.commit();


                                                    SharedPreferences.Editor ed3 = sp.edit();
                                                    ed3.remove("email");
                                                    ed3.commit();

                                                    Toast.makeText(changePassword.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(changePassword.this, "Error in updating data", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }else{
                                    Toast.makeText(changePassword.this, "Error Updating data", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}