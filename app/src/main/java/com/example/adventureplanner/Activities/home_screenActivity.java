package com.example.adventureplanner.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.adventureplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home_screenActivity extends AppCompatActivity {
    private BottomNavigationView bottomnavigationview;
    private FrameLayout framelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomnavigationview = findViewById(R.id.bottomnavigationview);
        framelayout = findViewById(R.id.framelayout);
        bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();

                if (itemid == R.id.navhome) {
                    loadFragment(new HomeFragment(), false);
                }  else if (itemid == R.id.navnotification) {
                    loadFragment(new NotificationFragment(), false);
                } else if (itemid == R.id.navmapview) {
                    loadFragment(new MapViewFragment(), false);
                } else {
                    loadFragment(new SettingFragment(), false);
                }
                return true;
            }
        });
        loadFragment(new HomeFragment(), true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmenttransation = fragmentmanager.beginTransaction();
        if (isAppInitialized) {
            fragmenttransation.add(R.id.framelayout, fragment);
        } else {
            fragmenttransation.replace(R.id.framelayout, fragment);
        }
        fragmenttransation.commit();
    }
}