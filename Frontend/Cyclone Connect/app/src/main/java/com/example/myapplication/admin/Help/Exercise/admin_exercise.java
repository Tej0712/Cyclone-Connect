package com.example.myapplication.admin.Help.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.HelpMenu.HealthAndWellness.ExerciseActivity;
import com.example.myapplication.R;

public class admin_exercise extends AppCompatActivity {

    Button btnViewAll, btnAdd, btnUpdate, btnDelete, btnex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_exercise);

        btnViewAll = findViewById(R.id.GET);
        btnAdd = findViewById(R.id.Add);
        btnUpdate = findViewById(R.id.PUT);  // Changed from Update to PUT to match XML ID
        btnDelete = findViewById(R.id.Delete);
        btnex = findViewById(R.id.exbenifits);

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_exercise.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_exercise.this, admin_exercise_benifits.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_exercise.this, admin_add_exercise.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_exercise.this, admin_update_exercise.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_exercise.this, admin_delete_exercise.class);
                startActivity(intent);
            }
        });
    }
}
