package com.example.myapplication.admin.Help.Exercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

public class admin_delete_exercise extends AppCompatActivity {

    private EditText exerciseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_exercise);

        exerciseId = findViewById(R.id.exerciseId);
        Button btnDeleteExercise = findViewById(R.id.btnDeleteExercise);

        btnDeleteExercise.setOnClickListener(v -> {
            String id = exerciseId.getText().toString();
            if (id.isEmpty()) {
                Toast.makeText(this, "Please enter an Exercise ID to delete", Toast.LENGTH_SHORT).show();
                return;  // Stop the function if no ID is entered
            }

            // Construct the URL for the DELETE request
            String url = "http://coms-309-046.class.las.iastate.edu:8080/exercises/" + id;

            VolleySingleton.getInstance(this).sendDeleteRequest(url,
                    response -> {
                        Toast.makeText(admin_delete_exercise.this, "Exercise deleted successfully!", Toast.LENGTH_SHORT).show();
                        finish();  // Close this activity and go back to the previous one in the stack
                    },
                    error -> Toast.makeText(admin_delete_exercise.this, "Error deleting exercise: " + error.toString(), Toast.LENGTH_LONG).show());
        });
    }
}
