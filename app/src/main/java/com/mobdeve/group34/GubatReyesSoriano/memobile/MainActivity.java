package com.mobdeve.group34.GubatReyesSoriano.memobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * I think the Main Activity can host all viewing features:
 * - Notes
 * - Items in to-do list.
 * - Calendar of events.
 * - Friends list.
 * We'll just change the adapters depending on the one being viewed(?)
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NoteClickListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle abdToggle;
    NavigationView navigationView;
    FloatingActionButton fabNewNote;
    FloatingActionButton fabNewItem;
    FloatingActionButton fabNewEvent;

    FloatingActionButton fabScheduled;
    boolean toggleScheduled;

    RecyclerView rvNotes;
    RecyclerView rvTodo;
    RecyclerView rvEvents;
    RecyclerView rvFriends;
    List<NoteModel> noteModels = new ArrayList<>();


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView firstName, email;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        firstName = header.findViewById(R.id.tv_username);
        email = header.findViewById(R.id.tv_usermail);

        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        this.userId = fAuth.getCurrentUser().getUid();

        RecyclerView noteList = findViewById(R.id.rv_notes);
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                firstName.setText(documentSnapshot.getString("FirstName"));
                email.setText(documentSnapshot.getString("Email"));
            }
        });
        /*
        RecyclerView noteList = findViewById(R.id.rv_notes);
        noteModels.add(new NoteModel("1", "Dummy data 1", "July 24,2021"));
        noteModels.add(new NoteModel("2", "Dummy data 2", "May 20,2021"));
        noteModels.add(new NoteModel("3", "Dummy data 3", "August 26,2021"));
        NoteAdaptor noteAdaptor = new NoteAdaptor(noteModels, MainActivity.this);
        noteList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        noteList.setAdapter(noteAdaptor);
        */
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
                this.fabScheduled.setVisibility(View.GONE);

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
                this.fabScheduled.setVisibility(View.VISIBLE);

                this.rvNotes.setVisibility(View.GONE);
                this.rvTodo.setVisibility(View.VISIBLE);
                this.rvEvents.setVisibility(View.GONE);
                this.rvFriends.setVisibility(View.GONE);
                break;

            case R.id.events:
                Toast.makeText(MainActivity.this, "You are now viewing your events calendar.", Toast.LENGTH_SHORT).show();
                this.fabNewNote.setVisibility(View.GONE);
                this.fabNewItem.setVisibility(View.GONE);
                this.fabNewEvent.setVisibility(View.VISIBLE);
                this.fabScheduled.setVisibility(View.GONE);

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
                this.fabScheduled.setVisibility(View.GONE);

                this.rvNotes.setVisibility(View.GONE);
                this.rvTodo.setVisibility(View.GONE);
                this.rvEvents.setVisibility(View.GONE);
                this.rvFriends.setVisibility(View.VISIBLE);
                break;

            case R.id.add:
                Intent addActivity = new Intent (MainActivity.this, AddFriendsActivity.class);
                startActivity(addActivity);
                break;

            case R.id.settings:
                Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                Toast.makeText(MainActivity.this, "Signed off successfully.", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

            default:
                Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private void initComponents() {

        Toolbar toolbar = findViewById(R.id.tb_toolbarevent);
        setSupportActionBar(toolbar);

        /*
         * While in-beta, this will always toggle to false on startup.
         * Will be changed to retain the status on closing/logout.
         */
        this.toggleScheduled = false;

        this.drawerLayout = findViewById(R.id.drawer);
        this.navigationView = findViewById(R.id.nav_view);
        this.abdToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        this.drawerLayout.addDrawerListener(abdToggle);
        this.abdToggle.setDrawerIndicatorEnabled(true);
        this.abdToggle.syncState();

        this.navigationView.setNavigationItemSelectedListener(this);

        /*
        * Button initiator. By default, the note screen is the first to be shown on start up.
        * The buttons will either be set to visible or gone depending on which screen is active.
        */
        this.fabNewNote = findViewById(R.id.fab_newnote);
        this.fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent (MainActivity.this, AddNoteActivity.class);
                regIntent.putExtra("id", "null");
                //regIntent.putExtra("note_texts", "null");
                regIntent.putExtra("create_time", "null");
                startActivity(regIntent);
            }
        });

        this.fabNewItem = findViewById(R.id.fab_newitem);
        this.fabNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemIntent = new Intent (MainActivity.this, AddItemActivity.class);
                startActivity(itemIntent);
            }
        });

        this.fabNewEvent = findViewById(R.id.fab_newevent);
        this.fabNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eveIntent = new Intent (MainActivity.this, AddEventActivity.class);
                startActivity(eveIntent);
            }
        });

        // Switching between a scheduled or unscheduled list.
        this.fabScheduled = findViewById(R.id.fab_togglesched);
        this.fabScheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!toggleScheduled) {
                    fabScheduled.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.scheduled));
                    toggleScheduled = true;
                    Toast.makeText(MainActivity.this, "To-do list is now SCHEDULED.", Toast.LENGTH_SHORT).show();
                } else if (toggleScheduled) {
                    fabScheduled.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unscheduled));
                    toggleScheduled = false;
                    Toast.makeText(MainActivity.this, "To-do list is now UNSCHEDULED.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        this.fabScheduled.setVisibility(View.GONE);

        this.rvNotes.setVisibility(View.VISIBLE);
        this.rvTodo.setVisibility(View.GONE);
        this.rvEvents.setVisibility(View.GONE);
        this.rvFriends.setVisibility(View.GONE);
    }

    public  void onResume() {
        super.onResume();
        viewNotes();
    }

    public void viewNotes(){

        RecyclerView noteList = findViewById(R.id.rv_notes);
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                List<HashMap<String, String>> noteModelMap = (List<HashMap<String, String>>) documentSnapshot.get("NoteList");
                //Log.d("notemodel", "notemodel" + noteModelMap.get(0).get("id"));
                noteModels.clear();
                for (int i = 0; i<noteModelMap.size();i++){
                    //Log.d("notemodelsMap", "notemodelsMap: " + noteModelMap.get(i).get("note_data"));
                    noteModels.add(new NoteModel(noteModelMap.get(i).get("id"), noteModelMap.get(i).get("note_data"), noteModelMap.get(i).get("created_at")));
                    //Log.d("notemodel", "notemodel" + noteModels.size());
                }
                NoteAdaptor noteAdaptor = new NoteAdaptor(noteModels, MainActivity.this);
                noteList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                noteList.setAdapter(noteAdaptor);
            }
        });
    }
    @Override
    public void onClickItem(NoteModel noteModel) {
        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
        intent.putExtra("id", noteModel.getId());
        intent.putExtra("note_texts", noteModel.getNote_data());
        intent.putExtra("create_time", noteModel.getCreated_at());
        startActivity(intent);
    }



}