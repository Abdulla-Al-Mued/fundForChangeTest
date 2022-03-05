package com.example.fundforchangetest.activities.forgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.otpVerification.otpVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class verifyUser extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextInputLayout email, otpUser;
    LinearLayout l1,l2;
    Button verifyUser, btnContinue;
    String phone, otpBackend;
    TextView resendOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);


        email = findViewById(R.id.email);
        otpUser = findViewById(R.id.otpField);
        l1 = findViewById(R.id.otp);
        l2 = findViewById(R.id.ul);
        verifyUser = findViewById(R.id.verifyBtn);
        resendOtp = findViewById(R.id.resendOtp);
        btnContinue  = findViewById(R.id.btnContinue);



        verifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCheck();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOtp();
            }
        });

        SharedPreferences sp3 = getSharedPreferences("datafile4",MODE_PRIVATE);
        phone = sp3.getString("phone","");

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp(phone);
            }
        });


    }


    private void sendOtp(String phone){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                verifyUser.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(verifyUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        otpBackend = otp;
                        Toast.makeText(verifyUser.this, "Otp Send successfully", Toast.LENGTH_SHORT).show();

                    }
                }
        );

    }
    private void checkOtp(){

        String userOtp = otpUser.getEditText().getText().toString().trim();

        if(userOtp.isEmpty()){
            otpUser.getEditText().setError("Please Enter your otp first");
            return;
        }

        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                otpBackend,userOtp
        );
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if(task.isSuccessful()){

                            Toast.makeText(verifyUser.this,"user verified", Toast.LENGTH_SHORT).show();
                            //insertData();
                            //verification();
                            SharedPreferences sp3 = getSharedPreferences("datafile4",MODE_PRIVATE);
                            SharedPreferences.Editor ed3 = sp3.edit();
                            ed3.remove("phone");
                            ed3.commit();
                            Intent intent = new Intent(getApplicationContext(), changePassword.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            FirebaseAuth.getInstance().signOut();
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(verifyUser.this, "Enter The Correct Otp Code", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
    private void userCheck() {

        String mail = email.getEditText().getText().toString().trim();


        if(mail.isEmpty()){
            email.getEditText().setError("Empty Field");
            return;
        }


        db.collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d: list){



                            String Mail, user;

                            Mail = d.getString("email");
                            user = d.getString("user");

                            if(Mail.equals(email.getEditText().getText().toString().trim()) ||
                                    user.equals(email.getEditText().getText().toString().trim())){

                                SharedPreferences sp3 = getSharedPreferences("datafile4",MODE_PRIVATE);
                                SharedPreferences.Editor ed3 = sp3.edit();
                                ed3.putString("phone",d.getString("phone"));
                                ed3.commit();

                                SharedPreferences sp = getSharedPreferences("datafile5",MODE_PRIVATE);
                                SharedPreferences.Editor ed = sp.edit();
                                ed.putString("email",d.getString("email"));
                                ed.commit();

                                email.getEditText().setError(null);
                                email.setErrorEnabled(false);
                                Toast.makeText(verifyUser.this, "User Found", Toast.LENGTH_SHORT).show();
                                l1.setVisibility(View.VISIBLE);
                                l2.setVisibility(View.GONE);
                                sendOtp(d.getString("phone"));
                                break;

                            }
                            else {
                                email.getEditText().setError("User Not Found");
                            }

                        }

                    }
                });

    }
}