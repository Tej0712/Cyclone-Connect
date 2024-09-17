package com.example.myapplication.admin.HeadStart.Major;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_headstart_update_major extends AppCompatActivity {

    private EditText etMajorID, etNewMajorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_major); // Make sure this layout matches your file

        etMajorID = findViewById(R.id.etMajorID);
        etNewMajorName = findViewById(R.id.etNewMajorName);
        Button btnUpdateMajor = findViewById(R.id.btnUpdateMajor);

        btnUpdateMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitUpdateMajor();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(admin_headstart_update_major.this, "Error creating update JSON", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitUpdateMajor() throws JSONException {
        final String majorID = etMajorID.getText().toString();
        final String newMajorName = etNewMajorName.getText().toString();

        String url = "http://coms-309-046.class.las.iastate.edu:8080/majors/" + majorID; // Adjust if necessary

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("MajorID", majorID); // Adjust these keys based on what your backend expects
        jsonBody.put("majorName", newMajorName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                response -> {
                    try {
                        // Assuming the backend response has a key "message" indicating success
                        String message = response.getString("message");
                        Toast.makeText(admin_headstart_update_major.this, message, Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(admin_headstart_update_major.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(admin_headstart_update_major.this, "Failed to Update Major", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
