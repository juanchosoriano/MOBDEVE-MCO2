package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddFriendsActivity extends AppCompatActivity {
    FloatingActionButton fabCancelAdd;
    FloatingActionButton fabAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_activity);

        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, the 'add friend' button will not function
         * with the exception of the cancel/back button.
         */
        this.fabCancelAdd = findViewById(R.id.fab_backadd);
        this.fabCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFriendsActivity.this, "Going back to the menu...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        this.fabAddUser = findViewById(R.id.fab_adduser);
        this.fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFriendsActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
