package com.example.myapplication.admin.Help.EmergencyContacts;

import android.os.Bundle;
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

public class admin_Emergency_addcontact extends AppCompatActivity {

    private EditText editTextContactName, editTextPhoneNumber, editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        editTextContactName = findViewById(R.id.editTextContactName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitNewContact();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void submitNewContact() throws JSONException {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/emergencies";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contactName", editTextContactName.getText().toString());
        jsonObject.put("phoneNumber", editTextPhoneNumber.getText().toString());
        jsonObject.put("description", editTextDescription.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    Toast.makeText(admin_Emergency_addcontact.this, "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                    finish(); // This will close the current activity and return to the previous one
                },
                error -> Toast.makeText(admin_Emergency_addcontact.this, "Failed to Add Contact", Toast.LENGTH_SHORT).show());

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}
