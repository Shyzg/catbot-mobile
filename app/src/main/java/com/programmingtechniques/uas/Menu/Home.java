package com.programmingtechniques.uas.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.programmingtechniques.uas.R;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Hooks
        recyclerView = findViewById(R.id.recyclerCatbot);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Munculin Data Dari Firebase Ke RecyclerView
        FirebaseRecyclerOptions<HomeHelper> options =
                new FirebaseRecyclerOptions.Builder<HomeHelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("catCommands"), HomeHelper.class)
                        .build();
        homeAdapter = new HomeAdapter(options);
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        homeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        homeAdapter.startListening();
    }

    public void callProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), Profile.class);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(findViewById(R.id.imageHero), "catbot_image_hero");
        pairs[1] = new Pair<View, String>(findViewById(R.id.textDeskripsi), "catbot_text_nama");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}