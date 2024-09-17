package com.example.myapplication.admin.HeadStart.Major;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONObject;

public class admin_headstart_add_major extends AppCompatActivity {

    private EditText etMajorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_major);

        etMajorName = findViewById(R.id.etMajorName);
        Button btnAddMajor = findViewById(R.id.btnAddMajor);

        btnAddMajor.setOnClickListener(view -> {
            String majorName = etMajorName.getText().toString().trim();
            if (!majorName.isEmpty()) {
                addMajor(majorName);
            } else {
                Toast.makeText(admin_headstart_add_major.this, "Major name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMajor(String majorName) {
        try {
            JSONObject majorInfo = new JSONObject();
            majorInfo.put("majorName", majorName);

            String url = "http://coms-309-046.class.las.iastate.edu:8080/majors";

            VolleySingleton.getInstance(this).sendPostRequest(url, majorInfo,
                    response -> {
                        // This block will be executed upon successful request completion
                        Toast.makeText(admin_headstart_add_major.this, "Major added successfully", Toast.LENGTH_LONG).show();
                        finish();
                        // Optionally, clear the EditText or navigate away from this activity
                    },
                    error -> {
                        // This block will be executed if there's an error with the request
                        Toast.makeText(admin_headstart_add_major.this, "Failed to add major", Toast.LENGTH_LONG).show();
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
        }
    }
}
