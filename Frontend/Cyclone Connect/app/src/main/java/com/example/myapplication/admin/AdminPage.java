package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.admin.Help.EmergencyContacts.admin_EmergencyContactUpdatesActivity;
import com.example.myapplication.admin.Help.Exercise.admin_exercise;
import com.example.myapplication.admin.Help.Navigation.admin_NavigationApp;
import com.example.myapplication.admin.Help.admin_bugs_chat;
import com.example.myapplication.admin.Users.admin_fetchusers;
import com.example.myapplication.admin.HeadStart.admin_headstart;
import com.example.myapplication.admin.cyfind.admin_delete_cyfind;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnFetchUsers = findViewById(R.id.btnFetchUsers);
        Button btnBugsChat = findViewById(R.id.btnBugsChat);
        Button Emergency_updates_btn = findViewById(R.id.EmerUpdates);
        Button Navigation_btn = findViewById(R.id.NavUpdates);
        Button User_edit_btn = findViewById(R.id.UserUpdates);
        Button headstart = findViewById(R.id.HeadStart);
        Button Exercisebtn = findViewById(R.id.Exercise);
        Button CyFind = findViewById(R.id.CyFind);


        btnFetchUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to admin_fetchusers
                startActivity(new Intent(AdminPage.this, admin_fetchusers.class));
            }
        });
        CyFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to admin_fetchusers
                startActivity(new Intent(AdminPage.this, admin_delete_cyfind.class));
            }
        });
        Exercisebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPage.this, admin_exercise.class));
            }
        });

        btnBugsChat.setOnClickListener(view -> {

            startActivity(new Intent(AdminPage.this, admin_bugs_chat.class));
        });

        Navigation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to admin_fetchusers
                startActivity(new Intent(AdminPage.this, admin_NavigationApp.class));
            }
        });




        Emergency_updates_btn.setOnClickListener(view -> {
            startActivity(new Intent(AdminPage.this, admin_EmergencyContactUpdatesActivity.class));
        });

        headstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to admin_fetchusers
                startActivity(new Intent(AdminPage.this, admin_headstart.class));
            }
        });

        User_edit_btn.setOnClickListener(view -> showToast("Edit User Info"));

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
