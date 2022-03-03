package com.example.fundforchangetest.activities.user.myEvents;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.myEventModel;
import com.example.fundforchangetest.adapters.myEventAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myEventController {
    myEventAdapter adapter;
    RecyclerView recyclerView;
    List<myEventModel> datalist;
    Context ctx;
    String uEmail;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public myEventController(myEventAdapter adapter, RecyclerView recyclerView,
                             List<myEventModel> datalist, Context ctx, String uEmail) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.datalist = datalist;
        this.ctx = ctx;
        this.uEmail = uEmail;
    }


    public void showItems(){

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        datalist = new ArrayList<>();

        adapter = new myEventAdapter(datalist,ctx);
        recyclerView.setAdapter(adapter);

        db.collection("event")
                .whereEqualTo("uEmail",uEmail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot d : list){
                                myEventModel obj = d.toObject(myEventModel.class);
                                obj.setId(d.getId());
                                datalist.add(obj);


                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
