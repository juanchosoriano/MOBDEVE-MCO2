package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.UUID;
import org.w3c.dom.Document;

public class AddNoteActivity extends AppCompatActivity {
    FloatingActionButton fabCancel;
    FloatingActionButton fabDraw;
    FloatingActionButton fabImage;
    FloatingActionButton fabClear;
    FloatingActionButton fabDelete;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    PaintView paintView;
    String userId;
    String noteId;
    String noteText;
    String noteDate;
    String noteImg;
    private Uri mImageUri;
    ImageView imgView;
    PaintView myCanvas;
    boolean toggleErase = false;
    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        try {
                            if(result.getData() != null) {
                                mImageUri = result.getData().getData();
                                Picasso.get().load(mImageUri).into(imgView);
                            }
                        } catch(Exception exception){
                            Log.d("TAG",""+exception.getLocalizedMessage());
                        }
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        this.noteId = getIntent().getStringExtra("id");
        this.noteText = getIntent().getStringExtra("note_texts");
        this.noteDate = getIntent().getStringExtra("create_time");
        this.imgView = findViewById(R.id.iv_noteimage);
        this.noteImg = getIntent().getStringExtra("note_image");
        if(noteImg == null){
            noteImg = "none";
        }
        //Log.d("ID", userId);
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.userId = fAuth.getCurrentUser().getUid();
        this.myCanvas = findViewById(R.id.pv_note);
        myCanvas.setVisibility(View.GONE);
        EditText note_data = findViewById(R.id.et_noteinput);
        note_data.setText(noteText);

        if(!noteImg.equals("none")){
            Log.d("NOTE_IMG", noteImg + "");
            String path = "images/" + userId + "-" + Uri.parse(noteImg).getLastPathSegment();
            Log.d("IMGPATH", path);

            FirebaseStorage.getInstance().getReference().child(path).getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(Task<Uri> task) {
                            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(imgView.getContext());
                            circularProgressDrawable.setCenterRadius(30);
                            Picasso.get()
                                    .load(task.getResult())
                                    .placeholder(circularProgressDrawable)
                                    .into(imgView);
                        }
                    });
        }


        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, all buttons have no function
         * with the exception of the cancel/back button.
         */
        final EditText note_text=findViewById(R.id.et_noteinput);
        this.fabCancel = findViewById(R.id.fab_back_note);
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
                if(myCanvas.getVisibility() == View.VISIBLE){
                    myCanvas.setVisibility(View.GONE);
                    fabDraw.setColorFilter(Color.WHITE);
                }
                else {
                    myCanvas.setVisibility(View.VISIBLE);
                    fabDraw.setColorFilter(Color.YELLOW);
                }

                //myCanvas = new PaintView(AddNoteActivity.this, null);
                //setContentView(myCanvas);

            }
        });

        this.fabImage = findViewById(R.id.fab_addimage);
        this.fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_OPEN_DOCUMENT);
                myActivityResultLauncher.launch(Intent.createChooser(i, "Select Picture"));
                //openFileChooser();
                //Toast.makeText(AddNoteActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabClear = findViewById(R.id.fab_clear);
        this.fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!toggleErase){
                    myCanvas.removeContents();
                    fabClear.setColorFilter(Color.YELLOW);

                }
                else{
                    myCanvas.drawMode();
                    fabClear.setColorFilter(Color.WHITE);
                }
                toggleErase = !toggleErase;
                //Toast.makeText(AddNoteActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        this.fabDelete = findViewById(R.id.fab_delete_note);
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
        Date date = new Date();
        if(mImageUri != null && !mImageUri.toString().equals(noteImg)){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(
                    "images/" + userId + "-" + mImageUri.getLastPathSegment()
            );

            storageReference.putFile(mImageUri);


        }
        if(noteId.equals("null") && !note.equals("")){
            String uniqueID = UUID.randomUUID().toString();
            if(mImageUri != null){
                noteImg = mImageUri.toString();
            }
            NoteModel newNote = new NoteModel(uniqueID, note, date.toString(), noteImg);
            documentReference.update("NoteList", FieldValue.arrayUnion(newNote));
        }
        else if((!note.equals("") && !note.equals(noteText)) || mImageUri != null){
            NoteModel oldNote = new NoteModel(noteId, noteText, noteDate, noteImg);
            documentReference.update("NoteList", FieldValue.arrayRemove(oldNote));
            if(mImageUri != null){
                noteImg = mImageUri.toString();
            }
            NoteModel newNote = new NoteModel(noteId, note, date.toString(), noteImg);
            documentReference.update("NoteList", FieldValue.arrayUnion(newNote));
        }

    }

    private void deleteNotes(){
        DocumentReference documentReference = fStore.collection("users").document(userId);
            NoteModel oldNote = new NoteModel(noteId, noteText, noteDate, noteImg);
            Log.d("OLDNOTE",noteId+noteText+noteDate+noteImg);
            documentReference.update("NoteList", FieldValue.arrayRemove(oldNote));

    }

//    private void openFileChooser(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        this.startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//        && data != null && data.getData() != null){
//            mImageUri = data.getData();
//            Picasso.with(this).load(mImageUri).into(mImageUri).into(mImageView);
//        }
//    }
}
