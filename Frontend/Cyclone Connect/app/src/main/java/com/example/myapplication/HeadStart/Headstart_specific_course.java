package com.example.myapplication.HeadStart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;

public class Headstart_specific_course extends AppCompatActivity {

    private TextView courseNameTextView;
    private TextView courseIdTextView;
    private TextView courseDescriptionTextView;
    private TextView prerequisitesTextView;
    private TextView totalCreditsTextView;
    private TextView programmingLanguagesTextView;
    private TextView frameworkTextView;
    private TextView difficultyLevelTextView;
    private TextView timeComplexityTextView;
    private TextView resourcesTextView;
    private TextView embeddedLinksTextView;
    private TextView roadMapTextView;
    private TextView reviewsTextView;
    private TextView ratingTextView;
    private TextView editedByTextView;
    private LinearLayout programmingLanguagesImagesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headstart_specific_course);

        TextView courseNumberTextView = findViewById(R.id.courseNumberTextView);
        courseNameTextView = findViewById(R.id.courseNameTextView);
        courseIdTextView = findViewById(R.id.courseIdTextView);
        courseDescriptionTextView = findViewById(R.id.courseDescriptionTextView);
        prerequisitesTextView = findViewById(R.id.prerequisitesTextView);
        totalCreditsTextView = findViewById(R.id.totalCreditsTextView);
        programmingLanguagesTextView = findViewById(R.id.programmingLanguagesTextView);
        frameworkTextView = findViewById(R.id.frameworkTextView);
        difficultyLevelTextView = findViewById(R.id.difficultyLevelTextView);
        timeComplexityTextView = findViewById(R.id.timeComplexityTextView);
        resourcesTextView = findViewById(R.id.resourcesTextView);
        embeddedLinksTextView = findViewById(R.id.embeddedLinksTextView);
        embeddedLinksTextView.setMovementMethod(LinkMovementMethod.getInstance());
        roadMapTextView = findViewById(R.id.roadMapTextView);
        reviewsTextView = findViewById(R.id.reviewsTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        editedByTextView = findViewById(R.id.editedByTextView);
        programmingLanguagesImagesLayout = findViewById(R.id.programmingLanguagesImagesLayout);

        String courseNumber = getIntent().getStringExtra("courseNumber");
        if (courseNumber != null) {
            courseNumberTextView.setText(courseNumber);
        }

        long courseID = getIntent().getLongExtra("courseID", -1);
        if (courseID != -1) {
            fetchCourseSpecifications(courseID);
        } else {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCourseSpecifications(Long courseID) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/courseSpecifications/course/" + courseID;

        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String courseName = response.getString("courseName");
                        String courseId = response.getString("courseId");
                        String courseDescription = response.getString("courseDescription");
                        String prerequisites = response.getString("prerequisites");
                        String totalCredits = response.getString("totalCredits");
                        String programmingLanguages = response.getString("programmingLanguages");
                        String framework = response.getString("framework").trim();
                        String difficultyLevel = response.getString("difficultyLevel");
                        String timeComplexity = response.getString("timeComplexity");
                        String resources = response.getString("resources");
                        String embeddedLinks = response.getString("embeddedLinks");
                        String roadMap = response.getString("roadMap");
                        String reviews = response.getString("reviews");
                        String rating = response.getString("rating");
                        String editedBy = response.getString("editedBy");

                        courseNameTextView.setText(courseName);
                        courseIdTextView.setText("Course ID: " + courseId);
                        courseDescriptionTextView.setText("Course Description: " + courseDescription);
                        prerequisitesTextView.setText("Prerequisites: " + prerequisites);
                        totalCreditsTextView.setText("Total Credits: " + totalCredits);
                        programmingLanguagesTextView.setText("Programming Languages: " + programmingLanguages);
                        displayProgrammingLanguageImages(programmingLanguages);
                        frameworkTextView.setText("Framework: " + framework);
                        difficultyLevelTextView.setText("Difficulty Level: " + difficultyLevel);
                        timeComplexityTextView.setText("Time Complexity: " + timeComplexity);
                        resourcesTextView.setText("Resources: " + resources);

                        StringBuilder embeddedLinksBuilder = new StringBuilder();
                        String[] urlArray = embeddedLinks.split("\\s+");
                        for (String link : urlArray) {
                            embeddedLinksBuilder.append("Embedded Resource link: ");
                            embeddedLinksBuilder.append(link);
                            embeddedLinksBuilder.append("\n");
                        }

                        SpannableString spannableString = new SpannableString(embeddedLinksBuilder.toString());
                        for (String link : urlArray) {
                            int startIndex = embeddedLinksBuilder.toString().indexOf(link);
                            int endIndex = startIndex + link.length();
                            ClickableSpan clickableSpan = new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                    startActivity(intent);
                                }
                            };
                            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        embeddedLinksTextView.setText(spannableString);

                        roadMapTextView.setText("Road Map: " + roadMap);
                        reviewsTextView.setText("Reviews: " + reviews);
                        ratingTextView.setText("Rating: " + rating);
                        editedByTextView.setText("Edited By: " + editedBy);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Headstart_specific_course.this, "Failed to parse course specifications", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(Headstart_specific_course.this, "Failed to fetch course specifications", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void displayProgrammingLanguageImages(String programmingLanguages) {
        String[] languages = programmingLanguages.split(",");
        programmingLanguagesImagesLayout.removeAllViews();

        int imageSizeInDp = 110; // Adjust the size as needed
        int imageSizeInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                imageSizeInDp,
                getResources().getDisplayMetrics()
        );

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                imageSizeInPx,
                imageSizeInPx
        );
        params.setMargins(0, 0, 16, 0);

        for (String language : languages) {
            String languageName = language.trim().toLowerCase();
            int imageResId = getImageResourceId(languageName);

            if (imageResId != 0) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(imageResId);
                imageView.setLayoutParams(params);

                GradientDrawable backgroundDrawable = new GradientDrawable();
                backgroundDrawable.setShape(GradientDrawable.OVAL);
                backgroundDrawable.setColor(Color.WHITE);
                backgroundDrawable.setStroke(2, Color.GRAY);

                imageView.setBackground(backgroundDrawable);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                programmingLanguagesImagesLayout.addView(imageView);
            }
        }
    }

    private int getImageResourceId(String languageName) {
        switch (languageName) {
            case "python":
                return R.drawable.python;
            case "java":
                return R.drawable.java;
            case "c++":
                return R.drawable.cplus;
            case "c#":
                return R.drawable.csharp;
            case "c":
                return R.drawable.c;
            case "R":
                return R.drawable.r;
            case "React":
                return R.drawable.react;
            case "MySQL":
                return R.drawable.mysql;
            // Add more cases for other programming languages
            default:
                return 0;
        }
    }
}