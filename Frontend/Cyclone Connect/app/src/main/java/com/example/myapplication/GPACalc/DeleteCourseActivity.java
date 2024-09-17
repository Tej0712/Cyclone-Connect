package com.example.myapplication.GPACalc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

public class DeleteCourseActivity extends AppCompatActivity {

    private EditText editTextCardId;
    private Button buttonDeleteCourse;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://coms-309-046.class.las.iastate.edu:8080/api/gpa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        editTextCardId = findViewById(R.id.editTextCardId);
        buttonDeleteCourse = findViewById(R.id.buttonDeleteCourse);

        requestQueue = Volley.newRequestQueue(this);

        buttonDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();
            }
        });
    }

    private void deleteCourse() {
        long cardId = Long.parseLong(editTextCardId.getText().toString());

        String url = BASE_URL + "/delete/" + cardId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DeleteCourseActivity.this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DeleteCourseActivity.this, "Error deleting course", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }
}