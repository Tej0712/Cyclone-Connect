package com.example.myapplication.admin.HeadStart.SpecificCourse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

public class admin_headstart_delete_specificcourse extends AppCompatActivity {

    private EditText courseIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_headstart_delete_specificcourse);

        courseIdEditText = findViewById(R.id.courseIdEditText);
        Button deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSpecificCourse();
            }
        });
    }

    private void deleteSpecificCourse() {
        String courseIdString = courseIdEditText.getText().toString().trim();

        if (courseIdString.isEmpty()) {
            Toast.makeText(this, "Please enter the course ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Long courseId;
        try {
            courseId = Long.parseLong(courseIdString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://coms-309-046.class.las.iastate.edu:8080/courseSpecifications/" + courseId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    Toast.makeText(admin_headstart_delete_specificcourse.this, "Specific course deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(admin_headstart_delete_specificcourse.this, "Failed to delete specific course", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}