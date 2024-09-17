package com.example.myapplication.HelpMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HelpMenu.EmergencyContacts.EmergencyContacts;
import com.example.myapplication.HelpMenu.Navigate.NavigateApp;
import com.example.myapplication.R;
import com.example.myapplication.HelpMenu.ReportBugs.ReportBugs;

/**
 * The Help_page class provides a user interface that offers various help options to the users of the application.
 * This includes buttons for emergency contacts, health and wellness resources, app navigation help, and reporting bugs.
 * Each button, when clicked, navigates the user to a different activity that provides the selected service.
 *
 * This activity serves as a central hub for users seeking assistance or information about the application and its services.
 *
 * Author: Abhay Prasanna Rao
 */
public class Help_page extends AppCompatActivity {
    /**
     * Called when the activity is starting. This method initializes the UI components from the layout resource,
     * and sets up click listeners for each button to navigate to the respective activities.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        // Initialize UI components
        Button Emergencybutton = findViewById(R.id.button);
        Button HealthAndWellness = findViewById(R.id.hwb);
        Button NavigateButton = findViewById(R.id.navigate);
        Button ReportBugsButton = findViewById(R.id.RB);
        // Configure click listener for the Emergency Contacts button

        ImageButton backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This will close the current activity
            }
        });
        Emergencybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open profile activity
                Intent intent = new Intent(Help_page.this, EmergencyContacts.class);
                startActivity(intent);
            }
        });
        // Configure click listener for the Health and Wellness button
        HealthAndWellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open profile activity
                Intent intent = new Intent(Help_page.this, com.example.myapplication.HelpMenu.HealthAndWellness.HealthAndWellness.class);
                startActivity(intent);
            }
        });
        // Configure click listener for the Navigate Application button
        NavigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open profile activity
                Intent intent = new Intent(Help_page.this, NavigateApp.class);
                startActivity(intent);
            }
        });
        // Configure click listener for the Report Bugs button
        ReportBugsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open profile activity
                Intent intent = new Intent(Help_page.this, ReportBugs.class);
                startActivity(intent);
            }
        });

    }

}
