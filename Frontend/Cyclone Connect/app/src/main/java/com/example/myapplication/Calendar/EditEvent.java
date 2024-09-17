package com.example.myapplication.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Calendar.WeekViewActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

public class EditEvent extends AppCompatActivity {

    private EditText eventNameET, eventDescriptionET;
    private TextView eventDateTV, eventStartTimeTV, eventEndTimeTV;
    private Button selectDateButton, selectStartTimeButton, selectEndTimeButton, saveEventButton;
    private LocalDate selectedDate = LocalDate.now(); // Initialize with the current date to avoid null.
    private LocalTime startTime, endTime;
    private String formatDate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        eventNameET = findViewById(R.id.eventNameET);
        eventDescriptionET = findViewById(R.id.eventDescriptionET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventStartTimeTV = findViewById(R.id.eventStartTimeTV);
        eventEndTimeTV = findViewById(R.id.eventEndTimeTV);
        selectDateButton = findViewById(R.id.selectDateButton);
        selectStartTimeButton = findViewById(R.id.selectStartTimeButton);
        selectEndTimeButton = findViewById(R.id.selectEndTimeButton);
        saveEventButton = findViewById(R.id.saveEventButton);

        selectDateButton.setOnClickListener(v -> selectDate());
        selectStartTimeButton.setOnClickListener(v -> selectTime(true));
        selectEndTimeButton.setOnClickListener(v -> selectTime(false));

        DateTimeFormatter pattern = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        formatDate = selectedDate.format(pattern);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1); // Make sure the key matches ("userId" not "UserID")


        saveEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("Event Name", eventNameET.getText().toString());
                Log.i("Event Description", eventDescriptionET.getText().toString());
                Log.i("Date",selectedDate.toString());
                Log.i("Start time", startTime.toString());
                Log.i("End time", endTime.toString());



                String sendEventToServerLink = "http://coms-309-046.class.las.iastate.edu:8080/api/user/calendar/"+ userId;
                Map<String, Object> params = new HashMap<>();
                String eventName = eventNameET.getText().toString();
                String description = eventDescriptionET.getText().toString();
                String start = startTime.toString();
                String end = endTime.toString();
                String date = selectedDate.toString();

                params.put("title", eventName);
                params.put("description", description);
                params.put("date", date);
                params.put("startTime", start);
                params.put("endTime", end);

                JSONObject jsonObject = new JSONObject(params);
                Log.i("Parameters Passed", String.valueOf(jsonObject));
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sendEventToServerLink, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response from Save", response.toString());
                        Toast.makeText(EditEvent.this, "Event created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditEvent.this, WeekViewActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }

                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }
        });

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
}