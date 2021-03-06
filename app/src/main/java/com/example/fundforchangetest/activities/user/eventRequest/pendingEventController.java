package com.example.fundforchangetest.activities.user.eventRequest;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.activities.home;
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
    List<pendingModel> datalist, backup;
    Context ctx;

    public pendingEventController(pendingEventsAdapter adapter, RecyclerView recyclerView, List<pendingModel> datalist, Context ctx, List<pendingModel> backup) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.datalist = datalist;
        this.ctx = ctx;
        this.backup = backup;
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

    public void processSearch(String query) {

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        backup = new ArrayList<>();

        db.collection("event").whereEqualTo("status","pending")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        backup.clear();
                        for(DocumentSnapshot d : list){

                            String data = d.getString("name").toLowerCase();
                            if(data.contains(query) ) {
                                pendingModel obj = d.toObject(pendingModel.class);
                                //obj.setId(d.getId());
                                backup.add(obj);
                            }

                        }
                        //adapter.notifyDataSetChanged();
                        adapter = new pendingEventsAdapter(backup, ctx);
                        recyclerView.setAdapter(adapter);



                    }
                });

    }


}
