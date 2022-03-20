package com.example.fundforchangetest.activities.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.pdfGenerator.downloadDashboardData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class dashboard extends AppCompatActivity {

    TextView last24, tdonor, totalEvent, pendingEvent, users, finishEvent, header;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] items = {"24 Hours","7 Days","1 Month","All Time"};
    AutoCompleteTextView dm;
    ArrayAdapter<String> adapterItems;
    long currentTime;
    Date time = new Date(currentTime);
    Button generatePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        last24 = findViewById(R.id.last24);
        tdonor = findViewById(R.id.tDonor);
        dm = findViewById(R.id.dropdown_menu);

        totalEvent = findViewById(R.id.total_event);
        pendingEvent = findViewById(R.id.pending_events);
        users = findViewById(R.id.users);
        finishEvent = findViewById(R.id.finish_event);
        header = findViewById(R.id.header);
        generatePdf = findViewById(R.id.bnt_getReport);


        adapterItems = new ArrayAdapter<String>(this, R.layout.list_items, items);
        dm.setAdapter(adapterItems);
        showAll();
        showPendingEvent();
        showUser();



        dm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("24 Hours")){

                    currentTime = new Date().getTime() - (24 * 3600 * 1000);
                    time = new Date(currentTime);
                    showData();
                    showPendingEvent();
                    showUser();
                    header.setText("Activity Of Last 24 Hours");
                }
                else if(item.equals("7 Days")){
                    currentTime = new Date().getTime() - (7 * 24 * 3600 * 1000);
                    time = new Date(currentTime);
                    showData();
                    showPendingEvent();
                    showUser();
                    header.setText("Activity Of Last 7 Days");
                }
                else if(item.equals("1 Month")){
                    currentTime = new Date().getTime() - (30L * 24 * 3600 * 1000);
                    time = new Date(currentTime);
                    showData();
                    showPendingEvent();
                    showUser();
                    header.setText("Activity Of Last 1 Month");
                }
                else if(item.equals("All Time")){
                    currentTime = new Date().getTime();
                    time = new Date(currentTime);
                    showAll();
                    showPendingEvent1();
                    showUser1();
                    header.setText("Activity Of All Time");
                }
            }
        });

        generatePdf.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), downloadDashboardData.class);
            intent.putExtra("TA",last24.getText());
            intent.putExtra("TD",tdonor.getText());
            intent.putExtra("TE",totalEvent.getText());
            intent.putExtra("PE",pendingEvent.getText());
            intent.putExtra("U",users.getText());
            intent.putExtra("FE",finishEvent.getText());
            intent.putExtra("H",header.getText());
            startActivity(intent);

        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
        startActivity(intent);
    }

    public void showData(){

        db.collection("Donations").whereGreaterThan("dTime",time)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                int td=0,donated, tDonor = 0;

                for(DocumentSnapshot d : list){

                    donated = Math.toIntExact(d.getLong("Amount"));

                    td = td + donated;
                    tDonor = tDonor + 1;

                }
                last24.setText(String.valueOf(td));
                tdonor.setText(String.valueOf(tDonor));

            }
        });

    }

    public void showAll(){

        Date currentTime = Calendar.getInstance().getTime();

        db.collection("Donations").whereLessThan("dTime",currentTime)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                int td=0,donated, tDonor = 0;

                for(DocumentSnapshot d : list){

                    donated = Math.toIntExact(d.getLong("Amount"));

                    td = td + donated;
                    tDonor = tDonor + 1;

                }
                last24.setText(String.valueOf(td));
                tdonor.setText(String.valueOf(tDonor));

            }
        });

    }

    public void showPendingEvent(){


        db.collection("event").whereGreaterThan("dTime",time)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                int ac = 0,fe = 0, pd = 0;
                String status;

                for(DocumentSnapshot d : list){


                    status = d.getString("status");
                    if(status.equals("accepted")){
                        ac = ac + 1;
                    }
                    if(status.equals("pending")){
                        pd = pd + 1;
                    }
                    if (status.equals("finish")){
                        fe = fe + 1;
                    }

                }

                totalEvent.setText(String.valueOf(ac));
                pendingEvent.setText(String.valueOf(pd));
                finishEvent.setText(String.valueOf(fe));

            }
        });

    }

    public void showPendingEvent1(){


        db.collection("event").whereLessThan("dTime",time)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                int ac = 0,fe = 0, pd = 0;
                String status;

                for(DocumentSnapshot d : list){


                    status = d.getString("status");
                    if(status.equals("accepted")){
                        ac = ac + 1;
                    }
                    if(status.equals("pending")){
                        pd = pd + 1;
                    }
                    if (status.equals("finish")){
                        fe = fe + 1;
                    }

                }

                totalEvent.setText(String.valueOf(ac));
                pendingEvent.setText(String.valueOf(pd));
                finishEvent.setText(String.valueOf(fe));

            }
        });

    }

    public void showUser(){

        db.collection("users").whereGreaterThan("dTime",time)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                int tu=0;

                for(DocumentSnapshot d : list){

                    if(d.getString("role").equals("user")){
                        tu = tu + 1;
                    }
                }
                users.setText(String.valueOf(tu));

            }
        });

    }

    public void showUser1(){

        db.collection("users").whereLessThan("dTime",time)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                int tu=0;

                for(DocumentSnapshot d : list){

                    if(d.getString("role").equals("user")){
                        tu = tu + 1;
                    }
                }
                users.setText(String.valueOf(tu));

            }
        });

    }
}