package com.example.myapplication.HeadStart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Headstart_courses extends AppCompatActivity {
    private CoursesAdapter coursesAdapter;
    private final List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headstart_course);

        long majorID = getIntent().getLongExtra("majorID", -1);
        RecyclerView coursesRecyclerView = findViewById(R.id.coursesRecyclerView);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coursesAdapter = new CoursesAdapter(courseList);
        coursesRecyclerView.setAdapter(coursesAdapter);

        if (majorID != -1) {
            fetchCourses(majorID);
        } else {
            Toast.makeText(this, "Error retrieving major ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCourses(long majorID) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/majors/" + majorID;

        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray coursesArray = response.getJSONArray("courses");
                        for (int i = 0; i < coursesArray.length(); i++) {
                            JSONObject object = coursesArray.getJSONObject(i);
                            Course course = new Course(
                                    object.getLong("id"),
                                    object.getString("courseName"),
                                    object.getString("courseNumber"),
                                    object.getString("description"));
                            courseList.add(course);
                        }
                        coursesAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(Headstart_courses.this, "Failed to fetch courses", Toast.LENGTH_SHORT).show());

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public static class Course {
        private final Long courseId;
        private final String courseName;
        private final String courseNumber;
        private final String description;

        public Course(Long courseId, String courseName, String courseNumber, String description) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseNumber = courseNumber;
            this.description = description;
        }

        public Long getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getCourseNumber() {
            return courseNumber;
        }

        public String getDescription() {
            return description;
        }
    }

    private static class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {
        private final List<Course> courses;

        public CoursesAdapter(List<Course> courses) {
            this.courses = courses;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Course course = courses.get(position);
            holder.courseIdNumber.setText("ID:" + course.getCourseId() + " -  " + course.getCourseNumber());
            holder.courseName.setText(course.getCourseName());
            holder.description.setText(course.getDescription());
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView courseIdNumber;
            TextView courseName;
            TextView description;
            LinearLayout detailsLayout;

            public ViewHolder(View view) {
                super(view);
                courseIdNumber = view.findViewById(R.id.courseIdNumberTextView);
                courseName = view.findViewById(R.id.courseNameTextView);
                description = view.findViewById(R.id.descriptionTextView);
                detailsLayout = view.findViewById(R.id.detailsLayout);

                courseIdNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (detailsLayout.getVisibility() == View.GONE) {
                            detailsLayout.setVisibility(View.VISIBLE);
                        } else {
                            detailsLayout.setVisibility(View.GONE);
                        }
                    }
                });

                View.OnClickListener navigateToSpecificCourse = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Course course = courses.get(position);
                            Intent intent = new Intent(view.getContext(), Headstart_specific_course.class);
                            intent.putExtra("courseID", course.getCourseId());
                            intent.putExtra("courseNumber", course.getCourseNumber());
                            view.getContext().startActivity(intent);
                        }
                    }
                };

                courseName.setOnClickListener(navigateToSpecificCourse);
                description.setOnClickListener(navigateToSpecificCourse);
            }
        }
    }
}