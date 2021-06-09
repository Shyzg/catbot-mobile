package com.programmingtechniques.uas;

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

import com.programmingtechniques.uas.Users.Login;
import com.programmingtechniques.uas.Users.Register;

public class Welcome extends AppCompatActivity {
    ImageView ivHero;
    TextView tvNama, tvDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Hooks
        ivHero = findViewById(R.id.imageHero);
        tvNama = findViewById(R.id.textNama);
        tvDeskripsi = findViewById(R.id.textDeskripsi);
    }

    public void callLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_nama");
        pairs[2] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_deskripsi");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Welcome.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void callRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), Register.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_nama");
        pairs[2] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_deskripsi");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Welcome.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}