package com.example.myapplication.SOH;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentOrganizationsHub extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_USER_ID = "userId";

    private RecyclerView recyclerViewMyClubs;
    private RecyclerView recyclerViewAllClubs;
    private ClubAdapter myClubsAdapter;
    private ClubAdapter allClubsAdapter;
    private List<Club> myClubs = new ArrayList<>();
    private List<Club> allClubs = new ArrayList<>();

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_organizations_hub);

        //userClubs();

        //recyclerViewMyClubs = findViewById(R.id.recyclerViewMyClubs);
        recyclerViewAllClubs = findViewById(R.id.recyclerViewAllClubs);

        //myClubsAdapter = new ClubAdapter(myClubs, true, this);
        allClubsAdapter = new ClubAdapter(allClubs, false, this);

        //recyclerViewMyClubs.setLayoutManager(new LinearLayoutManager(this));
        //recyclerViewMyClubs.setAdapter(myClubsAdapter);

        recyclerViewAllClubs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAllClubs.setAdapter(allClubsAdapter);

        userId = getUserIdFromPreferences();

        if (userId == -1) {
            // Generate a new user ID and save it to preferences
            userId = generateNewUserId();
            saveUserIdToPreferences(userId);
        }

        fetchAllClubs();
//        fetchUserClubs();
    }

//    private void userClubs() {
//        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/"+userId+"/organizations";
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            myClubs.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject clubObject = response.getJSONObject(i);
//                                int id = clubObject.getInt("id");
//                                String name = clubObject.getString("name");
//                                String description = clubObject.getString("description");
//                                Club userClub = new Club(id, name, description);
//                                myClubs.add(userClub);
//                            }
//                            myClubsAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(StudentOrganizationsHub.this, "Failed to parse user's club data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StudentOrganizationsHub.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        VolleySingleton.getInstance(this).addToRequestQueue(request);
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        fetchUserClubs();
    }

    private long generateNewUserId() {
        // Generate a new user ID (you can use your own logic here)
        return System.currentTimeMillis();
    }

    private void fetchAllClubs() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            allClubs.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject clubObject = response.getJSONObject(i);
                                int id = clubObject.getInt("id");
                                String name = clubObject.getString("name");
                                String description = clubObject.getString("description");
                                Club club = new Club(id, name, description);
                                allClubs.add(club);
                            }
                            allClubsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                         //   Toast.makeText(StudentOrganizationsHub.this, "Failed to parse club data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(StudentOrganizationsHub.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void fetchUserClubs() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/user/" + userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            myClubs.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject clubObject = response.getJSONObject(i);
                                int id = clubObject.getInt("id");
                                String name = clubObject.getString("name");
                                String description = clubObject.getString("description");
                                Club club = new Club(id, name, description);
                                myClubs.add(club);
                            }
                            myClubsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(StudentOrganizationsHub.this, "Failed to parse user club data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(StudentOrganizationsHub.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private long getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getLong(KEY_USER_ID, -1);
    }

    private void saveUserIdToPreferences(long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }
}