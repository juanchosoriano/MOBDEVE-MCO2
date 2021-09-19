package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddFriendsActivity extends AppCompatActivity {
    EditText EtEmailAdd;
    FloatingActionButton fabCancelAdd;
    FloatingActionButton fabAddUser;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    public String docuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_activity);
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.userId = fAuth.getCurrentUser().getUid();
        this.EtEmailAdd = findViewById(R.id.et_emailadd);
        this.initComponents(this.userId);
    }

    private void initComponents(String userId) {
        /*
         * For the beta release, the 'add friend' button will not function
         * with the exception of the cancel/back button.
         */
        this.fabCancelAdd = findViewById(R.id.fab_backadd);
        this.fabCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFriendsActivity.this, "Going Back to Menu...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        this.fabAddUser = findViewById(R.id.fab_adduser);
        this.fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(new friendCallback(){
                    public void onCallback(String docId) {
                        Map<String, Object> friends = new HashMap<>();
                        String userID = userId;
                        friends.put("Friend ID", docuId);
                        friends.put("User ID", userId);

                        fStore.collection("friends")
                                .add(friends)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(AddFriendsActivity.this, "Successfully Added Friend! Going Back to Menu...", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddFriendsActivity.this, "Failed to Add Friend! Going Back to Menu...", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }

            public void readData(friendCallback myCallback) {
                String friendId = EtEmailAdd.getText().toString();

                fStore.collection("users")
                        .whereEqualTo("Email", friendId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        docuId = document.getId();
                                        Toast.makeText(AddFriendsActivity.this, docuId, Toast.LENGTH_SHORT).show();
                                        myCallback.onCallback(docuId);
                                        break;
                                    }
                                } else {
                                    Log.d("Error", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }

        });

    }



}
