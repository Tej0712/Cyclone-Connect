package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;
    private Button changeTextButton;
    private Button openNewActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link to main activity layout

        // Initialize UI elements
        messageText = findViewById(R.id.main_msg_txt);
        messageText.setText("Hello World");

        // Button to change text
        changeTextButton = findViewById(R.id.change_text_button);
        changeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText.setText("Text Changed!");
            }
        });

        // Button to open new activity
        openNewActivityButton = findViewById(R.id.open_new_activity_button);
        openNewActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
