package com.example.myapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * UserLoginActivity provides a user interface for logging into the application. It handles user input for
 * email and password, performs the login operation by communicating with a backend server, and navigates
 * the user to the HomePage upon successful login.
 *
 * This activity uses Volley for making the network request and SharedPreferences for storing the userId
 * obtained from the login response, facilitating user session management across the application.
 *
 * Author: Abhay Prasanna Rao
 */
public class UserLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private final String loginUrl = "http://coms-309-046.class.las.iastate.edu:8080/api/login";

    /**
     * Called when the activity is starting. This method initializes the UI components, retrieves the
     * views from the layout, and sets up a click listener for the login button to initiate the login
     * process with the provided credentials.
     *
     * @param savedInstanceState If the activity is being re-initialized after being previously shut down,
     *                           this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        Button loginButton = findViewById(R.id.loginbutton);

        setupPasswordVisibilityToggle();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                loginUser(email, password);
            }
        });
    }

    /**
     * Setup toggle to change the visibility of the password.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setupPasswordVisibilityToggle() {
        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    } else {
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    }
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            emailEditText.setError("Email cannot be empty");
            return false;
        }

        if (!email.endsWith("@iastate.edu")) {
            emailEditText.setError("Email must have @iastate.edu domain");
            return false;
        }

        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            passwordEditText.setError("Password cannot be empty");
            return false;
        }

        return true;
    }


    /**
     * Initiates the login process by sending the user's email and password to the backend server. Upon
     * receiving a successful response, it stores the userId in SharedPreferences and navigates the user
     * to the HomePage activity. In case of failure, it displays an appropriate error message to the user.
     *
     * @param email The email address provided by the user.
     * @param password The password provided by the user.
     */
    private void loginUser(String email, String password) {
        if (!validateEmail(email) || !validatePassword(password)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, loginUrl,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response
                            String message = response.getString("message");
                            long userId = response.getLong("userId"); // Extract the userId
                            Log.i("User ID", String.valueOf(userId));

                            // Save userId in SharedPreferences
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserLoginActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong("userId", userId);
                            editor.apply();

                            // Notify user and navigate
                            Toast.makeText(UserLoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserLoginActivity.this, HomePage.class);
                            intent.putExtra("ID", userId);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserLoginActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handling error response
                        if (error.networkResponse != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Toast.makeText(UserLoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(loginRequest);
    }
}
