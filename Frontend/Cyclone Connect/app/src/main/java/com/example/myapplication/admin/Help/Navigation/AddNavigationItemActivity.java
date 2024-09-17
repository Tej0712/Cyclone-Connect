package com.example.myapplication.admin.Help.Navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNavigationItemActivity extends AppCompatActivity {

    private EditText etDestination, etDescription, etUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_navigation_item);

        etDestination = findViewById(R.id.etDestination);
        etDescription = findViewById(R.id.etDescription);
        Button btnSubmit = findViewById(R.id.btnSubmitNavigationItem);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitNavigationItem();
                } catch (JSONException e) {
                    Toast.makeText(AddNavigationItemActivity.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitNavigationItem() throws JSONException {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/navigations";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("destination", etDestination.getText().toString());
        jsonObject.put("description", etDescription.getText().toString());

        // Retrieve user ID from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1); // -1 is the default value if userId is not found

        // Check if userId is valid
        if(userId != -1) {
            jsonObject.put("userId", userId);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(AddNavigationItemActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close this activity
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddNavigationItemActivity.this, "Error adding item", Toast.LENGTH_SHORT).show();
                        }
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(AddNavigationItemActivity.this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }

}
