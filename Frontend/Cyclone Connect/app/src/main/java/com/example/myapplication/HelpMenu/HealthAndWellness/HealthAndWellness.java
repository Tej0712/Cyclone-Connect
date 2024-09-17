package com.example.myapplication.HelpMenu.HealthAndWellness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;

/**
 * The HealthAndWellness activity serves as a dedicated space within the application
 * for health and wellness resources and information. It provides users with access
 * to various tools, articles, emergency contacts, or guidance related to maintaining
 * and improving their health and overall well-being.
 *
 * This activity primarily functions as a content display page, where specific health
 * and wellness resources are presented to the user. The resources could range from
 * contact information for mental health support, exercise guides, nutritional advice,
 * to mindfulness and stress relief techniques.
 *
 * Upon creation, the activity sets its content view to a layout defined in
 * 'activity_health.xml', which is expected to contain the UI elements for displaying
 * the aforementioned resources.
 *
 * Author: Abhay Prasanna Rao
 */
public class HealthAndWellness extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        LinearLayout exerciseLL = findViewById(R.id.idLLExercise);
        LinearLayout stepCounterLL = findViewById(R.id.idLLStepCounter);
        LottieAnimationView exerciseLAV = findViewById(R.id.idLAVExercise);
        LottieAnimationView counterLAV = findViewById(R.id.idLAVStepCounter);

        exerciseLAV.setAnimationFromUrl("https://lottie.host/68a91d85-ea24-4c04-81dc-60e2146d922c/KrgpozIYW5.json");
        counterLAV.setAnimationFromUrl("https://lottie.host/827dc9c8-96f1-497a-9806-aadb6c475fb4/IgRjUR99ss.json");

        exerciseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HealthAndWellness.this, ExerciseActivity.class);
                startActivity(i);
            }
        });

        stepCounterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HealthAndWellness.this, StepCounterActivity.class);
                startActivity(i);
            }
        });

    }
}
