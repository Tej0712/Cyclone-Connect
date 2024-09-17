package com.example.myapplication.admin.Help.Exercise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HelpMenu.HealthAndWellness.ExerciseActivity;
import com.example.myapplication.R;

public class admin_exercise_benifits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_exercise_benifits);

        // Initialize buttons
        Button btnViewBenefits = findViewById(R.id.GET);
        Button btnAddBenefit = findViewById(R.id.Add);

        // Setup click listeners
        btnViewBenefits.setOnClickListener(v -> {
            startActivity(new Intent(admin_exercise_benifits.this, ExerciseActivity.class));
        });

        btnAddBenefit.setOnClickListener(v -> {
            startActivity(new Intent(admin_exercise_benifits.this, AddExerciseBenefitActivity.class));
        });

    }
}
