package com.example.myapplication.admin.Help.EmergencyContacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HelpMenu.EmergencyContacts.EmergencyContacts;
import com.example.myapplication.R;

public class admin_EmergencyContactUpdatesActivity extends AppCompatActivity {

    Button btnViewAll, btnAdd, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact_updates);

        // Initialize buttons
        btnViewAll = findViewById(R.id.GET);
        btnAdd = findViewById(R.id.Add);
        btnUpdate = findViewById(R.id.Update);
        btnDelete = findViewById(R.id.Delete);

        // View all navigation items
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_EmergencyContactUpdatesActivity.this, EmergencyContacts.class);
                startActivity(intent);
            }
        });

        // Add a new navigation item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_EmergencyContactUpdatesActivity.this, admin_Emergency_addcontact.class);
                startActivity(intent);
            }
        });

        // Update an existing emergency contact
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_EmergencyContactUpdatesActivity.this, admin_Emergency_updatecontact.class);
                startActivity(intent);
            }
        });

// Delete an emergency contact
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_EmergencyContactUpdatesActivity.this, admin_Emergency_deletecontact.class);
                startActivity(intent);
            }
        });
    }
}
