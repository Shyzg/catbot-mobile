package com.programmingtechniques.uas.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.programmingtechniques.uas.Profile;
import com.programmingtechniques.uas.R;

public class Login extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Animation topAnim, bottomAnim;
    Button btnLogin, btnLupaKataSandi;
    ImageView ivHero;
    TextView tvName, tvSlogan;
    CheckBox cbRememberMe;
    TextInputLayout tilUsername, tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivHero = findViewById(R.id.imageHero);
        tvName = findViewById(R.id.textName);
        tvSlogan = findViewById(R.id.textSlogan);
        tilUsername = findViewById(R.id.loginEmail);
        tilPassword = findViewById(R.id.loginPassword);
        cbRememberMe = findViewById(R.id.checkboxIngatAku);
        btnLupaKataSandi = findViewById(R.id.buttonLupaKataSandi);
        btnLogin = findViewById(R.id.buttonLogin);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("catUsers");

        animationLayout();
    }

    private boolean validateUsername() {
        String Validate = tilUsername.getEditText().getText().toString();

        if (Validate.isEmpty()) {
            tilUsername.setError("Cannot be Empty");
            return false;
        } else {
            tilUsername.setError(null);
            tilUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String Validate = tilPassword.getEditText().getText().toString();

        if (Validate.isEmpty()) {
            tilPassword.setError("Cannot be Empty");
            return false;
        } else {
            tilPassword.setError(null);
            tilPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUsers(View view) {
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = tilUsername.getEditText().getText().toString().trim();
        final String userEnteredPassword = tilPassword.getEditText().getText().toString().trim();

        reference = FirebaseDatabase.getInstance().getReference("catUsers");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tilUsername.setError(null);
                    tilUsername.setErrorEnabled(false);

                    String passwordFromFB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromFB.equals(userEnteredPassword)) {
                        tilUsername.setError(null);
                        tilUsername.setErrorEnabled(false);

                        String usernameFromFB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String emailFromFB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        intent.putExtra("username", usernameFromFB);
                        intent.putExtra("email", emailFromFB);
                        intent.putExtra("password", passwordFromFB);

                        startActivity(intent);
                    } else {
                        tilPassword.setError("Wrong Password");
                        tilPassword.requestFocus();
                    }
                } else {
                    tilUsername.setError("No such User Exist");
                    tilUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void animationLayout() {
//        Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//        Set Animation
        ivHero.setAnimation(topAnim);
        tvName.setAnimation(topAnim);
        tvSlogan.setAnimation(topAnim);
        tilUsername.setAnimation(topAnim);
        tilPassword.setAnimation(topAnim);
        cbRememberMe.setAnimation(bottomAnim);
        btnLupaKataSandi.setAnimation(bottomAnim);
        btnLogin.setAnimation(bottomAnim);
    }
}