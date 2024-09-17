package com.example.myapplication.SOH;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class ClubDetailsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_USER_ID = "userId";

    private TextView textViewClubName;
    private TextView textViewClubDescription;
    private Button buttonJoinLeave, members;

    private long userId;
    private int clubId;
    private boolean isJoined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        textViewClubName = findViewById(R.id.textViewClubName);
        textViewClubDescription = findViewById(R.id.textViewClubDescription);
        buttonJoinLeave = findViewById(R.id.buttonJoinLeave);
        members = findViewById(R.id.members);

        clubId = getIntent().getIntExtra("clubId", 0);
        Log.i("Club ID", String.valueOf(clubId));
        userId = getUserIdFromPreferences();
        Log.i("Student ID",String.valueOf(userId));

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubDetailsActivity.this, MemberListings.class);
                String clubName = textViewClubName.getText().toString();
                intent.putExtra("Club",clubName);
                startActivity(intent);
            }
        });


        fetchClubDetails();

        buttonJoinLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isJoined) {
                    leaveClub();
                } else {
                    joinClub();
                }
            }
        });
    }

    private long getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);
        return userId;
    }

    private void fetchClubDetails() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/" + clubId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String description = response.getString("description");
                            textViewClubName.setText(name);
                            textViewClubDescription.setText(description);
                            checkIfJoined();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(ClubDetailsActivity.this, "Failed to parse club details", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(ClubDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ClubDetailsActivity", "Error fetching club details: " + error.toString());
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void checkIfJoined() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/" + userId + "/check/" + clubId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            isJoined = response.getBoolean("joined");
                            updateJoinLeaveButton();
                        } catch (JSONException e) {
                            e.printStackTrace();
                          //  Toast.makeText(ClubDetailsActivity.this, "Failed to check if joined", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(ClubDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ClubDetailsActivity", "Error checking if joined: " + error.toString());
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void updateJoinLeaveButton() {
        if (isJoined) {
            buttonJoinLeave.setText("Leave Club");
        } else {
            buttonJoinLeave.setText("Join Club");
        }
    }

    private void joinClub() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/" + userId + "/join/" + clubId;
        Log.i("URL Sending Request",url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response Complete",response.toString());
                        try {
                            String message = response.getString("message");
                            Log.i("Message Returned",message);
                            if (message.equals("User has joined the organization successfully")) {
                                Log.i("Message","EQUALS");
                                //Toast.makeText(ClubDetailsActivity.this, "Joined club successfully", Toast.LENGTH_SHORT).show();
                                isJoined = true;
                                //updateJoinLeaveButton();
                                finish();
                            } else {
                                //Toast.makeText(ClubDetailsActivity.this, "Failed Join", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ClubDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ClubDetailsActivity", "Error joining club: " + error.toString());
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void leaveClub() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/" + userId + "/leave/" + clubId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                boolean success = response.getBoolean("success");
                                if (success) {
                                    //Toast.makeText(ClubDetailsActivity.this, "Left club successfully", Toast.LENGTH_SHORT).show();
                                    isJoined = false;
                                    updateJoinLeaveButton();
                                } else {
                                    String message = response.optString("message", "Failed to leave club");
                                   // Toast.makeText(ClubDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Toast.makeText(ClubDetailsActivity.this, "Invalid response format", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           // Toast.makeText(ClubDetailsActivity.this, "Empty response from server", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ClubDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ClubDetailsActivity", "Error leaving club: " + error.toString());
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}