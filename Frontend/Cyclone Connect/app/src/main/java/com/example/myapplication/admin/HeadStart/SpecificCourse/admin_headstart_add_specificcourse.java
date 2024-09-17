package com.example.myapplication.admin.HeadStart.SpecificCourse;

import android.os.Bundle;
import android.view.View;
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

public class admin_headstart_add_specificcourse extends AppCompatActivity {

    private EditText courseIdEditText;
    private EditText courseNameEditText;
    private EditText courseDescriptionEditText;
    private EditText prerequisitesEditText;
    private EditText totalCreditsEditText;
    private EditText programmingLanguagesEditText;
    private EditText frameworkEditText;
    private EditText difficultyLevelEditText;
    private EditText timeComplexityEditText;
    private EditText resourcesEditText;
    private EditText embeddedLinksEditText;
    private EditText roadMapEditText;
    private EditText reviewsEditText;
    private EditText ratingEditText;
    private EditText editedByEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_headstart_add_specificcourse);

        courseIdEditText = findViewById(R.id.courseIdEditText);
        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        prerequisitesEditText = findViewById(R.id.prerequisitesEditText);
        totalCreditsEditText = findViewById(R.id.totalCreditsEditText);
        programmingLanguagesEditText = findViewById(R.id.programmingLanguagesEditText);
        frameworkEditText = findViewById(R.id.frameworkEditText);
        difficultyLevelEditText = findViewById(R.id.difficultyLevelEditText);
        timeComplexityEditText = findViewById(R.id.timeComplexityEditText);
        resourcesEditText = findViewById(R.id.resourcesEditText);
        embeddedLinksEditText = findViewById(R.id.embeddedLinksEditText);
        roadMapEditText = findViewById(R.id.roadMapEditText);
        reviewsEditText = findViewById(R.id.reviewsEditText);
        ratingEditText = findViewById(R.id.ratingEditText);
        editedByEditText = findViewById(R.id.editedByEditText);

        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpecificCourse();
            }
        });
    }

    private void addSpecificCourse() {
        String courseIdString = courseIdEditText.getText().toString().trim();
        String courseName = courseNameEditText.getText().toString().trim();
        String courseDescription = courseDescriptionEditText.getText().toString().trim();
        String prerequisites = prerequisitesEditText.getText().toString().trim();
        String totalCredits = totalCreditsEditText.getText().toString().trim();
        String programmingLanguages = programmingLanguagesEditText.getText().toString().trim();
        String framework = frameworkEditText.getText().toString().trim();
        String difficultyLevel = difficultyLevelEditText.getText().toString().trim();
        String timeComplexity = timeComplexityEditText.getText().toString().trim();
        String resources = resourcesEditText.getText().toString().trim();
        String embeddedLinks = embeddedLinksEditText.getText().toString().trim();
        String roadMap = roadMapEditText.getText().toString().trim();
        String reviews = reviewsEditText.getText().toString().trim();
        String rating = ratingEditText.getText().toString().trim();
        String editedBy = editedByEditText.getText().toString().trim();

        if (courseIdString.isEmpty() || courseName.isEmpty() || courseDescription.isEmpty() ||
                prerequisites.isEmpty() || totalCredits.isEmpty() || programmingLanguages.isEmpty() ||
                framework.isEmpty() || difficultyLevel.isEmpty() || timeComplexity.isEmpty() ||
                resources.isEmpty() || embeddedLinks.isEmpty() || roadMap.isEmpty() ||
                reviews.isEmpty() || rating.isEmpty() || editedBy.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Long courseId;
        try {
            courseId = Long.parseLong(courseIdString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("courseId", courseId);
            requestBody.put("courseName", courseName);
            requestBody.put("courseDescription", courseDescription);
            requestBody.put("prerequisites", prerequisites);
            requestBody.put("totalCredits", totalCredits);
            requestBody.put("programmingLanguages", programmingLanguages);
            requestBody.put("framework", framework);
            requestBody.put("difficultyLevel", difficultyLevel);
            requestBody.put("timeComplexity", timeComplexity);
            requestBody.put("resources", resources);
            requestBody.put("embeddedLinks", embeddedLinks);
            requestBody.put("roadMap", roadMap);
            requestBody.put("reviews", reviews);
            requestBody.put("rating", rating);
            requestBody.put("editedBy", editedBy);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        String url = "http://coms-309-046.class.las.iastate.edu:8080/courseSpecifications";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    Toast.makeText(admin_headstart_add_specificcourse.this, "Specific course added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(admin_headstart_add_specificcourse.this, "Failed to add specific course", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}