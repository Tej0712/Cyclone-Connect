package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Login.UserLoginActivity;
import com.example.myapplication.SignUp.WhoActivity;

/**
 * MainActivity is the entry point of the application, presenting users with the option to either log in or sign up.
 * This class extends AppCompatActivity, providing compatibility support for different versions of Android.
 * It initializes the application's UI and sets up event listeners for the login and signup buttons.
 * <p>
 * Author: Abhay Prasanna Rao
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, registering click listeners, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button);

        Button signupButton = findViewById(R.id.signup_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WhoActivity.class);
                startActivity(intent);
            }
        });
    }
}
