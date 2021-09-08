package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {
    FloatingActionButton fabCancel;
    FloatingActionButton fabDraw;
    FloatingActionButton fabImage;
    FloatingActionButton fabPost;
    FloatingActionButton fabDelete;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String noteId;
    String noteText;
    String noteDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        this.noteId = getIntent().getStringExtra("id");
        this.noteText = getIntent().getStringExtra("note_texts");
        this.noteDate = getIntent().getStringExtra("create_time");
        //Log.d("ID", userId);
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.userId = fAuth.getCurrentUser().getUid();

        EditText note_data = findViewById(R.id.et_noteinput);
        note_data.setText(noteText);
        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, all buttons have no function
         * with the exception of the cancel/back button.
         */
        final EditText note_text=findViewById(R.id.et_noteinput);
        this.fabCancel = findViewById(R.id.fab_backevent);
        this.fabCancel.setOnClickListener(new View.OnClickListener() {
            FirebaseAuth fAuth;
            FirebaseFirestore fStore;
            String userId;
            @Override
            public void onClick(View v) {
                saveNotes(note_text.getText().toString());
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
                deleteNotes();
                Toast.makeText(AddNoteActivity.this, "Successfully deleted note.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveNotes(String note){
        DocumentReference documentReference = fStore.collection("users").document(userId);
        if(noteId.equals("null") && !note.equals("")){
            String uniqueID = UUID.randomUUID().toString();
            Date date = new Date();
            NoteModel newNote = new NoteModel(uniqueID, note, date.toString());
            documentReference.update("NoteList", FieldValue.arrayUnion(newNote));
        }
        else if(!note.equals("") && !note.equals(noteText)){
            Date date = new Date();
            NoteModel oldNote = new NoteModel(noteId, noteText, noteDate);
            documentReference.update("NoteList", FieldValue.arrayRemove(oldNote));
            NoteModel newNote = new NoteModel(noteId, note, date.toString());
            documentReference.update("NoteList", FieldValue.arrayUnion(newNote));
        }

    }

    private void deleteNotes(){
        DocumentReference documentReference = fStore.collection("users").document(userId);
        NoteModel oldNote = new NoteModel(noteId, noteText, noteDate);
        documentReference.update("NoteList", FieldValue.arrayRemove(oldNote));
    }
}
