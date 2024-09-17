package com.example.androidexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CounterActivity extends AppCompatActivity {

    private TextView numberTxt;
    private Button increaseBtn;
    private Button decreaseBtn;
    private Button backBtn;
    private Button shareBtn;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        numberTxt = findViewById(R.id.number);
        increaseBtn = findViewById(R.id.counter_increase_btn);
        decreaseBtn = findViewById(R.id.counter_decrease_btn);
        backBtn = findViewById(R.id.counter_back_btn);
        shareBtn = findViewById(R.id.counter_share_btn);

        increaseBtn.setOnClickListener(v -> numberTxt.setText(String.valueOf(++counter)));
        decreaseBtn.setOnClickListener(v -> numberTxt.setText(String.valueOf(--counter)));

        backBtn.setOnClickListener(v -> {
            saveCounterValue();
            finish();
        });

        shareBtn.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Counter Value");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Current counter value: " + counter);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
    }

    private void saveCounterValue() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_counter_key), counter);
        editor.apply();
    }
}
