package com.example.myapplication.admin.Help.Exercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONObject;

public class admin_update_exercise extends AppCompatActivity {

    private EditText exerciseId, exerciseName, exerciseDuration, exerciseCalories, exerciseImageUrl;
    private Button btnUpdateExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_exercise);

        exerciseId = findViewById(R.id.exerciseId);
        exerciseName = findViewById(R.id.exerciseName);
        exerciseDuration = findViewById(R.id.exerciseDuration);
        exerciseCalories = findViewById(R.id.exerciseCalories);
        exerciseImageUrl = findViewById(R.id.exerciseImageUrl);
        btnUpdateExercise = findViewById(R.id.btnUpdateExercise);

        btnUpdateExercise.setOnClickListener(v -> {
            String id = exerciseId.getText().toString();
            if (id.isEmpty()) {
                Toast.makeText(this, "Exercise ID is required", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("exerciseName", exerciseName.getText().toString());
                requestBody.put("time", Integer.parseInt(exerciseDuration.getText().toString()));
                requestBody.put("caloriesBurned", Integer.parseInt(exerciseCalories.getText().toString()));
                requestBody.put("imageUrl", exerciseImageUrl.getText().toString());

                VolleySingleton.getInstance(this).sendPutRequest("http://coms-309-046.class.las.iastate.edu:8080/exercises/" + id, requestBody,
                        response -> {
                            Toast.makeText(admin_update_exercise.this, "Exercise updated successfully!", Toast.LENGTH_SHORT).show();
                            finish();  // Close this activity and return to the previous one in the stack.
                        },
                        error -> Toast.makeText(admin_update_exercise.this, "Error updating exercise: " + error.toString(), Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                Toast.makeText(this, "Error creating JSON object or parsing numbers.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
