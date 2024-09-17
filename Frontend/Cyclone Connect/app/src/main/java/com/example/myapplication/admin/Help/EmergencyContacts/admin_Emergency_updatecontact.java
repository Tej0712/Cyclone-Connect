package com.example.myapplication.admin.Help.EmergencyContacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_Emergency_updatecontact extends AppCompatActivity {

    private EditText etEmergencyId, etContactName, etPhoneNumber, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_emergency_updatecontact);

        etEmergencyId = findViewById(R.id.etEmergencyId);
        etContactName = findViewById(R.id.etContactName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etDescription = findViewById(R.id.etDescription);
        Button btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmergencyContact();
            }
        });
    }

    private void updateEmergencyContact() {
        String emergencyId = etEmergencyId.getText().toString().trim();
        String contactName = etContactName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("contactName", contactName);
            requestBody.put("phoneNumber", phoneNumber);
            requestBody.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/emergencies/" + emergencyId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(admin_Emergency_updatecontact.this, "Emergency contact updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_Emergency_updatecontact.this, "Error updating emergency contact", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}