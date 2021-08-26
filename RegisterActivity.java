package com.mobdeve.s11.soriano.juancho.mco2memobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        this.initComponents();
    }

    private void initComponents() {
        this.btnCreate = findViewById(R.id.btn_createacc);
        this.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
