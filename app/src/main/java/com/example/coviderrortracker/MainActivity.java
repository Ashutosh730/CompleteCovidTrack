package com.example.coviderrortracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation_bar);


        // default fragment
        loadFragment(new CasesFragment());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.bed:
                        fragment = new BedFragment();
                        break;

                    case R.id.cases:
                        fragment = new CasesFragment();
                        break;

                    case R.id.test:
                        fragment = new TestFragment();
                        break;

                    default:
                        fragment = new BedFragment();
                        break;
                }
                loadFragment(fragment);
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}