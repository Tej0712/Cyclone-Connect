package com.example.myapplication.admin.HeadStart.Course;

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

public class admin_headstart_add_courses extends AppCompatActivity {

    private EditText courseNameEditText;
    private EditText courseNumberEditText;
    private EditText courseDescriptionEditText;
    private EditText majorIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_headstart_add_course);

        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseNumberEditText = findViewById(R.id.courseNumberEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        majorIdEditText = findViewById(R.id.majorIdEditText);
        Button addCourseButton = findViewById(R.id.addCourseButton);

        addCourseButton.setOnClickListener(v -> addCourse());
    }

    private void addCourse() {
        String courseName = courseNameEditText.getText().toString().trim();
        String courseNumber = courseNumberEditText.getText().toString().trim();
        String courseDescription = courseDescriptionEditText.getText().toString().trim();
        String majorIdString = majorIdEditText.getText().toString().trim();

        if (courseName.isEmpty() || courseNumber.isEmpty() || courseDescription.isEmpty() || majorIdString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Long majorId;
        try {
            majorId = Long.parseLong(majorIdString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid major ID", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("courseName", courseName);
            requestBody.put("courseNumber", courseNumber);
            requestBody.put("description", courseDescription);
            requestBody.put("majorId", majorId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        String url = "http://coms-309-046.class.las.iastate.edu:8080/courses";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    Toast.makeText(admin_headstart_add_courses.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(admin_headstart_add_courses.this, "Failed to add course", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}

