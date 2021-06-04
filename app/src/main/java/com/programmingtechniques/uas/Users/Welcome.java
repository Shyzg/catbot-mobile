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

public class Welcome extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    Button btnLogin, btnRegister;
    ImageView ivHero;
    TextView tvName, tvSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Hooks
        ivHero = findViewById(R.id.imageHero);
        tvName = findViewById(R.id.textName);
        tvSlogan = findViewById(R.id.textSlogan);
        btnLogin = findViewById(R.id.buttonGoLogin);
        btnRegister = findViewById(R.id.buttonGoRegister);

        animationLayout();
    }

    public void callLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.buttonGoLogin), "catbot_button_masuk");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Welcome.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void callRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterFirst.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.buttonGoRegister), "catbot_button_daftar");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Welcome.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void animationLayout() {
//        Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//        Set Animation
        ivHero.setAnimation(topAnim);
        tvName.setAnimation(topAnim);
        tvSlogan.setAnimation(topAnim);
        btnLogin.setAnimation(bottomAnim);
        btnRegister.setAnimation(bottomAnim);
    }
}