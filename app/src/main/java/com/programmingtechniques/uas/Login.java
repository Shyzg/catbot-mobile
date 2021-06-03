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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Button btnLogin, btnGoRegister;
    ImageView ivHero;
    TextView tvName, tvSlogan;
    TextInputLayout tilEmail, tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivHero = findViewById(R.id.imageHero);
        tvName = findViewById(R.id.textName);
        tvSlogan = findViewById(R.id.textSlogan);
        tilEmail = findViewById(R.id.loginEmail);
        tilPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnGoRegister = findViewById(R.id.buttonGoRegister);
    }

    private boolean validateEmail() {
        String Validate = tilEmail.getEditText().getText().toString();

        if (Validate.isEmpty()) {
            tilEmail.setError("Cannot be Empty");
            return false;
        } else {
            tilEmail.setError(null);
            tilEmail.setErrorEnabled(false);
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
        if (!validateEmail() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredEmail = tilEmail.getEditText().getText().toString().trim();
        final String userEnteredPassword = tilPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("catUsers");
        Query checkUser = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tilEmail.setError(null);
                    tilEmail.setErrorEnabled(false);

                    String passwordFromFB = snapshot.child(userEnteredPassword).child("password").getValue(String.class);
                    if (passwordFromFB.equals(userEnteredPassword)) {
                        tilEmail.setError(null);
                        tilEmail.setErrorEnabled(false);

                        String usernameFromFB = snapshot.child(userEnteredEmail).child("username").getValue(String.class);
                        String emailFromFB = snapshot.child(userEnteredEmail).child("email").getValue(String.class);

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
                    tilEmail.setError("No such User Exist");
                    tilEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callRegister(View view) {
        Intent intent = new Intent(Login.this, Register.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(ivHero, "catbot_image_hero");
        pairs[1] = new Pair<View, String>(tvName, "catbot_text_name");
        pairs[2] = new Pair<View, String>(tvSlogan, "catbot_text_slogan");
        pairs[3] = new Pair<View, String>(tilEmail, "catbot_input_email");
        pairs[4] = new Pair<View, String>(tilPassword, "catbot_input_password");
        pairs[5] = new Pair<View, String>(btnLogin, "catbot_button_loginregister");
        pairs[6] = new Pair<View, String>(btnGoRegister, "catbot_button_already");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }
}