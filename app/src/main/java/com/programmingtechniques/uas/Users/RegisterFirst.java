package com.programmingtechniques.uas.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programmingtechniques.uas.R;

public class RegisterFirst extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Animation topAnim, bottomAnim;
    Button btnRegister;
    ImageView ivHero;
    TextView tvName, tvSlogan;
    TextInputLayout tilUsername, tilEmail, tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivHero = findViewById(R.id.imageHero);
        tvName = findViewById(R.id.textName);
        tvSlogan = findViewById(R.id.textSlogan);
        tilUsername = findViewById(R.id.registerUsername);
        tilEmail = findViewById(R.id.registerEmail);
        tilPassword = findViewById(R.id.registerPassword);
        btnRegister = findViewById(R.id.buttonRegister);

        animationLayout();
    }

    private boolean validateUsername() {
        String Validate = tilUsername.getEditText().getText().toString();
        String usernameValidate = "\\A\\w{4,12}\\z";

        if (Validate.isEmpty()) {
            tilUsername.setError("Cannot be Empty");
            return false;
        } else if (Validate.length() > 12) {
            tilUsername.setError("Username too Long");
            return false;
        } else if (!Validate.matches(usernameValidate)) {
            tilUsername.setError("Character are Not Allowed");
            return false;
        } else {
            tilUsername.setError(null);
            tilUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String Validate = tilEmail.getEditText().getText().toString();
        String emailPatterm = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]";

        if (Validate.isEmpty()) {
            tilEmail.setError("Cannot be Empty");
            return false;
        } else if (!Validate.matches(emailPatterm)) {
            tilEmail.setError("Invalid Email Address");
            return false;
        } else {
            tilEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String Validate = tilPassword.getEditText().getText().toString();
        String passwordValidate = "^" + "(?=.*[a-zA-Z0-9])" + "(?=.*[!@#$%^&*+=])" + ".{8,}" + "$";

        if (Validate.isEmpty()) {
            tilPassword.setError("Cannot be Empty");
            return false;
        } else if (!Validate.matches(passwordValidate)) {
            tilPassword.setError("Password too Weak");
            return false;
        } else {
            tilPassword.setError(null);
            return true;
        }
    }

    public void registerUsers(View view) {
        if (!validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("catUsers");

        String Username = tilUsername.getEditText().getText().toString();
        String Email = tilEmail.getEditText().getText().toString();
        String Password = tilPassword.getEditText().getText().toString();
        UserHelper helper = new UserHelper(Username, Email, Password);

        reference.child(Username).setValue(helper);

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    public void animationLayout() {
//        Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//        Set Animation
        ivHero.setAnimation(topAnim);
        tvName.setAnimation(topAnim);
        tvSlogan.setAnimation(topAnim);
        tilUsername.setAnimation(bottomAnim);
        tilEmail.setAnimation(bottomAnim);
        tilPassword.setAnimation(bottomAnim);
        btnRegister.setAnimation(bottomAnim);
    }
}