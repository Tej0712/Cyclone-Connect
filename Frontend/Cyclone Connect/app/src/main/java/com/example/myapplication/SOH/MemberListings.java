package com.example.myapplication.SOH;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.Map;

public class MemberListings extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> userList;
    private TextView orgName;
    private long userId;
    private String clubName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group_membets);
        orgName = findViewById(R.id.userType);
        clubName = getIntent().getStringExtra("Club");
        orgName.setText("Group Members In " + clubName);
        userId = getUserIdFromPreferences();
        listView = findViewById(R.id.list);
        userList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.listview_item, userList);
        listView.setAdapter(arrayAdapter);

        final String url = "http://coms-309-046.class.las.iastate.edu:8080/api/organizations/user/" + userId;
        Log.i("URL", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.i("Message Returned", jsonArray.toString());
                try {
                    userList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject clubInfo = jsonArray.getJSONObject(i); // Get the i-th club object
                        JSONArray members = clubInfo.getJSONArray("members"); // Get the members array from the club object
                        for (int j = 0; j < members.length(); j++) {
                            JSONObject member = members.getJSONObject(j); // Get the j-th member object
                            String firstName = member.getString("firstName"); // Get the first name of the member
                            String lastName = member.getString("lastName"); // Get the last name of the member
                            String fullName = firstName + " " + lastName; // Combine first name and last name
                            userList.add(fullName); // Add the full name to the userList
                            Log.i("Member Name", fullName);
                        }
                    }
                    arrayAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }

    private long getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);
        return userId;
    }
}