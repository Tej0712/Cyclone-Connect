package com.example.myapplication.admin.HeadStart.Major;

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
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

public class admin_headstart_delete_major extends AppCompatActivity {

    private EditText etMajorIDToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_major);

        etMajorIDToDelete = findViewById(R.id.etMajorIDToDelete);
        Button btnDeleteMajor = findViewById(R.id.btnDeleteMajor);

        btnDeleteMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMajor();
            }
        });
    }

    private void deleteMajor() {
        String majorID = etMajorIDToDelete.getText().toString().trim();
        if (!majorID.isEmpty()) {
            String url = "http://coms-309-046.class.las.iastate.edu:8080/majors/" + majorID;

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(admin_headstart_delete_major.this, "Major Deleted Successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(admin_headstart_delete_major.this, "Failed to Delete Major", Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(this).add(stringRequest);
        } else {
            Toast.makeText(this, "Major ID cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
