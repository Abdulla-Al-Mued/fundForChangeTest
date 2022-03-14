package com.example.fundforchangetest.activities.user.users;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.userModel;
import com.example.fundforchangetest.adapters.userAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class userController {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    userAdapter adapter;
    RecyclerView recyclerView;
    List<userModel> datalist, backup;
    Context ctx;

    public userController(userAdapter adapter, RecyclerView recyclerView, List<userModel> datalist, Context ctx, List<userModel> backup) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.datalist = datalist;
        this.ctx = ctx;
        this.backup = backup;
    }

    public void showItems(){

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        datalist = new ArrayList<>();

        adapter = new userAdapter(datalist,ctx);
        recyclerView.setAdapter(adapter);


        //List<pendingModel> finalDatalist = datalist;
        //pendingEventsAdapter finalAdapter = adapter;

        db.collection("users").whereEqualTo("role","user")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot d : list){
                            userModel obj = d.toObject(userModel.class);
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

        db.collection("users").whereEqualTo("role","user")
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
                                userModel obj = d.toObject(userModel.class);
                                //obj.setId(d.getId());
                                backup.add(obj);
                            }

                        }
                        //adapter.notifyDataSetChanged();
                        adapter = new userAdapter(backup, ctx);
                        recyclerView.setAdapter(adapter);



                    }
                });

    }


}
