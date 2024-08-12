package com.appkida.vehservicing.screens.admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.screens.admin.fragments.BlankFragment;
import com.appkida.vehservicing.screens.admin.fragments.BookingsShow;
import com.appkida.vehservicing.screens.admin.fragments.EnrollFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminEnrollmentActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enrollment);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // Set the default fragment
        openFragment(EnrollFragment.newInstance("", ""));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(EnrollFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_dashboard:
                            openFragment(BookingsShow.newInstance("", ""));
                            return true;
                        case R.id.navigation_profile:
                            openFragment(BlankFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
}
