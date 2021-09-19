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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*
 * I think the Main Activity can host all viewing features:
 * - Notes
 * - Items in to-do list.
 * - Calendar of events.
 * - Friends list.
 * We'll just change the adapters depending on the one being viewed(?)
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NoteClickListener, TodoClickListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle abdToggle;
    NavigationView navigationView;
    FloatingActionButton fabNewNote;
    FloatingActionButton fabNewItem;
    FloatingActionButton fabNewEvent;

    FloatingActionButton fabScheduled;
    boolean toggleScheduled = true;

    private SharedPreferences sp;


    RecyclerView rvNotes;
    RecyclerView rvTodo;
    RecyclerView rvEvents;
    RecyclerView rvFriends;
    List<NoteModel> noteModels = new ArrayList<>();
    List<TodoModel> todoModels = new ArrayList<>();


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
        initComponents();
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
                itemIntent.putExtra("todo_id", "null");
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
                SharedPreferences.Editor spEditor;
                spEditor = sp.edit();
                if(!toggleScheduled) {
                    fabScheduled.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.scheduled));
                    toggleScheduled = true;

                    viewTodo();
                    Toast.makeText(MainActivity.this, "To-do list is now SCHEDULED.", Toast.LENGTH_SHORT).show();
                    spEditor.putBoolean(Keys.SCHEDULED.name(), true);
                } else if (toggleScheduled) {
                    fabScheduled.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unscheduled));
                    toggleScheduled = false;
                    viewTodo();
                    Toast.makeText(MainActivity.this, "To-do list is now UNSCHEDULED.", Toast.LENGTH_SHORT).show();
                    spEditor.putBoolean(Keys.SCHEDULED.name(), false);
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
        viewTodo();
        this.sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.toggleScheduled = sp.getBoolean(Keys.SCHEDULED.name(), true);
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
                if(noteModelMap != null) {
                    for (int i = 0; i < noteModelMap.size(); i++) {
                        //Log.d("notemodelsMap", "notemodelsMap: " + noteModelMap.get(i).get("note_data"));
                        noteModels.add(new NoteModel(noteModelMap.get(i).get("id"), noteModelMap.get(i).get("note_data"), noteModelMap.get(i).get("created_at"), noteModelMap.get(i).get("imgUri")));
                        //Log.d("notemodel", "notemodel" + noteModels.size());
                    }
                }
                NoteAdaptor noteAdaptor = new NoteAdaptor(noteModels, MainActivity.this);
                noteList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                noteList.setAdapter(noteAdaptor);
            }
        });
    }

    public void viewTodo(){

        RecyclerView todoList = findViewById(R.id.rv_todo);
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                List<HashMap<String, String>> todoModelMap = (List<HashMap<String, String>>) documentSnapshot.get("ItemList");
                List<HashMap<String, Boolean>> todoModelMap_c = (List<HashMap<String, Boolean>>) documentSnapshot.get("ItemList");
                List<HashMap<String, Long>> todoModelMap_p = (List<HashMap<String, Long>>) documentSnapshot.get("ItemList");
                List<HashMap<String, Timestamp>> todoModelMap_s = (List<HashMap<String, Timestamp>>) documentSnapshot.get("ItemList");
//                //Log.d("notemodel", "notemodel" + noteModelMap.get(0).get("id"));
                todoModels.clear();
                boolean check_value = true;
                Log.d("TODO_COUNT", ""+todoModelMap.size());
                if(todoModelMap != null){
                    for (int i = 0; i<todoModelMap.size();i++){

//                        if(todoModelMap.get(i).get("checked").equals("True")){
//                            todoModels.add(new TodoModel(todoModelMap.get(i).get("id"), todoModelMap.get(i).get("todo_Text")));
//                        }
//                        else{
                        Date fetched_date = todoModelMap_s.get(i).get("todo_date").toDate();
                        Log.d("PRIOR_VAL", "" + todoModelMap_p.get(i).get("priority").intValue());
                        todoModels.add(new TodoModel(todoModelMap.get(i).get("id"), todoModelMap.get(i).get("todo_Text"), todoModelMap_c.get(i).get("checked"), todoModelMap_p.get(i).get("priority").intValue(), fetched_date));
//                        }

                        //Log.d("notemodelsMap", "notemodelsMap: " + noteModelMap.get(i).get("note_data"));

                        //Log.d("notemodel", "notemodel" + noteModels.size());
                    }
                    if(!toggleScheduled){
                        for (int i = 0; i < todoModels.size() - 1; i++) {
                            int m = i;
                            for (int j = i + 1; j < todoModels.size(); j++) {
                                if (todoModels.get(m).getTodo_date().before(todoModels.get(j).getTodo_date()))
                                    m = j;
                            }
                            //swapping elements at position i and m
                            TodoModel temp = todoModels.get(i);
                            todoModels.set(i, todoModels.get(m));
                            todoModels.set(m, temp);
                        }
                    }else {
                        for (int i = 0; i < todoModels.size() - 1; i++) {
                            int m = i;
                            for (int j = i + 1; j < todoModels.size(); j++) {
                                if (todoModels.get(m).getPriority() > todoModels.get(j).getPriority())
                                    m = j;
                            }
                            //swapping elements at position i and m
                            TodoModel temp = todoModels.get(i);
                            todoModels.set(i, todoModels.get(m));
                            todoModels.set(m, temp);
                        }
                    }
                }



                //todoModels.add(new TodoModel("123", "Testing"));
                TodoAdaptor todoAdaptor = new TodoAdaptor(todoModels, MainActivity.this);
                todoList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                todoList.setAdapter(todoAdaptor);
            }
        });
    }


    @Override
    public void onClickItem(NoteModel noteModel) {
        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
        intent.putExtra("id", noteModel.getId());
        intent.putExtra("note_texts", noteModel.getNote_data());
        intent.putExtra("create_time", noteModel.getCreated_at());
        intent.putExtra("note_image", noteModel.getImgUri());
        startActivity(intent);
    }

    @Override
    public void onClickItem(TodoModel todoModel) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra("todo_id", todoModel.getId());
        intent.putExtra("todo_item", todoModel.getTodo_Text());
        intent.putExtra("checked", todoModel.isChecked());
        intent.putExtra("priority", todoModel.getPriority());
        intent.putExtra("todo_date", todoModel.getTodo_date().toString());
        startActivity(intent);
    }



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        CheckBox checkBox = ((CheckBox) view);
        boolean checked = ((CheckBox) view).isChecked();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        String cb_message = checkBox.getText().toString();

        for(int i = 0; i < todoModels.size(); i++){
            if(cb_message.equals(todoModels.get(i).getTodo_Text())){
                TodoModel todo_item = todoModels.get(i);
                if (checked){
                    Log.d("ONCHECK", view.getId() + "");
                    todo_item.setChecked(true);

                    // Log.d("GETSTRING", view.get)
                    //TodoModel oldItem = new TodoModel(view.getId());
//                    documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
//                    TodoModel newItem = new TodoModel(itemId, item, checked);
//                    documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
                }
                else {
                    todo_item.setChecked(false);
//                    TodoModel oldItem = new TodoModel(itemId, itemText, checked);
//                    documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
//                    TodoModel newItem = new TodoModel(itemId, item, checked);
//                    documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
                }
                break;
            }
        }
        //DocumentReference documentReference = fStore.collection("users").document(userId);
//
        documentReference.update("ItemList", todoModels);
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.cb_item:
//                if (checked){
//                    Log.d("ONCHECK", view.getId() + "");
//
//                   // Log.d("GETSTRING", view.get)
//                      //TodoModel oldItem = new TodoModel(view.getId());
////                    documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
////                    TodoModel newItem = new TodoModel(itemId, item, checked);
////                    documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
//                }
//            else {
////                    TodoModel oldItem = new TodoModel(itemId, itemText, checked);
////                    documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
////                    TodoModel newItem = new TodoModel(itemId, item, checked);
////                    documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
//                }
//                break;
//        }
    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        DocumentReference documentReference = fStore.collection("users").document(userId);
//
//        documentReference.update("ItemList", todoModels);
//    }

}