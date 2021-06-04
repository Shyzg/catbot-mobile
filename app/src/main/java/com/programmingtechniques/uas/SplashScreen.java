package com.programmingtechniques.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmingtechniques.uas.Users.Login;
import com.programmingtechniques.uas.Users.Welcome;

public class SplashScreen extends AppCompatActivity {
    private static final int SS = 3000;

    Animation topAnim, bottomAnim;
    ImageView ivHero;
    TextView tvName, tvSlogan;
    Button btnMasuk, btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Hooks
        ivHero = findViewById(R.id.imageHero);
        tvName = findViewById(R.id.textName);

//        Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//        Set Animation
        ivHero.setAnimation(topAnim);
        tvName.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(ivHero, "catbot_hero");
                pairs[1] = new Pair<View, String>(tvName, "catbot_name");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        }, SS);
    }
}