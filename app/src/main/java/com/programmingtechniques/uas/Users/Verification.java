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
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programmingtechniques.uas.R;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    FirebaseAuth mAuth;
    PinView pvDariUser;
    String verifikasiId;
    TextView tvNomorHandphoneUser;
    String namaPengguna, surel, kataSandi, nomorHandphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
//        Ambil Data Yang Udah Dimasukin Dari Register Activity
        Intent intent = getIntent();
        namaPengguna = intent.getStringExtra("namapengguna");
        surel = intent.getStringExtra("surel");
        kataSandi = intent.getStringExtra("katasandi");
        nomorHandphone = intent.getStringExtra("nomorhandphone");
//        Ambil Kode Yang Dimasukin Pengguna Untuk Di Validasi
        pvDariUser = findViewById(R.id.pinViewKode);
        tvNomorHandphoneUser = findViewById(R.id.textVerificationNomorHandphone);
        tvNomorHandphoneUser.setText(nomorHandphone);
        kirimVerifikasiKode(nomorHandphone);
    }

    private void kirimVerifikasiKode(String nomorHandphone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(nomorHandphone)                 // Nomor Handphone Pengguna Untuk Verifikasi
                        .setTimeout(60L, TimeUnit.SECONDS)      // Batas Waktu Memasukkan Kode
                        .setActivity(this)                              // Verifikasi Di Activity Ini
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifikasiId = s;
            Toast.makeText(getApplicationContext(), "Kode Telah Dikirim!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String kode = phoneAuthCredential.getSmsCode();
            if (kode != null) {
                pvDariUser.setText(kode);
                verifikasiKode(kode);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
        }
    };

    private void verifikasiKode(String kode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifikasiId, kode);
        signInwithCredential(credential);
    }

    private void signInwithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    buatAkunPengguna();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(getApplicationContext(), "Kode Verifikasi Salah!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void buatAkunPengguna() {
//        Simpan Data Dari Register Activity Kedalam Database
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("catUsers");
        RegisterHelper tambahPenggunaBaru = new RegisterHelper(namaPengguna, surel, kataSandi, nomorHandphone);
        reference.child(namaPengguna).setValue(tambahPenggunaBaru);
    }

    public void callVerifikasi(View view) {
        String kode = pvDariUser.getText().toString().trim();
        if (kode.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Masukkan Kode Verifikasi!", Toast.LENGTH_SHORT).show();
        } else if (kode.length() < 6) {
            Toast.makeText(getApplicationContext(), "Harap Memasukkan 6 Digit Kode!", Toast.LENGTH_SHORT).show();
        } else {
            pvDariUser.setText(kode);
            verifikasiKode(kode);
            Intent intent = new Intent(getApplicationContext(), Login.class);

            Pair[] pairs = new Pair[3];
            pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
            pairs[1] = new Pair<View, String>(findViewById(R.id.textNama), "catbot_text_nama");
            pairs[2] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_deskripsi");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Verification.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}