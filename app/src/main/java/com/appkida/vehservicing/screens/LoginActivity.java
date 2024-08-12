package com.appkida.vehservicing.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appkida.vehservicing.MainActivity;
import com.appkida.vehservicing.R;
import com.appkida.vehservicing.model.User;
import com.appkida.vehservicing.screens.admin.AdminEnrollmentActivity;
import com.appkida.vehservicing.screens.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.mailid);
        pass = findViewById(R.id.logpassword);

        TextView signupagain = findViewById(R.id.signupagain);
        signupagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button login = findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

            }
        });
    }

    public void loginUser() {
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "required all fields", Toast.LENGTH_SHORT).show();
            // Show an error message or toast indicating that all fields must be filled
            return;
        }
        if(email.equals("admin@gmail.com")){
            // Redirect to the main activity or perform other necessary actions
            Intent intent = new Intent(LoginActivity.this, AdminEnrollmentActivity.class);
            startActivity(intent);
            finish();
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("login response", "success");
                            // Login successful, you can redirect to the main activity or perform other actions
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                            checkUserType(email);

                        } else {
                            // If login fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("login response", "failed");

                        }
                    }
                });

    }

    private void checkUserType(String email) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("signup");

        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User data found in the database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            String userid=user.getUseruId();
                            saveEmailToSharedPreferences(email,userid);

                            if (!user.isCustomer()) {
                                // User is an admin, redirect to admin activity
                                Intent intent = new Intent(LoginActivity.this, AdminEnrollmentActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // User is a customer, redirect to user activity
                                Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                } else {
                    // User data not found in the database
                    Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveEmailToSharedPreferences(String email,String userid) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail", email);
        editor.putString("userId", userid);
        editor.apply();
    }
}