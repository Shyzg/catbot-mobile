package com.programmingtechniques.uas.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programmingtechniques.uas.R;

import java.text.MessageFormat;

public class Profile extends AppCompatActivity {
    ImageView ivHero;
    TextInputLayout tilNamaPengguna, tilSurel, tilKataSandi, tilNomorHandphone;
    TextView tvNama;
    String namaPengguna, surel, kataSandi, nomorHandphone;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        databaseReference = FirebaseDatabase.getInstance().getReference("catUsers");
        Intent intent = getIntent();
//        Hooks
        ivHero = findViewById(R.id.imageHero);
        tvNama = findViewById(R.id.textNama);
        tilNamaPengguna = findViewById(R.id.profileInputNamaPengguna);
        tilSurel = findViewById(R.id.profileInputSurel);
        tilKataSandi = findViewById(R.id.profileInputKataSandi);
        tilNomorHandphone = findViewById(R.id.profileInputNomorHandphone);
//        Dapetin Data Dari Firebase
        namaPengguna = intent.getStringExtra("namaPengguna");
        surel = intent.getStringExtra("surel");
        kataSandi = intent.getStringExtra("kataSandi");
        nomorHandphone = intent.getStringExtra("nomorHandphone");
//        Munculin Data Dari Firebase
        tvNama.setText(MessageFormat.format("Hai {0}! Kamu Dapat Merubah Data Pada Akunmu Disini.", namaPengguna));
        tilNamaPengguna.getEditText().setText(namaPengguna);
        tilSurel.getEditText().setText(surel);
        tilKataSandi.getEditText().setText(kataSandi);
        tilNomorHandphone.getEditText().setText(nomorHandphone);
    }

    public void callUbah(View view) {
        if (ubahSurel() || ubahKataSandi()) {
            Toast.makeText(this, "Data Telah Diubah", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Kamu Belum Melakukan Perubahan", Toast.LENGTH_LONG).show();
        }
    }

    private boolean ubahSurel() {
        if (!surel.equals(tilSurel.getEditText().getText().toString())) {
            databaseReference.child(namaPengguna).child("surel").setValue(tilSurel.getEditText().getText().toString());
            surel = tilSurel.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean ubahKataSandi() {
        if (!kataSandi.equals(tilKataSandi.getEditText().getText().toString())) {
            databaseReference.child(namaPengguna).child("kataSandi").setValue(tilKataSandi.getEditText().getText().toString());
            kataSandi = tilKataSandi.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void callHome(View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textNama), "catbot_text_nama");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Profile.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}