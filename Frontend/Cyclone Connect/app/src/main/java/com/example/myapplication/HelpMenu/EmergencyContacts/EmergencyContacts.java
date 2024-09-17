package com.example.myapplication.HelpMenu.EmergencyContacts;

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

public class EmergencyContacts extends AppCompatActivity {

    private EmergencyContactAdapter adapter;
    private final List<EmergencyContact> emergencyContactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencycontacts);

        RecyclerView recyclerView = findViewById(R.id.emergencyContactsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmergencyContactAdapter(emergencyContactsList);
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());


        fetchEmergencyContacts();
    }

    private void fetchEmergencyContacts() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/emergencies";

        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            EmergencyContact contact = new EmergencyContact(
                                    object.getLong("id"),
                                    object.getString("contactName"),
                                    object.getString("phoneNumber"),
                                    object.getString("description"));
                            emergencyContactsList.add(contact);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public static class EmergencyContact {
        private final Long id;
        private final String contactName;
        private final String phoneNumber;
        private final String description;

        public EmergencyContact(Long id, String contactName, String phoneNumber, String description) {
            this.id = id;
            this.contactName = contactName;
            this.phoneNumber = phoneNumber;
            this.description = description;
        }

        public Long getId() { return id; }
        public String getContactName() { return contactName; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getDescription() { return description; }
    }

    private static class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {
        private final List<EmergencyContact> emergencyContacts;

        public EmergencyContactAdapter(List<EmergencyContact> emergencyContacts) {
            this.emergencyContacts = emergencyContacts;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_contact_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            EmergencyContact contact = emergencyContacts.get(position);
            holder.bind(contact);
        }

        @Override
        public int getItemCount() {
            return emergencyContacts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView contactNameTextView;
            TextView phoneNumberTextView;
            TextView descriptionTextView;

            public ViewHolder(View view) {
                super(view);
                contactNameTextView = view.findViewById(R.id.contactNameTextView);
                phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
                descriptionTextView = view.findViewById(R.id.descriptionTextView);

                view.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        EmergencyContact contact = emergencyContacts.get(position);
                        phoneNumberTextView.setVisibility(phoneNumberTextView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        descriptionTextView.setVisibility(descriptionTextView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    }
                });
            }

            @SuppressLint("SetTextI18n")
            public void bind(EmergencyContact contact) {
                contactNameTextView.setText(contact.getId() + " - " + contact.getContactName());
                phoneNumberTextView.setText(contact.getPhoneNumber());
                descriptionTextView.setText(contact.getDescription());
                phoneNumberTextView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
            }
        }
    }
}