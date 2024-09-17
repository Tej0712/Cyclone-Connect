package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Create_Faculty_Account extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_faculty_account);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createAccountButton = findViewById(R.id.create_account_button);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Create_Faculty_Account.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Here you would add your logic to create the account
                // This example simply shows a toast message
                Toast.makeText(Create_Faculty_Account.this, "Account created for: " + email, Toast.LENGTH_SHORT).show();

                // Redirect to the login page or main activity after account creation
                Intent intent = new Intent(Create_Faculty_Account.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}