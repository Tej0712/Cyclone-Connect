package com.example.myapplication.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

/**
 * WhoActivity is an activity that allows new users to specify their role within the application
 * (e.g., student, professor, or admin) before proceeding to the appropriate account creation screen.
 * It extends AppCompatActivity to provide compatibility support for different versions of Android.
 * <p>
 * This activity presents three buttons for the user to choose their role, and based on the selection,
 * it sets the account type in a singleton (AccountTypeHolder) and navigates to the corresponding account
 * creation activity.
 * <p>
 * Author: Abhay Prasanna Rao
 */
public class WhoActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. This method is responsible for setting the content view,
     * initializing UI components, and setting up event listeners for each button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who); // Set the layout for the WhoActivity

        // Initialize buttons for choosing the role
        // Button for selecting the student role
        Button studentButton = findViewById(R.id.student_button);
        // Button for selecting the professor role
        Button professorButton = findViewById(R.id.faculty_button);
        // Button for selecting the admin role
        Button adminButton = findViewById(R.id.Admin);
        //Button for selecting the advisor role
        Button advisorButton = findViewById(R.id.advisorbtn);

        // Set the click listener for the student button to set account type and navigate to the CreateAccountActivity
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTypeHolder.getInstance().setAccountType("STUDENT");
                startActivity(new Intent(WhoActivity.this, CreateAccountActivity.class));
            }
        });

        // Set the click listener for the professor button to set account type and navigate to the Create_Faculty_Account activity
        professorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTypeHolder.getInstance().setAccountType("PROFESSOR");
                startActivity(new Intent(WhoActivity.this, Create_Faculty_Account.class));
            }
        });

        // Set the click listener for the admin button to set account type and navigate back to the CreateAccountActivity, presumably with different parameters or states based on the role
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTypeHolder.getInstance().setAccountType("ADMIN");
                startActivity(new Intent(WhoActivity.this, CreateAccountActivity.class));
            }
        });

        advisorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTypeHolder.getInstance().setAccountType("ADVISOR");
                startActivity(new Intent(WhoActivity.this, CreateAccountActivity.class));
            }
        });
    }
}
