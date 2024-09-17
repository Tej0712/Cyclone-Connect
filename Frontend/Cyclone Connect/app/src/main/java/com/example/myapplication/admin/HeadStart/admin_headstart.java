package com.example.myapplication.admin.HeadStart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HeadStart.Headstart;
import com.example.myapplication.R;
import com.example.myapplication.admin.HeadStart.Course.admin_headstart_course_updates;
import com.example.myapplication.admin.HeadStart.Major.admin_headstart_major_updates;
import com.example.myapplication.admin.HeadStart.SpecificCourse.admin_headstart_specific_updates;

public class admin_headstart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_headstart);

        Button btnViewHeadStart = findViewById(R.id.GET);
        Button btnMajors = findViewById(R.id.Major);
        Button btnCourses = findViewById(R.id.Courses);
        Button btnSpecificCourse = findViewById(R.id.SpecificCourse);

        btnViewHeadStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_headstart.this, Headstart.class);
                startActivity(intent);
            }
        });

        btnMajors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_headstart.this, admin_headstart_major_updates.class);
                startActivity(intent);
            }
        });

        btnCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_headstart.this, admin_headstart_course_updates.class);
                startActivity(intent);
            }
        });

        btnSpecificCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_headstart.this, admin_headstart_specific_updates.class);
                startActivity(intent);
            }
        });
    }
}
