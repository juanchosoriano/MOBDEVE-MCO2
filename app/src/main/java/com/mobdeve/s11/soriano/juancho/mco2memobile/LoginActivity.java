package com.mobdeve.s11.soriano.juancho.mco2memobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        this.initComponents();
    }

    private void initComponents() {
        /*
        * For the beta release, the login button will ignore inputs
        * and lead to the main activity. Will be modified to ask for inputs.
        */
        this.btnLogin = findViewById(R.id.btn_login);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent (LoginActivity.this, MainActivity.class);
                startActivity(logIntent);
            }
        });

        /*
         * The register button will lead to the registration page.
         * Registration page is non-functional for the beta demo.
         */
        this.btnRegister = findViewById(R.id.btn_register);
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent (LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });
    }
}
