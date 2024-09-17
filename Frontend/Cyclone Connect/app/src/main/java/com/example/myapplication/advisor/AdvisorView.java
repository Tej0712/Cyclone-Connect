package com.example.myapplication.advisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.admin.HeadStart.admin_headstart;

public class AdvisorView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adivisor_view);

        Button studentchat = findViewById(R.id.StudentChat);
        Button headstart = findViewById(R.id.Head_Start);

        studentchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open profile activity
                Intent intent = new Intent(AdvisorView.this, Advisor_Student_Chat.class);
                startActivity(intent);
            }
        });
        headstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open profile activity
                Intent intent = new Intent(AdvisorView.this, admin_headstart.class);
                startActivity(intent);
            }
        });

    }
}
