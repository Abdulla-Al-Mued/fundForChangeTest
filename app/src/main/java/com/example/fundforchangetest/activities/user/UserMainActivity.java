package com.example.fundforchangetest.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.home;
import com.example.fundforchangetest.activities.profile;
import com.example.fundforchangetest.activities.user.event.createEventFragment;
import com.example.fundforchangetest.activities.user.eventRequest.pendingEvents;
import com.example.fundforchangetest.activities.user.finishEvent.finishEventFragment;
import com.example.fundforchangetest.activities.user.myEvents.myEvent;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;

public class UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationview;
    Toolbar toolbar;
    TextView name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationview = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        //name = findViewById(R.id.profile_name);

        View header = navigationview.getHeaderView(0);
        name = header.findViewById(R.id.profile_name);
        setHeader();


        setSupportActionBar(toolbar);

        //navigation drawer menu
        navigationview.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.openDrawer(Gravity.LEFT);

        //hide or show item
        Menu menu = navigationview.getMenu();
        SharedPreferences sp3 = getSharedPreferences("datafile3",MODE_PRIVATE);
        String role = sp3.getString("role","");

        if(role.equals("user")){
            menu.findItem(R.id.nav_event_request).setVisible(false);
            menu.findItem(R.id.nav_finish_event).setVisible(false);
        }

        //menu.findItem(R.id.nav_login).setVisible(false);

        navigationview.setNavigationItemSelectedListener(this);

        //checked item
        navigationview.setCheckedItem(R.id.nav_create_event);
        //default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new createEventFragment()).addToBackStack(null).commit();
    }

    private void setHeader() {

        SharedPreferences sp = getSharedPreferences("datafile",MODE_PRIVATE);

        db.collection("users")
                .whereEqualTo("email",sp.getString("email",""))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d : list){
                            name.setText(d.getString("name"));
                        }
                    }
                });
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    public void profile(View view){

        Intent intent = new Intent(UserMainActivity.this, profile.class);
        intent.putExtra("email",getIntent().getStringExtra("email"));
        intent.putExtra("uid",getIntent().getStringExtra("uid"));
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        //Fragment temp = null;
        switch (item.getItemId()){
            case (R.id.nav_event_request):
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.container, new pendingEvents());
                ft1.addToBackStack("tag_back");
                ft1.commit();
                break;

            case  (R.id.nav_create_event):
                //temp = new Share();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new createEventFragment());
                ft2.addToBackStack("tag_back");
                ft2.commit();

                break;

            case  (R.id.nav_finish_event):
                //temp = new Share();
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.container, new finishEventFragment());
                ft3.addToBackStack("tag_back");
                ft3.commit();

                break;

            case  (R.id.nav_my_events):
                //temp = new Share();
                FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                ft4.replace(R.id.container, new myEvent());
                ft4.addToBackStack("tag_back");
                ft4.commit();

                break;
            case  (R.id.nav_see_event):
            //temp = new Share();
                Intent intent1 = new Intent(UserMainActivity.this, home.class);
                startActivity(intent1);

                break;


        }
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}