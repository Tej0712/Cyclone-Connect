package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private EditText emailEditText;
    private Button updateProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        usernameTextView = findViewById(R.id.profile_username_txt);
        emailEditText = findViewById(R.id.profile_email_edt);
        updateProfileButton = findViewById(R.id.profile_update_btn);

        String username = getIntent().getStringExtra("USERNAME");
        usernameTextView.setText(username);

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you would typically update the user profile information in your backend or local database
                Toast.makeText(UserProfileActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
