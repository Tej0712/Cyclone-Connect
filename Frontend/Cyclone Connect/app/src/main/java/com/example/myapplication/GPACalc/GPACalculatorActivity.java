package com.example.myapplication.GPACalc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class GPACalculatorActivity extends AppCompatActivity {

    private TextView textViewGPA;
    private TextView textViewGrade;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://coms-309-046.class.las.iastate.edu:8080/api/gpa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpa_calc);

        textViewGPA = findViewById(R.id.textViewGPA);
        textViewGrade = findViewById(R.id.textViewGrade);
        RecyclerView recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(courseList, this);
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCourses.setAdapter(courseAdapter);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);


        fetchGPA(userId);
        fetchCourseGrades(userId);

        Button buttonAddCourse = findViewById(R.id.buttonAddCourse);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GPACalculatorActivity.this, AddCourseActivity.class));
            }
        });

        Button buttonUpdateCourse = findViewById(R.id.buttonUpdateCourse);
        buttonUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourses();
            }

        });

        Button buttonDeleteCourse = findViewById(R.id.buttonDeleteCourse);
        buttonDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GPACalculatorActivity.this, DeleteCourseActivity.class));
            }
        });
        // Find the back button
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Finish the activity, return to the previous screen
            }
        });

    }

    private void fetchGPA(long userId) {
        String url = BASE_URL + "/calculate/" + userId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double gpa = response.getDouble("gpa");
                            String gradeLetter = response.getString("gradeLetter");
                            textViewGPA.setText(String.format("Your GPA: %.2f", gpa));
                            textViewGrade.setText(String.format("Your Grade: %s", gradeLetter));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchCourseGrades(long userId) {
        String url = BASE_URL + "/grades/" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        courseList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                long id = jsonObject.getLong("id");
                                String subjectName = jsonObject.getString("subjectName");
                                int credit = jsonObject.getInt("credit");
                                String grade = jsonObject.getString("grade");
                                Course course = new Course(id, subjectName, credit, grade);
                                courseList.add(course);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        courseAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void updateCourses() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);

        final int[] updatedCount = {0};
        int totalCount = courseList.size();

        for (Course course : courseList) {
            updateCourse(userId, course, new VolleyCallback() {
                @Override
                public void onSuccess() {
                    updatedCount[0]++;
                    if (updatedCount[0] == totalCount) {
                        fetchGPA(userId);
                        fetchCourseGrades(userId);
                    }
                }

                @Override
                public void onError() {
                    updatedCount[0]++;
                    if (updatedCount[0] == totalCount) {
                        fetchGPA(userId);
                        fetchCourseGrades(userId);
                    }
                }
            });
        }
    }

    private void updateCourse(long userId, Course course, final VolleyCallback callback) {
        String url = BASE_URL + "/update/" + course.getId();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("subjectName", course.getSubjectName());
            requestBody.put("credit", course.getCredit());
            requestBody.put("grade", course.getGrade());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private interface VolleyCallback {
        void onSuccess();
        void onError();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);
        fetchGPA(userId);
        fetchCourseGrades(userId);
    }
}