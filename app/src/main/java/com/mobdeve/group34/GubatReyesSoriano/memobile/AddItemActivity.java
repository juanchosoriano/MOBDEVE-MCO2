package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.os.Bundle;
import android.util.Log;
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

public class AddItemActivity extends AppCompatActivity {
    FloatingActionButton fabCancelItem;
    FloatingActionButton fabPostItem;
    FloatingActionButton fabDeleteItem;
    FloatingActionButton fabColor;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String itemId;
    String itemText;
    Boolean checked;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);
        this.itemId = getIntent().getStringExtra("todo_id");
        this.itemText = getIntent().getStringExtra("todo_item");
        this.checked = getIntent().getBooleanExtra("checked", false);
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.userId = fAuth.getCurrentUser().getUid();
        Log.d("TEST", "testing reached");
        EditText todo_data = findViewById(R.id.et_listinput);
        todo_data.setText(itemText);

        this.initComponents();
    }

    private void initComponents() {
        /*
         * For the beta release, all buttons have no function
         * with the exception of the cancel/back button.
         */
        final EditText todo_text=findViewById(R.id.et_listinput);
        this.fabCancelItem = findViewById(R.id.fab_backlist);
        this.fabCancelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem(todo_text.getText().toString());
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
                deleteItem();
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

    private void saveItem(String item){
        DocumentReference documentReference = fStore.collection("users").document(userId);
        if(itemId.equals("null") && !item.equals("")){
            String uniqueID = UUID.randomUUID().toString();
            TodoModel newItem = new TodoModel(uniqueID, item, false);
            documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
        }
        else if(!item.equals("") && !item.equals(itemText)){
            TodoModel oldItem = new TodoModel(itemId, itemText, checked);
            documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
            TodoModel newItem = new TodoModel(itemId, item, false);
            documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
        }

    }

    private void deleteItem(){
        DocumentReference documentReference = fStore.collection("users").document(userId);
        TodoModel oldItem = new TodoModel(itemId, itemText);
        documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
    }


}
