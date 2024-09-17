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
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

public class admin_Emergency_deletecontact extends AppCompatActivity {

    private EditText etEmergencyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_emergency_deletecontact);

        etEmergencyId = findViewById(R.id.etEmergencyId);
        Button btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmergencyContact();
            }
        });
    }

    private void deleteEmergencyContact() {
        String emergencyId = etEmergencyId.getText().toString().trim();

        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/emergencies/" + emergencyId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(admin_Emergency_deletecontact.this, "Emergency contact deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_Emergency_deletecontact.this, "Error deleting emergency contact", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}