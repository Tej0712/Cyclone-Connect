package com.example.myapplication.HeadStart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;
import com.example.myapplication.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Headstart extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private MajorAdapter adapter;
    private final List<Major> majorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headstart);

        RecyclerView recyclerView = findViewById(R.id.majorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MajorAdapter(majorList);
        recyclerView.setAdapter(adapter);

        fetchMajors();


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.Home) {
                startActivity(new Intent(Headstart.this, HomePage.class));
            } else if (id == R.id.courses_button) {
                startActivity(new Intent(Headstart.this, Headstart.class));
            } else if (id == R.id.profile_button) {
                startActivity(new Intent(Headstart.this, Profile.class));
            }
            return true;
        });
    }

    private void fetchMajors() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/majors";

        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            Major major = new Major(
                                    object.getLong("majorID"), // Change this to match the JSON key for the major ID from your backend
                                    object.getString("majorName")); // This seems correct but confirm the key name with your backend
                            majorList.add(major);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }


    public static class Major {
        private final Long majorId;
        private final String majorName;

        public Major(Long majorId, String majorName) {
            this.majorId = majorId;
            this.majorName = majorName;
        }

        public Long getMajorId() {
            return majorId;
        }

        public String getMajorName() {
            return majorName;
        }
    }

    private static class MajorAdapter extends RecyclerView.Adapter<MajorAdapter.ViewHolder> {
        private final List<Major> majors;

        public MajorAdapter(List<Major> majors) {
            this.majors = majors;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.major_item, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Major major = majors.get(position);
            holder.majorId.setText("ID: " + major.getMajorId());
            holder.majorName.setText("Major: " + major.getMajorName());
        }

        @Override
        public int getItemCount() {
            return majors.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView majorId;
            TextView majorName;

            public ViewHolder(View view) {
                super(view);
                majorId = view.findViewById(R.id.majorIdTextView);
                majorName = view.findViewById(R.id.majorNameTextView);

                view.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Major major = majors.get(position);
                        Intent intent = new Intent(view.getContext(), Headstart_courses.class);
                        intent.putExtra("majorID", major.getMajorId());
                        view.getContext().startActivity(intent);
                    }
                });
            }
        }

    }
}