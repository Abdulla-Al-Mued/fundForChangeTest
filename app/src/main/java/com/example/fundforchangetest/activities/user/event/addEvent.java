package com.example.fundforchangetest.activities.user.event;



import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addEvent {

    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    String  en, edesc, el, email, pn, nid, uMail;
    TextInputLayout txt,txt2,txt3,txt4,txt5,txt6, txt7;
    int edGoal;

    public addEvent(String en, String edesc, String el, int edGoal, String email, String pn, String nid, TextInputLayout txt, TextInputLayout txt2, TextInputLayout txt3, TextInputLayout txt4, TextInputLayout txt5, TextInputLayout txt6, TextInputLayout txt7, String uMail) {
        this.en = en;
        this.edesc = edesc;
        this.el = el;
        this.email = email;
        this.pn = pn;
        this.nid = nid;
        this.txt = txt;
        this.txt2 = txt2;
        this.txt3 = txt3;
        this.txt4 = txt4;
        this.txt5 = txt5;
        this.txt6 = txt6;
        this.txt7 = txt7;
        this.edGoal = edGoal;
        this.uMail = uMail;
    }




    public addEvent()
    {

    }





    public void insert(){



            Map<String, Object> items = new HashMap<>();


            items.put("name",en);
            items.put("description",edesc);
            items.put("location",el);
            items.put("goal",edGoal);
            items.put("email",email);
            items.put("phone",pn);
            items.put("NID",nid);
            items.put("status","pending");
            items.put("uEmail",uMail);


            dbroot.collection("event").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            //Toast.makeText(ctx,"Your Request Is Submitted Successfully",Toast.LENGTH_SHORT);


                        }
                    });



    }



}
