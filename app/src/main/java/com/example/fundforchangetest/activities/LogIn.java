package com.example.fundforchangetest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    Button btnLogIn, register;
    TextInputLayout uName, uPassword;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /*public void signup(View view){
            Intent intent = new Intent(LogIn.this, signup.class);
            startActivity(intent);
        }*/


        btnLogIn = findViewById(R.id.login);
        register = findViewById(R.id.signup);
        uName = findViewById(R.id.uName);
        uPassword = findViewById(R.id.uPassword);

        String email = uName.getEditText().getText().toString();
        String password = uPassword.getEditText().getText().toString();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SharedPreferences sharedPreferences = getSharedPreferences(LogIn.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasLogedIn",true);
                editor.commit();*/

                Intent intent  = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(intent);

               /* mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){


                                    Intent intent = new Intent(LogIn.this , UserMainActivity.class);
                                    intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                                    intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                                    startActivity(intent);

                                }
                                else{

                                    Toast.makeText(getApplicationContext(), "Invalid email password", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });*/






            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);
            }
        });






    }
}