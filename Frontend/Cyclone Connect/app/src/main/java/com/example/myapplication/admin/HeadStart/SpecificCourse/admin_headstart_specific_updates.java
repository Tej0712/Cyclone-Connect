package com.example.myapplication.admin.HeadStart.SpecificCourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HeadStart.Headstart;
import com.example.myapplication.R;

public class admin_headstart_specific_updates extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_headstart_specific_updates); // Make sure this matches the XML file name

        // Initialize buttons
        Button viewMajorsButton = findViewById(R.id.GET);
        Button addButton = findViewById(R.id.Add);
        Button updateButton = findViewById(R.id.PUT);
        Button deleteButton = findViewById(R.id.Delete);

        // Set up button click listeners
        viewMajorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to View Majors Activity
                Intent intent = new Intent(admin_headstart_specific_updates.this, Headstart.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Add Major Activity
                Intent intent = new Intent(admin_headstart_specific_updates.this, admin_headstart_add_specificcourse.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Update Major Activity
                Intent intent = new Intent(admin_headstart_specific_updates.this, admin_headstart_update_specificcourse.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Delete Major Activity
                Intent intent = new Intent(admin_headstart_specific_updates.this, admin_headstart_delete_specificcourse.class);
                startActivity(intent);
            }
        });
    }
}
