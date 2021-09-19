package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    FloatingActionButton fabCancelItem;
    FloatingActionButton fabPostItem;
    FloatingActionButton fabDeleteItem;
    FloatingActionButton fabColor;
    FloatingActionButton fabDate;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView tvDate;
    String setDate;
    String itemId;
    String itemText;
    Date newDate;
    Date oldDate;
    Boolean checked;
    String userId;
    int priority;
    int n_priority;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);
        this.itemId = getIntent().getStringExtra("todo_id");
        this.itemText = getIntent().getStringExtra("todo_item");
        this.checked = getIntent().getBooleanExtra("checked", false);
        this.priority = getIntent().getIntExtra("priority", 3);
        this.setDate = getIntent().getStringExtra("todo_date");
        if(this.setDate == null){
            this.setDate = (new Date()).toString();
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        try {
            newDate = sdf1.parse(setDate);
            this.setDate = new SimpleDateFormat("MMM dd yyyy").format(newDate);
            oldDate = newDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        n_priority = priority;
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
        this.tvDate = findViewById(R.id.tv_addtodo_date);
        this.tvDate.setText(setDate);
        this.fabDate = findViewById(R.id.fab_addDate);
        this.fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveItem(todo_text.getText().toString());
                //Toast.makeText(AddItemActivity.this, "Returning to your to-do list...", Toast.LENGTH_SHORT).show();
                //finish();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItemActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d("DATEPICKER", datePicker.toString());

                month = month + 1;
                Log.d("TAG", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;

                try {
                    newDate=new SimpleDateFormat("MM/dd/yyyy").parse(date);
                    setDate = new SimpleDateFormat("MMM dd yyyy").format(newDate);;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvDate.setText(setDate);
            }
        };
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
                if(n_priority == 3){
                    n_priority = 1;
                    Toast.makeText(AddItemActivity.this, "Color set to: Urgent Red!", Toast.LENGTH_SHORT).show();
                } else if (n_priority == 1){
                    n_priority = 2;
                    Toast.makeText(AddItemActivity.this, "Color set to: Mild Yellow!", Toast.LENGTH_SHORT).show();
                } else {
                    n_priority = 3;
                    Toast.makeText(AddItemActivity.this, "Color set to: Easy Green!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void saveItem(String item){
        DocumentReference documentReference = fStore.collection("users").document(userId);
        Log.d("NEW_PRIO", ""+n_priority );
        if(itemId.equals("null") && !item.equals("")){
            String uniqueID = UUID.randomUUID().toString();
            TodoModel newItem = new TodoModel(uniqueID, item, false, n_priority, newDate);
            documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
        }
        else if((!item.equals("") && !item.equals(itemText))||priority != n_priority || oldDate != newDate){
            TodoModel oldItem = new TodoModel(itemId, itemText, checked, priority, oldDate);
            documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
            TodoModel newItem = new TodoModel(itemId, item, false, n_priority, newDate);
            documentReference.update("ItemList", FieldValue.arrayUnion(newItem));
        }

    }

    private void deleteItem(){
        DocumentReference documentReference = fStore.collection("users").document(userId);
        TodoModel oldItem = new TodoModel(itemId, itemText, checked, priority,oldDate);
        documentReference.update("ItemList", FieldValue.arrayRemove(oldItem));
    }


}
