package com.example.myapplication.admin.Help.Exercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;
import org.json.JSONObject;

public class AddExerciseBenefitActivity extends AppCompatActivity {

    private EditText exerciseIdEditText, benefitEditText;
    private Button submitBenefitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_benefit);

        exerciseIdEditText = findViewById(R.id.exerciseIdEditText);
        benefitEditText = findViewById(R.id.benefitEditText);
        submitBenefitButton = findViewById(R.id.submitBenefitButton);

        submitBenefitButton.setOnClickListener(v -> {
            String exerciseId = exerciseIdEditText.getText().toString();
            String benefit = benefitEditText.getText().toString();
            if (exerciseId.isEmpty() || benefit.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("exerciseId", Integer.parseInt(exerciseId));
                requestBody.put("benefits", benefit);

                VolleySingleton.getInstance(this).sendPostRequest(
                        "http://coms-309-046.class.las.iastate.edu:8080/exerciseBenefits",
                        requestBody,
                        response -> {
                            Toast.makeText(AddExerciseBenefitActivity.this, "Benefit added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        },
                        error -> Toast.makeText(AddExerciseBenefitActivity.this, "Failed to add benefit", Toast.LENGTH_SHORT).show()
                );
            } catch (Exception e) {
                Toast.makeText(this, "Error in request creation", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
