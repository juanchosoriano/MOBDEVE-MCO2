package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEventActivity extends AppCompatActivity {
    FloatingActionButton fabAddEvent;
    FloatingActionButton fabDelEvent;
    FloatingActionButton fabBackEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);

        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, all buttons have no function
         * with the exception of the cancel/back button.
         */
        this.fabBackEvent = findViewById(R.id.fab_backevent);
        this.fabBackEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEventActivity.this, "Returning to your calendar...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Every other FABs will display the same toast.
        this.fabAddEvent = findViewById(R.id.fab_addevent);
        this.fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEventActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabDelEvent = findViewById(R.id.fab_delevent);
        this.fabDelEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEventActivity.this, "Successfully deleted event from calendar.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
