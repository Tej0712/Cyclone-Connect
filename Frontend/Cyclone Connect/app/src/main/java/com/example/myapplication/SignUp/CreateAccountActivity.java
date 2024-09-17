package com.example.myapplication.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.example.myapplication.Login.UserLoginActivity;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * CreateAccountActivity is responsible for allowing a new user to create an account.
 * It supports input fields for the user's first name, last name, email, password, and
 * confirmation password. Upon successful account creation, the user is navigated to
 * the UserLoginActivity. This activity also integrates with a backend service using
 * a POST request to send the signup details.
 * <p>
 * Author: Abhay Prasanna Rao
 */
public class CreateAccountActivity extends AppCompatActivity {
    // UI elements for user input and action
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private String accountType;     // Stores the account type set by previous activities

    private boolean validateEmail(String email) {
        if (!email.endsWith("@iastate.edu")) {
            emailEditText.setError("Email must have @iastate.edu domain");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            passwordEditText.setError("Password must be at least 8 characters long");
            return false;
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            passwordEditText.setError("Password must contain at least one special character");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            passwordEditText.setError("Password must contain at least one number");
            return false;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        Button createAccountButton = findViewById(R.id.create_account_button);
        Intent intent = getIntent();
        accountType = AccountTypeHolder.getInstance().getAccountType();
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (!validateEmail(email)) {
                    return;
                }

                if (!validatePassword(password)) {
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(CreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                sendSignupRequest(firstName, lastName, email, password);
            }
        });
    }
    /**
     * Constructs and sends a signup request to the backend server using Volley. This method
     * constructs a JSON payload containing the user's first name, last name, email, password,
     * and account type. Handles both success and error responses from the server.
     *
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email address
     * @param password User's chosen password
     */
    private void sendSignupRequest(String firstName, String lastName, String email, String password) {
        try {
            final String requestBody = new JSONObject()
                    .put("firstName", firstName)
                    .put("lastName", lastName)
                    .put("email", email)
                    .put("password", password)
                    .put("accountType", accountType)
                    .toString();

            String backendUrl = "http://coms-309-046.class.las.iastate.edu:8080/api/signup";

            // Use StringRequest instead of JsonObjectRequest
            StringRequest stringRequest = new StringRequest(Request.Method.POST, backendUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle success response
                            Toast.makeText(CreateAccountActivity.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateAccountActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response
                            if (error.networkResponse != null) {
                                String errorMsg = new String(error.networkResponse.data);
                                Toast.makeText(CreateAccountActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CreateAccountActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                public byte[] getBody() throws com.android.volley.AuthFailureError {
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
