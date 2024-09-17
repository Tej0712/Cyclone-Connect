package com.example.myapplication.HelpMenu.HealthAndWellness;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.myapplication.R;

import java.util.ArrayList;

//public class ExerciseActivity extends AppCompatActivity implements ExerciseRVAdapter.ExerciseClickInterface {
//
//    private ArrayList<ExerciseRVModal> exerciseRVModalArrayList;
//    private ExerciseRVAdapter exerciseRVAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exercise);
//        RecyclerView exerciseRV = findViewById(R.id.idRVExercise);
//        exerciseRVModalArrayList = new ArrayList<>();
//        exerciseRVAdapter = new ExerciseRVAdapter(exerciseRVModalArrayList, this);
//        exerciseRV.setLayoutManager(new LinearLayoutManager(this));
//        exerciseRV.setAdapter(exerciseRVAdapter);
//        addData();
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private void addData() {
//        exerciseRVModalArrayList.add(new ExerciseRVModal("Side Plank", getResources().getString(R.string.side_plank), "https://lottie.host/ccf1a3b2-bda3-455a-aefd-cc32f1a511fe/wT38vTq1Dr.json", 20, 10));
//        exerciseRVModalArrayList.add(new ExerciseRVModal("Lunges", getResources().getString(R.string.lunges), "https://lottie.host/7f79ef0c-3ce1-41c8-843f-35d6bd0ce77f/dgIQMbafF6.json", 20, 10));
//        exerciseRVModalArrayList.add(new ExerciseRVModal("High Stepping", getResources().getString(R.string.stepping), "https://lottie.host/cbf039b5-03f5-483d-bdae-892a6cf08ec5/XqGALV6c3E.json", 40, 10));
//        exerciseRVModalArrayList.add(new ExerciseRVModal("Abs Crunches", getResources().getString(R.string.abs_crunches), "https://lottie.host/6a692e81-2cc2-4767-9c69-83081479cd2a/7Ql2DebrX8.json", 50, 10));
//        exerciseRVModalArrayList.add(new ExerciseRVModal("Push Ups", getResources().getString(R.string.push_ups), "https://lottie.host/92f53510-5f96-4b55-b46f-36a8e5e9db8b/nIGtor0JGU.json", 10, 5));
//        exerciseRVAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onExerciseClick(int position) {
//        Intent i = new Intent(ExerciseActivity.this, ExerciseDetailActivity.class);
//        i.putExtra("exerciseName", exerciseRVModalArrayList.get(position).getExerciseName());
//        i.putExtra("imgUrl", exerciseRVModalArrayList.get(position).getImgUrl());
//        i.putExtra("time", exerciseRVModalArrayList.get(position).getTime());
//        i.putExtra("calories", exerciseRVModalArrayList.get(position).getCalories());
//        i.putExtra("desc", exerciseRVModalArrayList.get(position).getExerciseDescription());
//        startActivity(i);
//    }
//}


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements ExerciseRVAdapter.ExerciseClickInterface {
    private List<Exercise> exerciseList;
    private ExerciseRVAdapter exerciseRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        RecyclerView exerciseRV = findViewById(R.id.idRVExercise);
        exerciseList = new ArrayList<>();
        exerciseRVAdapter = new ExerciseRVAdapter(exerciseList, this);
        exerciseRV.setLayoutManager(new LinearLayoutManager(this));
        exerciseRV.setAdapter(exerciseRVAdapter);

        fetchExercisesFromBackend();
    }

    private void fetchExercisesFromBackend() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/exercises";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray exercisesArray = response.getJSONArray("data");
                            for (int i = 0; i < exercisesArray.length(); i++) {
                                JSONObject exerciseObject = exercisesArray.getJSONObject(i);
                                String exerciseName = exerciseObject.getString("exerciseName");
                                int time = exerciseObject.getInt("time");
                                String imageUrl = exerciseObject.getString("imageUrl");
                                int caloriesBurned = exerciseObject.getInt("caloriesBurned");
                                long exerciseId = exerciseObject.getLong("id");

                                Exercise exercise = new Exercise(exerciseId, exerciseName, time, caloriesBurned, imageUrl);
                                exerciseList.add(exercise);
                            }
                            exerciseRVAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ExerciseActivity", "Error fetching exercises: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onExerciseClick(int position) {
        Intent i = new Intent(ExerciseActivity.this, ExerciseDetailActivity.class);
        Exercise exercise = exerciseList.get(position);
        i.putExtra("exerciseId", exercise.getId());
        i.putExtra("exerciseName", exercise.getExerciseName());
        i.putExtra("imgUrl", exercise.getImageUrl());
        i.putExtra("time", exercise.getTime());
        i.putExtra("calories", exercise.getCaloriesBurned());
        startActivity(i);
    }
}