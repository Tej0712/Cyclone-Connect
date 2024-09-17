package com.example.myapplication.admin.Help.Navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateNavigationItemActivity extends AppCompatActivity {

    private EditText etNavigationItemId, etNewDestination, etNewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_navigation_item);

        etNavigationItemId = findViewById(R.id.etNavigationItemId);
        etNewDestination = findViewById(R.id.etNewDestination);
        etNewDescription = findViewById(R.id.etNewDescription);
        Button btnSubmitUpdate = findViewById(R.id.btnSubmitUpdate);

        btnSubmitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateNavigationItem();
                } catch (Exception e) {
                    Toast.makeText(UpdateNavigationItemActivity.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateNavigationItem() {
        try {
            String itemId = etNavigationItemId.getText().toString();
            String url = "http://coms-309-046.class.las.iastate.edu:8080/navigations/" + itemId;

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            Long userId = preferences.getLong("userId", -1); // Use a default value if not found

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", itemId);
            jsonObject.put("userId", userId); // Include the userId field in the payload
            jsonObject.put("destination", etNewDestination.getText().toString());
            jsonObject.put("description", etNewDescription.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                    response -> {
                        Toast.makeText(UpdateNavigationItemActivity.this, "Navigation item updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Return to the previous activity (possibly showing the list of items)
                    },
                    error -> {
                        Toast.makeText(UpdateNavigationItemActivity.this, "Error updating item: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("UpdateNavigationItem", "Error updating item", error);
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            Toast.makeText(UpdateNavigationItemActivity.this, "Error creating JSON object: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("UpdateNavigationItem", "Error creating JSON object", e);
        }
    }

}
