package com.example.myapplication.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private LinearLayout userInfoContainer;
    private String accountType;
    private Button updateUserInfoButton;
    private Button deleteUserButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        userInfoContainer = findViewById(R.id.user_info_container);
        updateUserInfoButton = findViewById(R.id.update_user_info_button);
        deleteUserButton = findViewById(R.id.delete_user_button);
        saveButton = findViewById(R.id.save_button);

        Button photoButton = findViewById(R.id.photo_button);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateProfileActivity.this, ProfilePhotoActivity.class));
            }
        });

        updateUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoContainer.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                updateUserInfoButton.setVisibility(View.GONE);
                deleteUserButton.setVisibility(View.GONE);
                fetchUserData();
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
                finish();
            }
        });
    }

    private void fetchUserData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String firstName = response.optString("firstName");
                                String lastName = response.optString("lastName");
                                String email = response.optString("email");
                                String password = response.optString("password");
                                accountType = response.optString("accountType");

                                firstNameEditText.setText(firstName);
                                lastNameEditText.setText(lastName);
                                emailEditText.setText(email);
                                passwordEditText.setText(password);
                                confirmPasswordEditText.setText(password);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(UpdateProfileActivity.this, "Error parsing user data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(UpdateProfileActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                        }
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + userId;

            JSONObject userDetails = new JSONObject();
            try {
                userDetails.put("firstName", firstNameEditText.getText().toString());
                userDetails.put("lastName", lastNameEditText.getText().toString());
                userDetails.put("email", emailEditText.getText().toString());
                userDetails.put("password", passwordEditText.getText().toString());
                userDetails.put("accountType", accountType);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, userDetails,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(UpdateProfileActivity.this, "User info updated successfully", Toast.LENGTH_SHORT).show();
                            userInfoContainer.setVisibility(View.GONE);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(UpdateProfileActivity.this, "Error updating user info", Toast.LENGTH_SHORT).show();
                        }
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + userId;

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                    response -> {
                        Toast.makeText(UpdateProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                        finish();
                    },
                    error -> {
                        if (error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 404) {
                                Toast.makeText(UpdateProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateProfileActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UpdateProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }
}