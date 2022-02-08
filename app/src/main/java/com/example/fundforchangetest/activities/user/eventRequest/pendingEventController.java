package com.example.fundforchangetest.activities.user.eventRequest;

import static java.security.AccessController.getContext;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.adapters.commonRecyclerAdapter;
import com.example.fundforchangetest.adapters.pendingEventsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class pendingEventController {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    pendingEventsAdapter adapter;
    RecyclerView recyclerView;
    List<pendingModel> datalist;
    Context ctx;

    public pendingEventController(pendingEventsAdapter adapter, RecyclerView recyclerView, List<pendingModel> datalist, Context ctx) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.datalist = datalist;
        this.ctx = ctx;
    }

    public void showItems(){

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        datalist = new ArrayList<>();

        adapter = new pendingEventsAdapter(datalist,ctx);
        recyclerView.setAdapter(adapter);


        //List<pendingModel> finalDatalist = datalist;
        //pendingEventsAdapter finalAdapter = adapter;

        db.collection("event").whereEqualTo("status","pending")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot d : list){
                            pendingModel obj = d.toObject(pendingModel.class);
                            obj.setId(d.getId());
                            datalist.add(obj);

                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


}
