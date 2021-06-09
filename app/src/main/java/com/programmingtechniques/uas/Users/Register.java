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
import com.programmingtechniques.uas.R;

public class Register extends AppCompatActivity {
    Animation bottomAnim;
    ImageView ivHero;
    TextView tvNama, tvDeskripsi;
    TextInputLayout tilNamaPengguna, tilSurel, tilKataSandi, tilNomorHandphone;
    Button btnLanjut, btnCallLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Hooks
        ivHero = findViewById(R.id.imageHero);
        tvNama = findViewById(R.id.textNama);
        tvDeskripsi = findViewById(R.id.textDeskripsi);
        tilNamaPengguna = findViewById(R.id.inputRegisterNamaPengguna);
        tilSurel = findViewById(R.id.inputRegisterSurel);
        tilKataSandi = findViewById(R.id.inputRegisterKataSandi);
        tilNomorHandphone = findViewById(R.id.inputRegisterNomorHandphone);
        btnLanjut = findViewById(R.id.buttonKirimKode);
        btnCallLogin = findViewById(R.id.buttonCallLogin);
//        Animation
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//        Set Animation
        tilNamaPengguna.setAnimation(bottomAnim);
        tilSurel.setAnimation(bottomAnim);
        tilKataSandi.setAnimation(bottomAnim);
        tilNomorHandphone.setAnimation(bottomAnim);
        btnLanjut.setAnimation(bottomAnim);
        btnCallLogin.setAnimation(bottomAnim);
    }

    private boolean validasiNamaPengguna() {
        String Validate = tilNamaPengguna.getEditText().getText().toString().trim();
        String usernameValidate = "\\A\\w{4,12}\\z";

        if (Validate.isEmpty()) {
            tilNamaPengguna.setError("Tidak Boleh Kosong");
            return false;
        } else if (Validate.length() > 12) {
            tilNamaPengguna.setError("Nama Pengguna Terlalu Panjang");
            return false;
        } else if (!Validate.matches(usernameValidate)) {
            tilNamaPengguna.setError("Huruf Tidak Diperbolehkan");
            return false;
        } else {
            tilNamaPengguna.setError(null);
            tilNamaPengguna.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validasiSurel() {
        String Validate = tilSurel.getEditText().getText().toString().trim();
        String emailPatterm = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (Validate.isEmpty()) {
            tilSurel.setError("Tidak Boleh Kosong");
            return false;
        } else if (!Validate.matches(emailPatterm)) {
            tilSurel.setError("Alamat Surel Tidak Sesuai");
            return false;
        } else {
            tilSurel.setError(null);
            return true;
        }
    }

    private boolean validasiKataSandi() {
        String Validate = tilKataSandi.getEditText().getText().toString().trim();
        String passwordValidate = "^" + "(?=.*[a-zA-Z0-9])" + "(?=.*[!@#$%^&*+=])" + ".{8,}" + "$";

        if (Validate.isEmpty()) {
            tilKataSandi.setError("Tidak Boleh Kosong");
            return false;
        } else if (!Validate.matches(passwordValidate)) {
            tilKataSandi.setError("Kata Sandi Lemah");
            return false;
        } else {
            tilKataSandi.setError(null);
            return true;
        }
    }

    private boolean validasiNomorHandphone() {
        String Validate = tilNomorHandphone.getEditText().getText().toString().trim();

        if (Validate.isEmpty()) {
            tilNomorHandphone.setError("Masukkan Nomor Handphone");
            return false;
        } else {
            tilNomorHandphone.setError(null);
            return true;
        }
    }

    public void callLanjut(View view) {
        if (!validasiNamaPengguna() | !validasiSurel() | !validasiKataSandi() | !validasiNomorHandphone()) {
            return;
        }
//        Bungkus/Simpan Sementara
//        Data Yang Didapat Untuk Memasukkan Kedalam Database Di Activity Selanjutnya
        String tNamaPengguna = tilNamaPengguna.getEditText().getText().toString().trim();
        String tSurel = tilSurel.getEditText().getText().toString().trim();
        String tKataSandi = tilKataSandi.getEditText().getText().toString().trim();
        String _tAmbilNomorHandphone = tilNomorHandphone.getEditText().getText().toString().trim();
        if (_tAmbilNomorHandphone.charAt(0) == '0') {
            _tAmbilNomorHandphone = _tAmbilNomorHandphone.substring(1);
        }
        final String tNomorHandphone = "+62" + _tAmbilNomorHandphone;

        Bundle bundle = new Bundle();
        bundle.putString("namapengguna", tNamaPengguna);
        bundle.putString("surel", tSurel);
        bundle.putString("katasandi", tKataSandi);
        bundle.putString("nomorhandphone", tNomorHandphone);

        Intent intent = new Intent(getApplicationContext(), Verification.class);
        intent.putExtras(bundle);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textNama), "catbot_text_nama");
        pairs[2] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_deskripsi");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void callLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textNama), "catbot_text_nama");
        pairs[2] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_deskripsi");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}