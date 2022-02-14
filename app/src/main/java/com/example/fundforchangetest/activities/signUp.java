package com.example.fundforchangetest.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.otpVerification.otpVerification;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class signUp extends AppCompatActivity {

    TextInputLayout txt1, txt2, txt3, txt4, txt5, txt6;

    Button register,login;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt1 = findViewById(R.id.name);
        txt2 = findViewById(R.id.Uname);
        txt3 = findViewById(R.id.email);
        txt4 = findViewById(R.id.phone);
        txt5 = findViewById(R.id.pass);
        txt6 = findViewById(R.id.c_pass);

        progressBar = findViewById(R.id.progressBar_Account);


        register = findViewById(R.id.register);
        login = findViewById(R.id.login);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() | !validateFullname() | !validatePhone() | !validatePassword() |
                        !validateConfirmPassword() | !validateUsername())
                    return;

                progressBar.setVisibility(View.VISIBLE);
                register.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);



                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+880" + "1" + txt4.getEditText().getText().toString().trim(),
                        60,
                        TimeUnit.SECONDS,
                        signUp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                progressBar.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                progressBar.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);

                                Toast.makeText(signUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                progressBar.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(getApplicationContext(), otpVerification.class);
                                intent.putExtra("Number","01" + txt4.getEditText().getText().toString().trim());
                                intent.putExtra("bkndOtp",otp);
                                intent.putExtra("fName",txt1.getEditText().getText().toString().trim());
                                intent.putExtra("uName",txt2.getEditText().getText().toString().trim());
                                intent.putExtra("email",txt3.getEditText().getText().toString().trim());
                                intent.putExtra("password",txt5.getEditText().getText().toString().trim());
                                startActivity(intent);

                            }
                        }
                );

                /*Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(intent);*/

            }
        });


    }


    private boolean validateFullname() {

        String t1;

        t1 = txt1.getEditText().getText().toString().trim();


        if (t1.isEmpty()) {
            txt1.getEditText().setError("Field Cannot be empty");
            return false;
        } else {
            txt1.setError(null);
            txt1.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateUsername() {

        String t1;
        String checkSpaces = "\\A\\w{1,20}\\Z";

        t1 = txt2.getEditText().getText().toString().trim();

        if (t1.isEmpty()) {

            txt2.getEditText().setError("Field Cannot be empty");
            return false;

        } else if (t1.length() > 20) {

            txt2.getEditText().setError("Username is too large");
            return false;

        } else if (!t1.matches(checkSpaces)) {
            txt2.getEditText().setError("No white space are allowed and character first");
            return false;
        } else {
            txt2.getEditText().setError(null);
            txt2.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateEmail() {

        String t1;
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        t1 = txt3.getEditText().getText().toString().trim();

        if (t1.isEmpty()) {

            txt3.getEditText().setError("Invalid Email");
            return false;

        } else if (!t1.matches(checkEmail)) {
            txt3.getEditText().setError("Invalid Email");
            return false;
        } else {
            txt3.getEditText().setError(null);
            txt3.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validatePhone() {

        String t1;
        String checkPhone = "^01[0-9][9]";

        t1 = txt4.getEditText().getText().toString().trim();

        if (t1.isEmpty()) {

            txt4.getEditText().setError("This field can not be empty");
            return false;

        } else if (t1.length() != 9) {
            txt4.getEditText().setError("Invalid Number");
            return false;
        } else {
            txt4.getEditText().setError(null);
            txt4.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validatePassword() {

        String t1;
        String checkSpaces = "\\A\\w{1,20}\\Z";

        t1 = txt5.getEditText().getText().toString().trim();


        if (t1.isEmpty()) {

            txt5.getEditText().setError("Field Cannot be empty");
            return false;

        } else if (!(t1.length() > 5)) {

            txt5.getEditText().setError("password should contain at least 6 character ");
            return false;

        } else if (!t1.matches(checkSpaces)) {
            txt5.getEditText().setError("No white space are allowed");
            return false;
        } else {
            txt5.getEditText().setError(null);
            txt5.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateConfirmPassword() {

        String t1, t2;
        String checkSpaces = "\\A\\w{1,20}\\Z";

        t1 = txt6.getEditText().getText().toString().trim();
        t2 = txt5.getEditText().getText().toString().trim();

        if (t1.isEmpty()) {

            txt6.getEditText().setError("Field Cannot be empty");
            return false;

        } else if (!t1.equals(t2)) {
            txt6.getEditText().setError("Password is unmatched ");

            return false;
        } else {
            txt6.getEditText().setError(null);
            txt6.setErrorEnabled(false);
            return true;
        }
    }



}