package com.appkida.vehservicing.screens.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.adapter.ServiceCenterAdapter;
import com.appkida.vehservicing.adapter.SliderAdapter;
import com.appkida.vehservicing.model.ServicingCenter;
import com.appkida.vehservicing.model.SliderData;
import com.appkida.vehservicing.screens.admin.fragments.BlankFragment;
import com.appkida.vehservicing.screens.admin.fragments.EnrollFragment;
import com.appkida.vehservicing.screens.user.fragments.ServiceHistoryFragment;
import com.appkida.vehservicing.screens.user.fragments.UserHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shuhart.stepview.StepView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(UserHomeFragment.newInstance("", ""));
    }
        public void openFragment (Fragment fragment){
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
                            case R.id.home:
                                openFragment(UserHomeFragment.newInstance("", ""));
                                return true;
                            case R.id.history:
                                openFragment(ServiceHistoryFragment.newInstance("", ""));
                                return true;
                            case R.id.profile:
                                openFragment(BlankFragment.newInstance("", ""));
                                return true;
                        }
                        return false;
                    }
                };
    }