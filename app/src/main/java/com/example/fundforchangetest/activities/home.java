package com.example.fundforchangetest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.UserMainActivity;
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
    List<Model> datalist,backup;
    FirebaseFirestore db;
    commonRecyclerAdapter adapter;
    private CircleImageView profileBtn;
    //private static int SPLASH_TIME= 1;
    private static final String TAG = "FirestoreSearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText searchBox = findViewById(R.id.search_box);
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



        profileBtn = findViewById(R.id.profileBtn);
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



    // new added

    private void processSearch(String query) {

        db.collection("event").whereEqualTo("status","accepted")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        backup.clear();
                        for(DocumentSnapshot d : list){
                            String data = d.getString("name").toLowerCase();
                            if(data.contains(query)) {
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