package com.mobdeve.s11.soriano.juancho.mco2memobile;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

/*
 * I think the Main Activity can host all viewing features:
 * - Notes
 * - Items in to-do list.
 * - Calendar of events.
 * - Friends list.
 * We'll just change the adapters depending on the one being viewed(?)
 */
public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle abdToggle;
    NavigationView navigationView;
    FloatingActionButton fabNewNote;
    FloatingActionButton fabNewItem;
    FloatingActionButton fabNewEvent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.initComponents();
    }

    private void initComponents() {
        Toolbar toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);

        this.drawerLayout = findViewById(R.id.drawer);
        this.navigationView = findViewById(R.id.nav_view);
        this.abdToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        this.drawerLayout.addDrawerListener(abdToggle);
        this.abdToggle.setDrawerIndicatorEnabled(true);
        this.abdToggle.syncState();

        /*
        * Button initiator. By default, the note screen is the first to be shown on start up. The buttons will either
        * be set to visible or gone depending on which screen is active.
        */
        this.fabNewNote = findViewById(R.id.fab_newnote);
        this.fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent (MainActivity.this, AddNoteActivity.class);
                startActivity(regIntent);
            }
        });

        this.fabNewItem = findViewById(R.id.fab_newitem);
        this.fabNewEvent = findViewById(R.id.fab_newevent);
    }
}