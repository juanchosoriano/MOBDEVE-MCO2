package com.mobdeve.s11.soriano.juancho.mco2memobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle abdToggle;
    NavigationView navigationView;
    FloatingActionButton fabNewNote;
    FloatingActionButton fabNewItem;
    FloatingActionButton fabNewEvent;

    RecyclerView rvNotes;
    RecyclerView rvTodo;
    RecyclerView rvEvents;
    RecyclerView rvFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.initComponents();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.notes:
                Toast.makeText(MainActivity.this, "You are now viewing your notes.", Toast.LENGTH_SHORT).show();
                this.fabNewNote.setVisibility(View.VISIBLE);
                this.fabNewItem.setVisibility(View.GONE);
                this.fabNewEvent.setVisibility(View.GONE);

                this.rvNotes.setVisibility(View.VISIBLE);
                this.rvTodo.setVisibility(View.GONE);
                this.rvEvents.setVisibility(View.GONE);
                this.rvFriends.setVisibility(View.GONE);
                break;

            case R.id.todo:
                Toast.makeText(MainActivity.this, "You are now viewing your to-do list.", Toast.LENGTH_SHORT).show();
                this.fabNewNote.setVisibility(View.GONE);
                this.fabNewItem.setVisibility(View.VISIBLE);
                this.fabNewEvent.setVisibility(View.GONE);

                this.rvNotes.setVisibility(View.GONE);
                this.rvTodo.setVisibility(View.VISIBLE);
                this.rvEvents.setVisibility(View.GONE);
                this.rvFriends.setVisibility(View.GONE);
                break;

            case R.id.events:
                Toast.makeText(MainActivity.this, "You are now viewing your list of events.", Toast.LENGTH_SHORT).show();
                this.fabNewNote.setVisibility(View.GONE);
                this.fabNewItem.setVisibility(View.GONE);
                this.fabNewEvent.setVisibility(View.VISIBLE);

                this.rvNotes.setVisibility(View.GONE);
                this.rvTodo.setVisibility(View.GONE);
                this.rvEvents.setVisibility(View.VISIBLE);
                this.rvFriends.setVisibility(View.GONE);
                break;

            case R.id.friendslist:
                Toast.makeText(MainActivity.this, "You are now viewing your friends list.", Toast.LENGTH_SHORT).show();
                this.fabNewNote.setVisibility(View.GONE);
                this.fabNewItem.setVisibility(View.GONE);
                this.fabNewEvent.setVisibility(View.GONE);

                this.rvNotes.setVisibility(View.GONE);
                this.rvTodo.setVisibility(View.GONE);
                this.rvEvents.setVisibility(View.GONE);
                this.rvFriends.setVisibility(View.VISIBLE);
                break;

            case R.id.add:
                break;

            case R.id.settings:
                break;

            case R.id.logout:
                Toast.makeText(MainActivity.this, "Signed off successfully.", Toast.LENGTH_SHORT).show();
                finish();
                break;

            default:
                Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
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

        this.navigationView.setNavigationItemSelectedListener(this);

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

        // Initializes the floating action buttons depending on the menu selected.
        this.fabNewItem = findViewById(R.id.fab_newitem);
        this.fabNewEvent = findViewById(R.id.fab_newevent);

        // Recycler views for displaying their respective content.
        this.rvNotes = findViewById(R.id.rv_notes);
        this.rvTodo = findViewById(R.id.rv_todo);
        this.rvEvents = findViewById(R.id.rv_events);
        this.rvFriends = findViewById(R.id.rv_friends);

        /*
         * By default, the notes page is selected. So only components for the active
         * screen/menu will be visible. Other components will be rendered gone.
         */
        this.fabNewNote.setVisibility(View.VISIBLE);
        this.fabNewItem.setVisibility(View.GONE);
        this.fabNewEvent.setVisibility(View.GONE);

        this.rvNotes.setVisibility(View.VISIBLE);
        this.rvTodo.setVisibility(View.GONE);
        this.rvEvents.setVisibility(View.GONE);
        this.rvFriends.setVisibility(View.GONE);
    }
}