package com.example.myapplication.admin.Help.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HelpMenu.Navigate.NavigateApp;
import com.example.myapplication.R;

public class admin_NavigationApp extends AppCompatActivity {

    Button btnViewAll, btnAdd, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_navigation);

        // Initialize buttons
        btnViewAll = findViewById(R.id.GET);
        btnAdd = findViewById(R.id.Add);
        btnUpdate = findViewById(R.id.Update);
        btnDelete = findViewById(R.id.Delete);

        // View all navigation items
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_NavigationApp.this, NavigateApp.class);
                startActivity(intent);
            }
        });

        // Add a new navigation item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_NavigationApp.this, AddNavigationItemActivity.class);
                startActivity(intent);
            }
        });

        // Update an existing navigation item
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_NavigationApp.this, UpdateNavigationItemActivity.class);
                startActivity(intent);
            }
        });

        // Delete a navigation item
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_NavigationApp.this, DeleteNavigationItemActivity.class);
                startActivity(intent);
            }
        });
    }
}
