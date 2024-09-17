package com.example.myapplication.professor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.admin.AdminPage;
import com.example.myapplication.admin.HeadStart.admin_headstart;

public class ProfessorHome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to your XML layout file
        setContentView(R.layout.professor_home);

        Button headstart = findViewById(R.id.headstart);
        headstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfessorHome.this, admin_headstart.class));
            }
        });
    }



}
