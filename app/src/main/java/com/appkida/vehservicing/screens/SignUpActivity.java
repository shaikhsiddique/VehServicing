package com.appkida.vehservicing.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText usernames,emails,passwords;
    private FirebaseAuth firebaseAuth;

    CheckBox isCustomers;
    // SharedPreferences for storing user's email
    private SharedPreferences sharedPreferences;
    private static final String PREF_EMAIL = "user_name";

    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        frameLayout=findViewById(R.id.framelayout);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        usernames=findViewById(R.id.username);
        emails=findViewById(R.id.email);
        passwords=findViewById(R.id.password);
        isCustomers=findViewById(R.id.areYouCustomer);
        TextView loginagain=findViewById(R.id.loginagain);
        loginagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        Button login=findViewById(R.id.signupbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
                addSignupData();
//                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    public void addSignupData() {
        String username = usernames.getText().toString().trim();
        String email = emails.getText().toString().trim();
        String password = passwords.getText().toString().trim();
        boolean isCustomer = isCustomers.isChecked();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            frameLayout.setVisibility(View.GONE);

            // Show an error message or toast indicating that all fields must be filled
            return;
        }
        if(email.equals("admin@gmail.com")){
            // Redirect to the main activity or perform other necessary actions
            Intent intent = new Intent(SignUpActivity.this, AdminEnrollmentActivity.class);
            startActivity(intent);
            finish();
        }
        // Use Firebase Authentication for secure password handling
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (firebaseUser != null) {
                                // Successfully created a user in Firebase Authentication
                                saveUserToDatabase(firebaseUser.getUid(), username,password, email, isCustomer);
                                frameLayout.setVisibility(View.GONE);
                                saveUserEmail(username);
                                Toast.makeText(SignUpActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                checkUserLogin();
                            }
                        } else {
                            frameLayout.setVisibility(View.GONE);
                            // Handle the task exception, e.g., show a toast with the error message
                            Toast.makeText(SignUpActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkUserLogin() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            checkUserType(currentUser.getEmail());
        } else {
            // User is an admin, redirect to admin activity
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
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
                            if (!user.isCustomer()) {
                                // User is an admin, redirect to admin activity
                                Intent intent = new Intent(SignUpActivity.this, AdminEnrollmentActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // User is a customer, redirect to user activity
                                Intent intent = new Intent(SignUpActivity.this, UserMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                } else {
                    // User data not found in the database
                    Toast.makeText(SignUpActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(SignUpActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToDatabase(String userId, String username,String password, String email, boolean isCustomer) {
        saveEmailToSharedPreferences(email);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("signup");
        String useruId = UUID.randomUUID().toString();

        User user = new User(useruId,username, email, password, isCustomer);
        databaseReference.child(userId).setValue(user);
    }

    private void saveUserEmail(String email) {
        // Save the user's email in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    private void saveEmailToSharedPreferences(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail", email);
        editor.apply();
    }

}