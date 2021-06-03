package com.programmingtechniques.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {
    TextInputLayout tilUsername, tilEmail, tilPassword;
    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tilUsername = findViewById(R.id.profileInputUsername);
        tilEmail = findViewById(R.id.profileInputEmail);
        tilPassword = findViewById(R.id.profileInputPassword);
        tvUsername = findViewById(R.id.profileShowUsername);

        showUserData();
    }

    public void showUserData() {
        Intent intent = getIntent();
        String userUsername = intent.getStringExtra("username");
        String userEmail = intent.getStringExtra("email");
        String userPassword = intent.getStringExtra("password");

        tvUsername.setText(userUsername);
        tilUsername.getEditText().setText(userUsername);
        tilEmail.getEditText().setText(userEmail);
        tilPassword.getEditText().setText(userPassword);
    }
}