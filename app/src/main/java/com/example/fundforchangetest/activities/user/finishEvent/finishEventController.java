package com.example.fundforchangetest.activities.user.finishEvent;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.finishEventModel;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.adapters.finishEventAdapter;
import com.example.fundforchangetest.adapters.pendingEventsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class finishEventController {

    finishEventAdapter adapter;
    RecyclerView recyclerView;
    List<finishEventModel> datalist;
    Context ctx;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public finishEventController(finishEventAdapter adapter, RecyclerView recyclerView,
                                 List<finishEventModel> datalist, Context ctx) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.datalist = datalist;
        this.ctx = ctx;
    }


    public void showItems(){

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        datalist = new ArrayList<>();

        adapter = new finishEventAdapter(datalist,ctx);
        recyclerView.setAdapter(adapter);


        //List<pendingModel> finalDatalist = datalist;
        //pendingEventsAdapter finalAdapter = adapter;

        db.collection("event").whereEqualTo("status","accepted")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot d : list){
                            int donated = Math.toIntExact(d.getLong("donated"));
                            int goal = Math.toIntExact(d.getLong("goal"));
                            if(donated >= goal){
                                finishEventModel obj = d.toObject(finishEventModel.class);
                                obj.setId(d.getId());
                                datalist.add(obj);
                            }


                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
