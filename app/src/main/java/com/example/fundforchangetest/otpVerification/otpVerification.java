package com.example.fundforchangetest.otpVerification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.LogIn;
import com.example.fundforchangetest.activities.signUp;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class otpVerification extends AppCompatActivity {

    String otpBackend, msg="", x;
    EditText t1, t2, t3, t4, t5, t6;
    Button submit;
    TextView tvBtn, tv2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean temp = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        t1 = findViewById(R.id.otp_1);
        t2 = findViewById(R.id.otp_2);
        t3 = findViewById(R.id.otp_3);
        t4 = findViewById(R.id.otp_4);
        t5 = findViewById(R.id.otp_5);
        t6 = findViewById(R.id.otp_6);

        tvBtn = findViewById(R.id.resend_code);

        ProgressBar  progressBar = findViewById(R.id.otp_progressbar);

        submit = findViewById(R.id.submit_otp);


        tv2 = findViewById(R.id.numberField);
        tv2.setText(getIntent().getStringExtra("Number"));
        otpBackend = getIntent().getStringExtra("bkndOtp");
        x = getIntent().getStringExtra("email");




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!(t1.getText().toString().trim().isEmpty()) && !(t2.getText().toString().trim().isEmpty())
                &&!(t3.getText().toString().trim().isEmpty()) && !(t4.getText().toString().trim().isEmpty())
                &&!(t5.getText().toString().trim().isEmpty()) && !(t6.getText().toString().trim().isEmpty())){

                    String copOtp = t1.getText().toString().trim() +
                            t2.getText().toString().trim()+
                            t3.getText().toString().trim()+
                            t4.getText().toString().trim()+
                            t5.getText().toString().trim()+
                            t6.getText().toString().trim();

                    if(otpBackend!=null){


                        progressBar.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                          otpBackend,copOtp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                submit.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){

                                    msg = "verification Complete";
                                    Toast.makeText(otpVerification.this, msg, Toast.LENGTH_SHORT).show();
                                    //insertData();
                                    //verification();
                                    temp = true;


                                    SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);
                                    SharedPreferences.Editor ed = sp.edit();
                                    ed.putString("email",x);
                                    ed.commit();

                                    SharedPreferences sp3 = getSharedPreferences("datafile3",MODE_PRIVATE);
                                    SharedPreferences.Editor ed3 = sp3.edit();
                                    ed3.putString("role","user");
                                    ed3.commit();

                                    SharedPreferences sp2 = getSharedPreferences("temp",MODE_PRIVATE);
                                    SharedPreferences.Editor ed2 = sp2.edit();
                                    ed2.putString("status","firstTime");
                                    ed2.commit();

                                    Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(otpVerification.this, "Enter The Correct Otp Code", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(otpVerification.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(otpVerification.this,"Please enter the Otp",Toast.LENGTH_SHORT);
                }

            }
        });


        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+88" + getIntent().getStringExtra("Number"),
                        60,
                        TimeUnit.SECONDS,
                        otpVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(otpVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                otpBackend = otp;
                                Toast.makeText(otpVerification.this, "Otp Send successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });

        if(temp){



        }

        moveTxtField();

    }

    private void moveTxtField() {


        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                    t2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        t2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                    t3.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        t3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                    t4.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        t4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                    t5.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        t5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                    t6.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}