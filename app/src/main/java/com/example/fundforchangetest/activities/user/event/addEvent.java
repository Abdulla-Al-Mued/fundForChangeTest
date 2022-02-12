package com.example.fundforchangetest.activities.user.event;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addEvent {

    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    String  edesc, el, email, pn, nid, en;
    int edGoal;


    public addEvent()
    {

    }


    public addEvent(String t1, String t2, String t3, int t4, String t5, String t6, String t7) {

        en = t1;
        edesc = t2;
        el = t3;
        edGoal = t4;
        email = t5;
        pn = t6;
        nid = t7;

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


        dbroot.collection("event").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        //Toast.makeText(ctx,"Your Request Is Submitted Successfully",Toast.LENGTH_SHORT);


                    }
                });


    }

}
