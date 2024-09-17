package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.BMI.BMI_Calc;
import com.example.myapplication.Calendar.WeekViewActivity;
import com.example.myapplication.CyFind.CyFind_Home;
import com.example.myapplication.CyMagicBall.MagicBall8;
import com.example.myapplication.CycloneAI.CycloneAI;
import com.example.myapplication.GPACalc.GPACalculatorActivity;
import com.example.myapplication.HeadStart.Headstart;
import com.example.myapplication.HelpMenu.Help_page;
import com.example.myapplication.Maps.Maps;
import com.example.myapplication.Notes.NotesListActivity;
import com.example.myapplication.SOH.StudentOrganizationsHub;
import com.example.myapplication.Weather.Weather_Page;
import com.example.myapplication.advisor.Advisor_Student_Chat;
import com.example.myapplication.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewFlipper);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.Home) {
                startActivity(new Intent(HomePage.this, HomePage.class));
            } else if (id == R.id.courses_button) {
                startActivity(new Intent(HomePage.this, Headstart.class));
            } else if (id == R.id.profile_button) {
                startActivity(new Intent(HomePage.this, Profile.class));
            }
            return true;
        });

        // Set up the image slider
        int[] imageIds = {R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6, R.id.image7, R.id.image8};
        String[] imageUrls = {
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image1.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image2.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image3.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image4.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image5.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image6.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image7.jpg",
                "http://coms-309-046.class.las.iastate.edu:8080/images/history/image8.jpg",
        };
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = findViewById(imageIds[i]);
            Picasso.get().load(imageUrls[i]).into(imageView);
        }

        fetchTrivia();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ChatwithAdvisor) {
            startActivity(new Intent(HomePage.this, Advisor_Student_Chat.class));
        } else if (id == R.id.Calendar) {
            startActivity(new Intent(HomePage.this, WeekViewActivity.class));
        } else if (id == R.id.Maps) {
            startActivity(new Intent(HomePage.this, Maps.class));
        } else if (id == R.id.Notes) {
            startActivity(new Intent(HomePage.this, NotesListActivity.class));
        } else if (id == R.id.Help) {
            startActivity(new Intent(HomePage.this, Help_page.class));
        } else if (id == R.id.CycloneAI) {
            startActivity(new Intent(HomePage.this, CycloneAI.class));
        } else if (id == R.id.CyFind) {
            startActivity(new Intent(HomePage.this, CyFind_Home.class));
        } else if (id == R.id.StudentOrg) {
            startActivity(new Intent(HomePage.this, StudentOrganizationsHub.class));
        } else if (id == R.id.btnCalculateGPA) {
            startActivity(new Intent(HomePage.this, GPACalculatorActivity.class));
        } else if (id == R.id.Weather) {
            startActivity(new Intent(HomePage.this, Weather_Page.class));
        }
        else if (id == R.id.BMIcalc) {
            startActivity(new Intent(HomePage.this, BMI_Calc.class));
        }
        else if (id == R.id.CyMagicBall8) {
            startActivity(new Intent(HomePage.this, MagicBall8.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fetchTrivia() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/trivia/3/details";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String imageUrl = response.getString("photoUrl");
                        String description = response.getString("description");
                        updateTriviaUI(imageUrl, description);
                        Log.d("TriviaFetch", "Image and description loaded successfully.");
                    } catch (JSONException e) {
                        Log.e("TriviaFetch", "JSON parsing error", e);
                    }
                }, error -> {
            Log.e("TriviaFetch", "Error fetching trivia", error);
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void updateTriviaUI(String imageUrl, String description) {
        TextView triviaText = findViewById(R.id.triviaText);
        triviaText.setText(description);
    }

    private void postTrivia(String photoUrl, String description) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/trivia/upload";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("photoUrl", photoUrl);
            jsonObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    System.out.println("Response: " + response.toString());
                }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}