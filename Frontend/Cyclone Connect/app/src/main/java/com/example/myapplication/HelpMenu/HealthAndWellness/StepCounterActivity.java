package com.example.myapplication.HelpMenu.HealthAndWellness;

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView stepsTV;
    private Button startButton;
    private Button stopButton;
    private boolean running = false;
    private int totalStepsSinceStart = 0;
    private int initialStepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Check if the step counter sensor is available
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) == null) {
            Toast.makeText(this, "Step counter sensor is not available on this device", Toast.LENGTH_LONG).show();
        }

        stepsTV = findViewById(R.id.tv_stepsTaken);
        startButton = findViewById(R.id.idButtonStart);
        stopButton = findViewById(R.id.idButtonStop);

        startButton.setOnClickListener(view -> startStepCounter());
        stopButton.setOnClickListener(view -> stopStepCounter());
        stopButton.setEnabled(false); // Initially disable the stop button
    }



    private void startStepCounter() {
        running = true;
        totalStepsSinceStart = 0;  // Reset step count each time we start
        stepsTV.setText(String.valueOf(totalStepsSinceStart));  // Display the reset step count
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_UI);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        initialStepCount = -1;  // Reset initial step count
    }

    private void stopStepCounter() {
        running = false;
        sensorManager.unregisterListener(this);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        new Handler().postDelayed(() -> {
            Toast.makeText(this, "Stay Healthy! Closing the step counter", Toast.LENGTH_LONG).show();
            finish();
        }, 3000);  // Display closing message and close the activity after 3 seconds
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            if (initialStepCount < 0) {
                initialStepCount = (int) event.values[0];  // Set the initial number of steps
            }
            totalStepsSinceStart = (int) event.values[0] - initialStepCount;
            stepsTV.setText(String.valueOf(totalStepsSinceStart));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (running) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (running) {
            sensorManager.unregisterListener(this);
        }
    }
}
