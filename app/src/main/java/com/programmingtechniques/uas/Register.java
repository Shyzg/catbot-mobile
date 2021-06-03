package com.programmingtechniques.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Button btnRegister, btnGoLogin;
    ImageView ivHero;
    TextView tvName, tvSlogan;
    TextInputLayout tilUsername, tilEmail, tilPassword;
    long AutoIncrement = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivHero = findViewById(R.id.imageHero);
        tvName = findViewById(R.id.textName);
        tvSlogan = findViewById(R.id.textSlogan);
        tilUsername = findViewById(R.id.registerUsername);
        tilEmail = findViewById(R.id.registerEmail);
        tilPassword = findViewById(R.id.registerPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        btnGoLogin = findViewById(R.id.buttonGoLogin);
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
    }

    public void callLogin(View view) {
        Intent intent = new Intent(Register.this, Login.class);

        Pair[] pairs = new Pair[8];
        pairs[0] = new Pair<View, String>(ivHero, "catbot_image_hero");
        pairs[1] = new Pair<View, String>(tvName, "catbot_text_name");
        pairs[2] = new Pair<View, String>(tvSlogan, "catbot_text_slogan");
        pairs[3] = new Pair<View, String>(tilPassword, "catbot_input_username");
        pairs[4] = new Pair<View, String>(tilEmail, "catbot_input_email");
        pairs[5] = new Pair<View, String>(tilPassword, "catbot_input_password");
        pairs[6] = new Pair<View, String>(btnRegister, "catbot_button_loginregister");
        pairs[7] = new Pair<View, String>(btnGoLogin, "catbot_button_already");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }
}