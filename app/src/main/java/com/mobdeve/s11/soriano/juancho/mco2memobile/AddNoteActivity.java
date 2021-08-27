package com.mobdeve.s11.soriano.juancho.mco2memobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNoteActivity extends AppCompatActivity {
    FloatingActionButton fabCancel;
    FloatingActionButton fabDraw;
    FloatingActionButton fabImage;
    FloatingActionButton fabPost;
    FloatingActionButton fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        String id = getIntent().getStringExtra("id");
        String noteText = getIntent().getStringExtra("note_texts");
        String noteDate = getIntent().getStringExtra("create_time");

        EditText note_data = findViewById(R.id.et_noteinput);
        note_data.setText(noteText);
        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, all buttons have no function
         * with the exception of the cancel/back button.
         */
        this.fabCancel = findViewById(R.id.fab_backevent);
        this.fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Returning to your notes...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Every other FABs will display the same toast.
        this.fabDraw = findViewById(R.id.fab_draw);
        this.fabDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabImage = findViewById(R.id.fab_addimage);
        this.fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabPost = findViewById(R.id.fab_addevent);
        this.fabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabDelete = findViewById(R.id.fab_delevent);
        this.fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Successfully deleted note.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
