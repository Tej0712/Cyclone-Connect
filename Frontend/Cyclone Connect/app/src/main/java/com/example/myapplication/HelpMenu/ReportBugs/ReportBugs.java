package com.example.myapplication.HelpMenu.ReportBugs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportBugs extends AppCompatActivity {

    private EditText subjectEditText, descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportbugs);

        subjectEditText = findViewById(R.id.subjectEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        Button submitBugButton = findViewById(R.id.submitBugButton);
        Button liveChatButton = findViewById(R.id.LiveChat);

        submitBugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBugReport();
            }
        });

        liveChatButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, report_live_chat.class);
            startActivity(intent);
        });
    }

    private void sendBugReport() {
        String subject = subjectEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        // Retrieve the user ID from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Long userId = preferences.getLong("userId", -1); // Use a default value if not found

        try {
            JSONObject reportData = new JSONObject()
                    .put("subject", subject)
                    .put("description", description)
                    .put("userId", userId); // Include the user ID in the report data

            String backendreportUrl = "http://coms-309-046.class.las.iastate.edu:8080/reports";
            VolleySingleton.getInstance(this).sendPostRequest(backendreportUrl, reportData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(ReportBugs.this, "Report submitted!", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                Log.e("ReportBugs", "Error submitting report. Status Code: " + statusCode);
                            } else {
                                Log.e("ReportBugs", "Error submitting report. No network response.");
                            }
                            Toast.makeText(ReportBugs.this, "Error submitting report", Toast.LENGTH_SHORT).show();
                        }

                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}