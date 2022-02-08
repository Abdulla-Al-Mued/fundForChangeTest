package com.example.fundforchangetest.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.profile;
import com.example.fundforchangetest.activities.user.event.createEventFragment;
import com.example.fundforchangetest.activities.user.eventRequest.pendingEvents;
import com.example.fundforchangetest.activities.user.home.homeFragment;
import com.example.fundforchangetest.activities.user.myEvents.myEvent;
import com.google.android.material.navigation.NavigationView;

public class UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationview;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationview = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //navigation drawer menu
        navigationview.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //hide or show item
        Menu menu = navigationview.getMenu();
        //menu.findItem(R.id.nav_login).setVisible(false);

        navigationview.setNavigationItemSelectedListener(this);

        //checked item
        navigationview.setCheckedItem(R.id.nav_home);
        //default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new homeFragment()).commit();
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    public void profile(View view){

        Intent intent = new Intent(UserMainActivity.this, profile.class);
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        //Fragment temp = null;
        switch (item.getItemId()){
            case R.id.nav_home:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, new homeFragment());
                ft.addToBackStack("tag_back");
                ft.commit();
                //temp = new Home();
                break;
            case (R.id.nav_myevents):
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


        }
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}