package com.example.myapplication.admin.Users;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class fetches the users for the admin
 */
public class admin_fetchusers extends AppCompatActivity {

    private TextView tvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_fetchuser);

        Button btnFetchUsers = findViewById(R.id.btnFetchUsers);
        tvUsers = findViewById(R.id.tvUsers);

        btnFetchUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUsers();
            }
        });
    }

    private void fetchUsers() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray users = new JSONArray(response);
                            StringBuilder usersText = new StringBuilder();
                            for (int i = 0; i < users.length(); i++) {
                                String email = users.getJSONObject(i).getString("email");
                                // Append other user details you want to display
                                usersText.append(email).append("\n");
                            }
                            tvUsers.setText(usersText.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(admin_fetchusers.this, "Error parsing users", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_fetchusers.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}

