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
 * @author Abhay Prasanna Rao
 * This activity provides a user interface for faculty members to create an account within the application. It collects
 * information such as first name, last name, email, and password. The account creation process includes a validation
 * step to ensure that the entered passwords match. Upon successful account creation, the user is directed to the
 * UserLoginActivity to log into the application.
 *
 * The activity leverages Volley for network communication to send account information to a backend server via a POST request.
 * It handles both successful and error responses from the server to provide feedback to the user.
 */
public class Create_Faculty_Account extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    // Stores the selected account type as determined in prior activities or selections within the app
    private String accountType;

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

    /**
     * Initializes the activity. This includes setting up the user interface from the layout resource,
     * initializing member variables by binding them to the respective views in the layout, and setting
     * up a click listener for the account creation button.
     *
     * @param savedInstanceState If the activity is being re-initialized after being previously shut down,
     *                           this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_faculty_account);

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
                    Toast.makeText(Create_Faculty_Account.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendSignupRequest(firstName, lastName, email, password);
            }
        });
    }
    /**
     * Sends a request to the backend server to create a new faculty account using the provided information.
     * Constructs a JSON payload with the user's first name, last name, email, password, and account type,
     * and sends this to the server via a POST request. Handles both success and error responses from the server.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param password The password for the new account.
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
                            Toast.makeText(Create_Faculty_Account.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Create_Faculty_Account.this, UserLoginActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response
                            if (error.networkResponse != null) {
                                String errorMsg = new String(error.networkResponse.data);
                                Toast.makeText(Create_Faculty_Account.this, errorMsg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Create_Faculty_Account.this, "Error creating account", Toast.LENGTH_SHORT).show();
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
