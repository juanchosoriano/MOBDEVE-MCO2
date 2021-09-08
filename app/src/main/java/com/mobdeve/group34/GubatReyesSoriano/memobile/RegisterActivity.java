package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etRegFirstName;
    EditText etRegLastName;
    EditText etRegMiddleInitial;
    EditText etRegDateOfBirth;
    EditText etRegEmail;
    EditText etRegPassword;
    EditText etRegConfirmPassword;
    Button btnCreate;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private static final String TAG = "Registration";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        etRegFirstName = findViewById(R.id.pt_firstname);
        etRegLastName = findViewById(R.id.pt_lastname);
        etRegMiddleInitial = findViewById(R.id.pt_initials);
        etRegDateOfBirth = findViewById(R.id.d_birthdate);
        etRegEmail = findViewById(R.id.em_regmail);
        etRegPassword = findViewById(R.id.pw_regpass);
        etRegConfirmPassword = findViewById(R.id.pw_confpass);
        btnCreate = findViewById(R.id.btn_createacc);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

//        if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            finish();
//        }

        btnCreate.setOnClickListener(view ->{
            createUser();
        });
    }

    private void createUser(){
        String fName =  etRegFirstName.getText().toString();
        String lName = etRegLastName.getText().toString();
        String mInitial = etRegMiddleInitial.getText().toString();
        String dateOfBirth = etRegDateOfBirth.getText().toString();
        String confPass = etRegConfirmPassword.getText().toString();
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();

        if (TextUtils.isEmpty(fName)){
            etRegFirstName.setError("First Name field must be filled out!");
            etRegFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lName)){
            etRegLastName.setError("Last Name field must be filled out!");
            etRegLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mInitial)){
            etRegMiddleInitial.setError("Middle Name field must be filled out!");
            etRegMiddleInitial.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(dateOfBirth)){
            etRegDateOfBirth.setError("Date of Birth field must be filled out!");
            etRegDateOfBirth.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)){
            etRegEmail.setError("Email field must be filled out!");
            etRegEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            etRegPassword.setError("Password field must be filled out!");
            etRegPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            etRegPassword.setError("Password field must be >= 6 characters!");
            etRegPassword.requestFocus();
            return;
        }
        if (!(TextUtils.equals(password, confPass))){
            etRegConfirmPassword.setError("Confirmation Password does not match with Password!");
            etRegConfirmPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    List<NoteModel> noteModelList = new ArrayList<>();
                    Date date = new Date();
                    noteModelList.add(new NoteModel("1", "Time to study!", date.toString()));
                    Map<String,Object> user = new HashMap<>();
                    user.put("FirstName", fName);
                    user.put("LastName", lName);
                    user.put("MiddleInitial", mInitial);
                    user.put("DateOfBirth", dateOfBirth);
                    user.put("Email", email);
                    user.put("Password", password);
                    user.put("ConfirmPassword", confPass);
                    user.put("NoteList", noteModelList);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "OnSuccess: user profile is created for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }
    }
}
