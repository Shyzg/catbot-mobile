package com.programmingtechniques.uas.Users;

import androidx.annotation.NonNull;
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
import com.programmingtechniques.uas.Menu.Profile;
import com.programmingtechniques.uas.R;

public class Login extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Animation bottomAnim;
    ImageView ivHero;
    TextView tvNama, tvDeskripsi;
    TextInputLayout tilNamaPengguna, tilKataSandi;
    CheckBox cbIngatAku;
    Button btnMasuk, btnLupaKataSandi, btnCallRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Hooks
        ivHero = findViewById(R.id.imageHero);
        tvNama = findViewById(R.id.textNama);
        tvDeskripsi = findViewById(R.id.textDeskripsi);
        tilNamaPengguna = findViewById(R.id.inputLoginNamaPengguna);
        tilKataSandi = findViewById(R.id.inputLoginKataSandi);
        cbIngatAku = findViewById(R.id.checkboxIngatAku);
        btnLupaKataSandi = findViewById(R.id.buttonLupaKataSandi);
        btnMasuk = findViewById(R.id.buttonMasuk);
        btnCallRegister = findViewById(R.id.buttonCallRegister);
//        Panggil Firebase & Ambil Data Dari Table catUsers
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("catUsers");
//        Animation
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//        Set Animation
        tilNamaPengguna.setAnimation(bottomAnim);
        tilKataSandi.setAnimation(bottomAnim);
        cbIngatAku.setAnimation(bottomAnim);
        btnLupaKataSandi.setAnimation(bottomAnim);
        btnMasuk.setAnimation(bottomAnim);
        btnCallRegister.setAnimation(bottomAnim);
    }

    private boolean validasiSurel() {
        String Validate = tilNamaPengguna.getEditText().getText().toString();

        if (Validate.isEmpty()) {
            tilNamaPengguna.setError("Tidak Boleh Kosong");
            return false;
        } else {
            tilNamaPengguna.setError(null);
            tilNamaPengguna.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validasiKataSandi() {
        String Validate = tilKataSandi.getEditText().getText().toString();

        if (Validate.isEmpty()) {
            tilKataSandi.setError("Tidak Boleh Kosong");
            return false;
        } else {
            tilKataSandi.setError(null);
            tilKataSandi.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validasiSurel() | !validasiKataSandi()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredNamaPengguna = tilNamaPengguna.getEditText().getText().toString().trim();
        final String userEnteredPassword = tilKataSandi.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance().getReference("catUsers").orderByChild("namaPengguna").equalTo(userEnteredNamaPengguna);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tilNamaPengguna.setError(null);
                    tilNamaPengguna.setErrorEnabled(false);

                    String passwordFromFB = snapshot.child(userEnteredNamaPengguna).child("kataSandi").getValue(String.class);
                    if (passwordFromFB.equals(userEnteredPassword)) {
                        tilKataSandi.setError(null);
                        tilKataSandi.setErrorEnabled(false);

                        String namaPenggunaFromFB = snapshot.child(userEnteredNamaPengguna).child("namaPengguna").getValue(String.class);
                        String surelFromFB = snapshot.child(userEnteredNamaPengguna).child("surel").getValue(String.class);
                        String nomorHandphoneFromFB = snapshot.child(userEnteredNamaPengguna).child("nomorHandphone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Profile.class);

                        intent.putExtra("surel", surelFromFB);
                        intent.putExtra("namaPengguna", namaPenggunaFromFB);
                        intent.putExtra("nomorHandphone", nomorHandphoneFromFB);
                        intent.putExtra("kataSandi", passwordFromFB);

                        startActivity(intent);
                    } else {
                        tilKataSandi.setError("Kata Sandi Salah");
                        tilKataSandi.requestFocus();
                    }
                } else {
                    tilNamaPengguna.setError("Nama Pengguna Tidak Ditemukan");
                    tilNamaPengguna.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), Register.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textNama), "catbot_text_nama");
        pairs[2] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_deskripsi");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}