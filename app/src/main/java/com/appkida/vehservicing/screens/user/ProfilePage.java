package com.appkida.vehservicing.screens.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.screens.SplashScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilePage extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        bottomNavigation = findViewById(R.id.nav);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Button logoutButton = findViewById(R.id.logoutButton2);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePage.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout
                FirebaseAuth.getInstance().signOut();
                // Add any other necessary logout actions

                // Close the app or navigate to the login screen
                // For example, you can use finish() to close the current activity
                // Navigate from Fragment to Activity
                Intent intent = new Intent(ProfilePage.this, SplashScreen.class);
                startActivity(intent);

                // Finish the current activity or fragment (optional)
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.show();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Intent intent=new Intent(ProfilePage.this,UserMainActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.navigation_dashboard:
                            Intent intent2=new Intent(ProfilePage.this,ProfilePage.class);
                            startActivity(intent2);
                            return true;
                        case R.id.navigation_profile:
                            Intent intent3=new Intent(ProfilePage.this,ProfilePage.class);
                            startActivity(intent3);
                            return true;
                    }
                    return false;
                }
            };
}