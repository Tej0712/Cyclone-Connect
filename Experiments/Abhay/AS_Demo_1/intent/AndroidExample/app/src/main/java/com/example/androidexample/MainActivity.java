package com.example.androidexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;
    private Button counterButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageText = findViewById(R.id.main_msg_txt);
        counterButton = findViewById(R.id.main_counter_btn);
        resetButton = findViewById(R.id.main_reset_btn);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int lastCounter = sharedPref.getInt(getString(R.string.saved_counter_key), 0);
        messageText.setText("Last counter was " + lastCounter);

        counterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CounterActivity.class);
            startActivity(intent);
        });

        resetButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.saved_counter_key), 0);
            editor.apply();
            messageText.setText("Counter reset to 0");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int lastCounter = sharedPref.getInt(getString(R.string.saved_counter_key), 0);
        messageText.setText("Last counter was " + lastCounter);
    }
}
