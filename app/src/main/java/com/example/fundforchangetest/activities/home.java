package com.example.fundforchangetest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.adapters.commonRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class home extends AppCompatActivity {


    RecyclerView recview;
    List<Model> datalist;
    FirebaseFirestore db;
    commonRecyclerAdapter adapter;
    private CircleImageView profileBtn;
    private static int SPLASH_TIME= 1;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recview = findViewById(R.id.rec_view);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();

        adapter = new commonRecyclerAdapter(this,datalist);
        recview.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();

        showItems();



        profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences(LogIn.PREFS_NAME,0);
                        boolean haslogin = sharedPreferences.getBoolean("hasLogedIn",false);

                        if (haslogin) {

                            Toast.makeText(getApplicationContext(), "Already log in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(home.this , UserMainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Intent intent = new Intent(home.this , LogIn.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                },SPLASH_TIME);*/

                Intent intent = new Intent(home.this , LogIn.class);
                startActivity(intent);



            }
        });

    }

    public void showItems(){

        db.collection("event").whereEqualTo("status","accepted")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot d : list){
                            Model obj = d.toObject(Model.class);
                            obj.setId(d.getId());
                            datalist.add(obj);

                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}