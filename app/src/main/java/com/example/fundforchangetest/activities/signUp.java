package com.example.fundforchangetest.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.otpVerification.otpVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class signUp extends AppCompatActivity {

    TextInputLayout txt1, txt2, txt3, txt4, txt5, txt6;

    Button register,login;

    ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                                startActivity(intent);

                            }
                        }
                );


                /*Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(intent);*/

                SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();

                if(!(sp.contains("email"))){

                    Map<String, String> items = new HashMap<>();


                    items.put("name",txt1.getEditText().getText().toString().trim());
                    items.put("user",txt2.getEditText().getText().toString().trim());
                    items.put("email",txt3.getEditText().getText().toString().trim());
                    items.put("phone","+8801"+txt4.getEditText().getText().toString().trim());
                    items.put("password",txt5.getEditText().getText().toString().trim());
                    items.put("role","user");



                    db.collection("users").add(items)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    //Toast.makeText(ctx,"Your Request Is Submitted Successfully",Toast.LENGTH_SHORT);


                                }
                            });

                }
                else {
                Toast.makeText(signUp.this, "Error Saving Data", Toast.LENGTH_SHORT).show();
                }


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
        //String checkPhone = "^01[0-9][9]";
        checkPhoneExist();
        SharedPreferences sp = getSharedPreferences("datafile2",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        SharedPreferences sp1 = getSharedPreferences("datafile3",MODE_PRIVATE);
        SharedPreferences.Editor ed1 = sp1.edit();

        t1 = txt4.getEditText().getText().toString().trim();

        if (t1.isEmpty()) {

            txt4.getEditText().setError("This field can not be empty");
            return false;

        } else if (t1.length() != 9) {
            txt4.getEditText().setError("Invalid Number");
            return false;
        }
        else if(sp.contains("Exist1") && sp1.contains("Exist2")){

            txt4.getEditText().setError("Phone number already exist");
            txt3.getEditText().setError("Email number already exist");
            ed.remove("Exist1");
            ed.commit();
            ed1.remove("Exist2");
            ed1.commit();
            return false;
        }
        else if(sp.contains("Exist1")){

            txt3.getEditText().setError("Email number already exist");
            ed.remove("Exist1");
            ed.commit();
            return false;

        }
        else if(sp1.contains("Exist2")){

            txt4.getEditText().setError("Phone number already exist");
            ed1.remove("Exist2");
            ed1.commit();
            return false;

        }
        else {
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

    public void checkPhoneExist() {


        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot qs : list){

                            String phone, email;
                            phone = qs.getString("phone");
                            email = qs.getString("email");

                            String number = "+8801"+txt4.getEditText().getText().toString().trim();
                            String mail = txt3.getEditText().getText().toString().trim();

                            SharedPreferences sp = getSharedPreferences("datafile2",MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();

                            SharedPreferences sp1 = getSharedPreferences("datafile3",MODE_PRIVATE);
                            SharedPreferences.Editor ed1 = sp1.edit();

                            if(mail.equals(email)&&(number.equals(phone))){
                                ed1.putString("Exist2","p");
                                ed1.commit();
                                ed.putString("Exist1","e");
                                ed.commit();


                                break;


                            }
                            else if((mail.equals(email))&&!(number.equals(phone))){

                                ed.putString("Exist1","e");
                                ed.commit();

                                break;
                            }
                            else if(number.equals(phone)){
                                ed1.putString("Exist2","p");
                                ed1.commit();
                                break;
                            }


                        }

                    }
                });


    }



}