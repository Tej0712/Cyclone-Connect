package com.example.myapplication.GPACalc;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateCourseActivity extends AppCompatActivity {

    private EditText editTextCardId;
    private EditText editTextCourseName;
    private EditText editTextCredits;
    private Spinner spinnerGrade;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://coms-309-046.class.las.iastate.edu:8080/api/gpa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        editTextCardId = findViewById(R.id.editTextCardId);
        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCredits = findViewById(R.id.editTextCredits);
        spinnerGrade = findViewById(R.id.spinnerGrade);
        Button buttonUpdateCourse = findViewById(R.id.buttonUpdateCourse);

        String[] grades = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, grades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        buttonUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourse();
            }
        });
    }

    private void updateCourse() {
        long cardId = Long.parseLong(editTextCardId.getText().toString());
        String courseName = editTextCourseName.getText().toString();
        int credits = Integer.parseInt(editTextCredits.getText().toString());
        String grade = spinnerGrade.getSelectedItem().toString();

        String url = BASE_URL + "/update/" + cardId;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("subjectName", courseName);
            requestBody.put("credit", credits);
            requestBody.put("grade", grade);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UpdateCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCourseActivity.this, "Error updating course", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}