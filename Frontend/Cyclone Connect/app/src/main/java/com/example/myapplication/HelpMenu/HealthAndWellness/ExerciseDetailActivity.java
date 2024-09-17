package com.example.myapplication.HelpMenu.HealthAndWellness;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExerciseDetailActivity extends AppCompatActivity {
    private TextView exerciseNameTV, caloriesTV, timeTV, benefitsTV;
    private LottieAnimationView exerciseLAV;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        exerciseNameTV = findViewById(R.id.idTVExerciseName);
        caloriesTV = findViewById(R.id.idTVCalories);
        timeTV = findViewById(R.id.idTVTime);
        benefitsTV = findViewById(R.id.idTVBenefits);
        exerciseLAV = findViewById(R.id.idExerciseLAV);

        long exerciseId = getIntent().getLongExtra("exerciseId", 0);
        String exerciseName = getIntent().getStringExtra("exerciseName");
        String imgUrl = getIntent().getStringExtra("imgUrl");
        int calories = getIntent().getIntExtra("calories", 0);
        int time = getIntent().getIntExtra("time", 0);

        exerciseNameTV.setText(exerciseName);
        caloriesTV.setText("Calories: " + calories + " KCAL");
        timeTV.setText("Time: " + time + " Min");
        exerciseLAV.setAnimationFromUrl(imgUrl);

        fetchExerciseBenefitsFromBackend(exerciseId);
    }

    private void fetchExerciseBenefitsFromBackend(long exerciseId) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/exercises/" + exerciseId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dataObject = response.getJSONObject("data");
                            JSONArray benefitsArray = dataObject.getJSONArray("benefits");
                            StringBuilder benefitsBuilder = new StringBuilder();
                            for (int i = 0; i < benefitsArray.length(); i++) {
                                JSONObject benefitObject = benefitsArray.getJSONObject(i);
                                String benefit = benefitObject.getString("benefits");
                                benefitsBuilder.append(i + 1).append(". ").append(benefit).append("\n");
                            }
                            benefitsTV.setText(benefitsBuilder.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ExerciseDetailActivity", "Error fetching exercise benefits: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}