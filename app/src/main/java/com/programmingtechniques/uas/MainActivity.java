package com.programmingtechniques.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.WindowManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chipNavigationBar = findViewById(R.id.bottom_navigation_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainActivityFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.navigation_home:
                        fragment = new Fragment();
                        break;
                    case R.id.navigation_cc:
                        fragment = new Fragment();
                        break;
                    case R.id.navigation_profile:
                        fragment = new Fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainActivityFragment()).commit();
            }
        });
    }
}