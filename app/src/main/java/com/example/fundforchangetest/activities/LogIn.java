package com.example.fundforchangetest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.forgotPassword.verifyUser;
import com.example.fundforchangetest.activities.security.PasswordEncryptor;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.otpVerification.otpVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.NoSuchAlgorithmException;

public class LogIn extends AppCompatActivity {

    Button btnLogIn, register,frogotPass;
    TextInputLayout uEmail, uPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //public static String PREFS_NAME = "MyPrefsFile";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        btnLogIn = findViewById(R.id.login);
        register = findViewById(R.id.signup);
        uEmail = findViewById(R.id.uName);
        uPassword = findViewById(R.id.uPassword);
        frogotPass = findViewById(R.id.forgot_pass);
        progressBar = findViewById(R.id.progressbar_login);

        frogotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), verifyUser.class);
                startActivity(intent);

            }
        });


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /*SharedPreferences sharedPreferences = getSharedPreferences(LogIn.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasLogedIn",true);
                editor.commit();*/

                if (!validateEmail() | !validatePassword())
                    return;


                progressBar.setVisibility(View.VISIBLE);
                btnLogIn.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                frogotPass.setVisibility(View.INVISIBLE);




                db.collection("users")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                for(QueryDocumentSnapshot q: queryDocumentSnapshots){


                                    String mail ,pass, role, user;

                                    mail = q.getString("email");
                                    pass = q.getString("password");
                                    role = q.getString("role");
                                    user = q.getString("user");

                                    String email = uEmail.getEditText().getText().toString();
                                    String password = null;
                                    try {
                                        password = PasswordEncryptor.getInstance().encriptPassword(uPassword.getEditText().getText().toString());
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                    }


                                    if(mail.equals(email)&&(pass.equals(password)) || user.equals(email)&&(pass.equals(password))){

                                        uEmail.getEditText().setError(null);
                                        uEmail.setErrorEnabled(false);
                                        uPassword.getEditText().setError(null);
                                        uPassword.setErrorEnabled(false);

                                        Toast.makeText(LogIn.this, "Log In Success", Toast.LENGTH_SHORT).show();

                                        Intent intent  = new Intent(getApplicationContext(), UserMainActivity.class);
                                        startActivity(intent);

                                        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sp.edit();
                                        ed.putString("email",mail);
                                        ed.commit();

                                        SharedPreferences sp3 = getSharedPreferences("datafile3",MODE_PRIVATE);
                                        SharedPreferences.Editor ed3 = sp3.edit();
                                        ed3.putString("role",role);
                                        ed3.commit();
                                        break;

                                    }
                                    else if((mail.equals(email))&&!(pass.equals(password))){
                                        uPassword.getEditText().setError("Invalid password");
                                        progressBar.setVisibility(View.GONE);
                                        btnLogIn.setVisibility(View.VISIBLE);
                                        register.setVisibility(View.VISIBLE);
                                        frogotPass.setVisibility(View.VISIBLE);
                                    }


                                    else if(!(mail.equals(email))&&!(pass.equals(password)) || !(user.equals(email))&&!(pass.equals(password)))
                                    {
                                        uEmail.getEditText().setError("User Not found");
                                        uPassword.getEditText().setError("Password Unmatched");
                                        progressBar.setVisibility(View.GONE);
                                        btnLogIn.setVisibility(View.VISIBLE);
                                        register.setVisibility(View.VISIBLE);
                                        frogotPass.setVisibility(View.VISIBLE);
                                    }


                                }


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogIn.this, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                            }
                        });



                /*Toast.makeText(LogIn.this, email[0], Toast.LENGTH_SHORT).show();
                Toast.makeText(LogIn.this, password[0], Toast.LENGTH_SHORT).show();*/


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnLogIn.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                frogotPass.setVisibility(View.INVISIBLE);


                Intent intent  = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);


                progressBar.setVisibility(View.GONE);
                btnLogIn.setVisibility(View.VISIBLE);
                register.setVisibility(View.VISIBLE);
                frogotPass.setVisibility(View.VISIBLE);
            }
        });

    }

    private boolean validateEmail() {

        String t1;
        //String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        t1 = uEmail.getEditText().getText().toString().trim();

        if (t1.isEmpty()) {

            uEmail.getEditText().setError("Invalid Email");
            return false;

        }
        else {
            uEmail.getEditText().setError(null);
            uEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {

        String t1;
        String checkSpaces = "\\A\\w{1,20}\\Z";

        t1 = uPassword.getEditText().getText().toString().trim();


        if (t1.isEmpty()) {

            uPassword.getEditText().setError("Field Cannot be empty");
            return false;

        } else if (!(t1.length() > 5)) {

            uPassword.getEditText().setError("password should contain at least 6 character ");
            return false;

        } else if (!t1.matches(checkSpaces)) {
            uPassword.getEditText().setError("No white space are allowed");
            return false;
        } else {
            uPassword.getEditText().setError(null);
            uPassword.setErrorEnabled(false);
            return true;
        }
    }
}