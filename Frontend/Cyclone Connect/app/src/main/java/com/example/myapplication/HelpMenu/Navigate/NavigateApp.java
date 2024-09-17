package com.example.myapplication.HelpMenu.Navigate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class NavigateApp extends AppCompatActivity {

    private NavigationAdapter adapter;
    private final List<NavigationItem> navigationItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        RecyclerView recyclerView = findViewById(R.id.navigationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NavigationAdapter(navigationItemList);
        recyclerView.setAdapter(adapter);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());


        fetchNavigations();
    }

    private void fetchNavigations() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/navigations";

        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            NavigationItem item = new NavigationItem(
                                    object.getLong("id"),
                                    object.getString("destination"),
                                    object.getString("description"),
                                    object.optLong("userId"));
                            navigationItemList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public static class NavigationItem {
        private final Long id;
        private final String destination;
        private final String description;
        private final Long userId;

        public NavigationItem(Long id, String destination, String description, Long userId) {
            this.id = id;
            this.destination = destination;
            this.description = description;
            this.userId = userId;
        }

        // Getters
        public Long getId() { return id; }
        public String getDestination() { return description; }
        public String getDescription() { return destination; }
        public Long getUserId() { return userId; }
    }

    public static class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {
        private final List<NavigateApp.NavigationItem> navigationItems;

        public NavigationAdapter(List<NavigateApp.NavigationItem> navigationItems) {
            this.navigationItems = navigationItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NavigateApp.NavigationItem item = navigationItems.get(position);
            // Combine ID and Destination for display
            String displayText = item.getId() + " - " + item.getDestination();
            holder.destinationTextView.setText(displayText);
            holder.descriptionTextView.setText(item.getDescription());
            // Initially hide the description
            holder.descriptionTextView.setVisibility(View.GONE);

            holder.rootView.setOnClickListener(v -> {
                // Toggle visibility of description upon clicking the card
                holder.descriptionTextView.setVisibility(holder.descriptionTextView.isShown() ? View.GONE : View.VISIBLE);
            });
        }

        @Override
        public int getItemCount() {
            return navigationItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView destinationTextView, descriptionTextView;
            View rootView; // Root view for click listener

            public ViewHolder(View view) {
                super(view);
                rootView = view; // Assign root view
                destinationTextView = view.findViewById(R.id.subjectTextView);
                descriptionTextView = view.findViewById(R.id.descriptionTextView);
            }
        }
    }
}
