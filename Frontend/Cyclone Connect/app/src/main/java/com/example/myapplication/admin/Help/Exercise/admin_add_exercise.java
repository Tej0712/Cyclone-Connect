package com.example.myapplication.admin.Help.Exercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_add_exercise extends AppCompatActivity {

    private EditText exerciseName, exerciseDuration, exerciseCalories, exerciseImageUrl;
    private Button btnAddExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_exercise);

        exerciseName = findViewById(R.id.exerciseName);
        exerciseDuration = findViewById(R.id.exerciseDuration);
        exerciseCalories = findViewById(R.id.exerciseCalories);
        exerciseImageUrl = findViewById(R.id.exerciseImageUrl);
        btnAddExercise = findViewById(R.id.btnAddExercise);

        btnAddExercise.setOnClickListener(v -> {
            try {
                submitNewExercise();
            } catch (JSONException e) {
                Toast.makeText(this, "Error creating JSON object.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitNewExercise() throws JSONException {
        String name = exerciseName.getText().toString();
        String duration = exerciseDuration.getText().toString();
        String calories = exerciseCalories.getText().toString();
        String imageUrl = exerciseImageUrl.getText().toString();

        if (calories.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Calories and Image URL cannot be empty.", Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exerciseName", name);
        jsonObject.put("time", Integer.parseInt(duration));
        jsonObject.put("caloriesBurned", Integer.parseInt(calories));
        jsonObject.put("imageUrl", imageUrl);

        String url = "http://coms-309-046.class.las.iastate.edu:8080/exercises";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    Toast.makeText(admin_add_exercise.this, "Exercise added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(admin_add_exercise.this, "Error adding exercise: " + error.toString(), Toast.LENGTH_LONG).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
