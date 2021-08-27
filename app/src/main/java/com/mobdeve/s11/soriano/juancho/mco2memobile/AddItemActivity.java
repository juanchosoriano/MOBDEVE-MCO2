package com.mobdeve.s11.soriano.juancho.mco2memobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddItemActivity extends AppCompatActivity {
    FloatingActionButton fabCancelItem;
    FloatingActionButton fabPostItem;
    FloatingActionButton fabDeleteItem;
    FloatingActionButton fabColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, all buttons have no function
         * with the exception of the cancel/back button.
         */
        this.fabCancelItem = findViewById(R.id.fab_backlist);
        this.fabCancelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Returning to your to-do list...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Every other FABs will display the same toast.
        this.fabPostItem = findViewById(R.id.fab_postitem);
        this.fabPostItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabDeleteItem = findViewById(R.id.fab_delitem);
        this.fabDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Successfully deleted list item.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        this.fabColor = findViewById(R.id.fab_color);
        this.fabColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
