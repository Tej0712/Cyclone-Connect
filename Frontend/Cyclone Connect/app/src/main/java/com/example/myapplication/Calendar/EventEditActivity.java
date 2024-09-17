package com.example.myapplication.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

public class EventEditActivity extends AppCompatActivity {
    private static final String TAG = "EventEditActivity";

    private EditText eventNameET, eventDescriptionET;
    private TextView eventDateTV, eventStartTimeTV, eventEndTimeTV;
    private Button selectDateButton, selectStartTimeButton, selectEndTimeButton, saveEventButton;
    private LocalDate selectedDate = LocalDate.now(); // Initialize with the current date to avoid null.
    private LocalTime startTime, endTime;
    private String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        Intent intent = getIntent();
        String selectedDateStr = intent.getStringExtra("SELECTED_DATE");
        if (selectedDateStr != null) {
            selectedDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ISO_DATE);
        }

        selectDateButton.setOnClickListener(v -> selectDate());
        selectStartTimeButton.setOnClickListener(v -> selectTime(true));
        selectEndTimeButton.setOnClickListener(v -> selectTime(false));
        saveEventButton = findViewById(R.id.saveEventButton);

        DateTimeFormatter pattern = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        formatDate = selectedDate.format(pattern);
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDescriptionET = findViewById(R.id.eventDescriptionET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventStartTimeTV = findViewById(R.id.eventStartTimeTV);
        eventEndTimeTV = findViewById(R.id.eventEndTimeTV);
        selectDateButton = findViewById(R.id.selectDateButton);
        selectStartTimeButton = findViewById(R.id.selectStartTimeButton);
        selectEndTimeButton = findViewById(R.id.selectEndTimeButton);
        saveEventButton = findViewById(R.id.saveEventButton);
    }

    private void selectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            eventDateTV.setText("Date: " + selectedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        }, selectedDate.getYear(), selectedDate.getMonthValue() - 1, selectedDate.getDayOfMonth());
        datePickerDialog.show();
    }

    private void selectTime(boolean isStartTime) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            LocalTime time = LocalTime.of(hourOfDay, minute);
            if (isStartTime) {
                startTime = time;
                eventStartTimeTV.setText("Start Time: " + startTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            } else {
                endTime = time;
                eventEndTimeTV.setText("End Time: " + endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        }, 12, 0, true);
        timePickerDialog.show();
    }

    public void saveEventAction(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1); // Default to -1 if not found
        if (userId == -1) {
            Toast.makeText(this, "User not identified. Please log in.", Toast.LENGTH_LONG).show();
            return;
        }

        String sendEventToServerLink = "http://coms-309-046.class.las.iastate.edu:8080/api/user/calendar/user/" + userId + "/events";

        JSONObject eventDetails = new JSONObject();
        try {
            eventDetails.put("title", eventNameET.getText().toString());
            Log.d(TAG, "Event Name: " + eventNameET.getText().toString());
            eventDetails.put("description", eventDescriptionET.getText().toString());
            Log.d(TAG, "Event Description: " + eventDescriptionET.getText().toString());
            eventDetails.put("date", selectedDate.toString());
            Log.d(TAG, "Event Date: " + selectedDate.toString());
            eventDetails.put("startTime", startTime != null ? startTime.format(DateTimeFormatter.ISO_TIME) : null);
            Log.d(TAG, "Event Start Time: " + (startTime != null ? startTime.toString() : "null"));
            eventDetails.put("endTime", endTime != null ? endTime.format(DateTimeFormatter.ISO_TIME) : null);
            Log.d(TAG, "Event End Time: " + (endTime != null ? endTime.toString() : "null"));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating event JSON.", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sendEventToServerLink, eventDetails,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            long eventId = response.getLong("eventId");
                            Log.d(TAG, "Event created successfully. Message: " + message + ", Event ID: " + eventId);
                            Toast.makeText(EventEditActivity.this, "Event saved to server", Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EventEditActivity.this, "Error parsing server response.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error saving event to server: " + error.getMessage());
                        Toast.makeText(EventEditActivity.this, "Error saving event to server.", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
