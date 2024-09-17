package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WhoActivity extends AppCompatActivity {

    private Button studentButton;
    private Button facultyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);

        studentButton = findViewById(R.id.student_button);
        facultyButton = findViewById(R.id.faculty_button);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhoActivity.this, CreateAccountActivity.class);
                intent.putExtra("ACCOUNT_TYPE", "Student");
                startActivity(intent);
            }
        });

        facultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhoActivity.this, Create_Faculty_Account.class);
                intent.putExtra("ACCOUNT_TYPE", "Faculty");
                startActivity(intent);
            }
        });
    }
}
