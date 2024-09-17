package com.example.myapplication.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.myapplication.HeadStart.Headstart;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.admin.AdminPage;
import com.example.myapplication.advisor.AdvisorView;
import com.example.myapplication.helper.VolleySingleton;
import com.example.myapplication.professor.ProfessorHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private EditText emailEditText, firstNameEditText, lastNameEditText, passwordEditText, accountTypeEditText, UserIDEditText;
    private Button updateButton, adminButton, advisorButton, professorButton;
    private ImageView profileImageView;
    private static final String USER_PROFILE_PHOTO_URL = "http://coms-309-046.class.las.iastate.edu:8080/api/users/{userId}/profilePhoto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeViews();
        fetchUserData();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.Home) {
                startActivity(new Intent(Profile.this, HomePage.class));
            } else if (id == R.id.courses_button) {
                startActivity(new Intent(Profile.this, Headstart.class));
            } else if (id == R.id.profile_button) {
                startActivity(new Intent(Profile.this, Profile.class));
            }
            return true;
        });
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.Email);
        firstNameEditText = findViewById(R.id.First_Name);
        lastNameEditText = findViewById(R.id.Last_name);
        passwordEditText = findViewById(R.id.Password);
        accountTypeEditText = findViewById(R.id.Account_type);
        UserIDEditText = findViewById(R.id.User_ID);
        updateButton = findViewById(R.id.Update_button);
        adminButton = findViewById(R.id.adminbutton);
        advisorButton = findViewById(R.id.advisor_btn);
        professorButton = findViewById(R.id.professor_btn);
        profileImageView = findViewById(R.id.imageView);

        adminButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, AdminPage.class);
            startActivity(intent);
        });

        advisorButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, AdvisorView.class);
            startActivity(intent);
        });

        professorButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, ProfessorHome.class);
            startActivity(intent);
        });

        updateButton.setOnClickListener(v -> {
            Intent updateIntent = new Intent(Profile.this, UpdateProfileActivity.class);
            startActivity(updateIntent);
        });
    }

    private void fetchUserData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + userId;

            VolleySingleton.getInstance(this).sendGetRequest(url,
                    response -> {
                        Log.d("ProfileActivity", "Response: " + response.toString());
                        onUserFetched(response);
                        fetchProfilePhoto(userId);
                    },
                    error -> {
                        Log.e("ProfileActivity", "Error: " + error.toString());
                        Toast.makeText(this, "Error fetching user details", Toast.LENGTH_SHORT).show();
                    }
            );
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void onUserFetched(JSONObject response) {
        try {
            String email = response.optString("email");
            String firstName = response.optString("firstName");
            String lastName = response.optString("lastName");
            String password = response.optString("password");
            String accountType = response.optString("accountType");
            String userId = response.optString("userId");

            emailEditText.setText(email);
            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            passwordEditText.setText(password);
            UserIDEditText.setText(userId);
            accountTypeEditText.setText(accountType);

            updateUiBasedOnAccountType(accountType);
        } catch (Exception e) {
            Toast.makeText(this, "Error parsing user details", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void updateUiBasedOnAccountType(String accountType) {
        adminButton.setVisibility(View.GONE);
        advisorButton.setVisibility(View.GONE);
        professorButton.setVisibility(View.GONE);
        updateButton.setVisibility(View.VISIBLE); // Ensure the update button is always visible

        if (accountType != null) {
            switch (accountType.toUpperCase()) {
                case "ADMIN":
                    adminButton.setVisibility(View.VISIBLE);
                    advisorButton.setVisibility(View.VISIBLE);
                    professorButton.setVisibility(View.VISIBLE);
                    break;
                case "ADVISOR":
                    advisorButton.setVisibility(View.VISIBLE);
                    break;
                case "PROFESSOR":
                    professorButton.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void fetchProfilePhoto(long userId) {
        String photoUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/" + userId + "/image";

        ImageRequest imageRequest = new ImageRequest(
                photoUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        profileImageView.setImageBitmap(response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        profileImageView.setImageResource(R.drawable.user);
                    }
                }
        );

        VolleySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }
}
