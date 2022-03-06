package com.example.fundforchangetest.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.finishEventModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
import com.example.fundforchangetest.adapters.commonRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class home extends AppCompatActivity {


    RecyclerView recview;
    List<Model> datalist,backup;
    FirebaseFirestore db;
    commonRecyclerAdapter adapter;
    private CircleImageView profileBtn;
    //private static int SPLASH_TIME= 1;
    //private static final String TAG = "FirestoreSearchActivity";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText searchBox = findViewById(R.id.search_box);
        progressBar = findViewById(R.id.progressBar_home);
        profileBtn = findViewById(R.id.profileBtn);


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //Log.d(TAG, "SearchBox has change to : "+ s.toString());
                processSearch(s.toString().toLowerCase().trim());
                //profileBtn.setVisibility(View.INVISIBLE);

            }
        });

        recview = findViewById(R.id.rec_view);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        backup = new ArrayList<>();

        adapter = new commonRecyclerAdapter(this,datalist);
        recview.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();

        showItems();




        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();

                if(!(sp.contains("email"))){

                    Intent intent = new Intent(home.this , LogIn.class);
                    startActivity(intent);

                }
                else {
                    Intent intent = new Intent(home.this , UserMainActivity.class);
                    startActivity(intent);
                }


            }
        });



    }

    @Override
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        //System.exit(0);
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }

    public void showItems(){


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
                            if(donated < goal){

                                Model obj = d.toObject(Model.class);
                                obj.setId(d.getId());
                                datalist.add(obj);

                            }


                        }
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                });

    }



    // new added

    private void processSearch(String query) {

        db.collection("event").whereEqualTo("status","accepted")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        backup.clear();
                        for(DocumentSnapshot d : list){

                            int donated = Math.toIntExact(d.getLong("donated"));
                            int goal = Math.toIntExact(d.getLong("goal"));
                            String data = d.getString("name").toLowerCase();
                            if(data.contains(query) && donated < goal) {
                                Model obj = d.toObject(Model.class);
                                //obj.setId(d.getId());
                                backup.add(obj);
                            }

                        }
                        //adapter.notifyDataSetChanged();
                        adapter = new commonRecyclerAdapter(home.this,backup);
                        recview.setAdapter(adapter);



                    }
                });

    }


}