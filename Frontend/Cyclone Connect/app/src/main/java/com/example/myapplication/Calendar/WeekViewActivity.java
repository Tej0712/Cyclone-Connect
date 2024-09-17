package com.example.myapplication.Calendar;

import static com.example.myapplication.Calendar.CalendarUtils.daysInWeekArray;
import static com.example.myapplication.Calendar.CalendarUtils.monthYearFromDate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, EventAdapter.OnEventClickListener {
    private static final String TAG = "WeekViewActivity";

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private LocalDate selectedDate;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        selectedDate = LocalDate.now();
        setWeekView();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getLong("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not identified. Please log in.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    public void previousWeekAction(View view) {
        selectedDate = selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        selectedDate = selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        Intent intent = new Intent(this, EventEditActivity.class);
        intent.putExtra("SELECTED_DATE", date.toString());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> weekEvents = new ArrayList<>();

        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/user/calendar/user/" + userId + "/events";
        Log.d(TAG, "Fetching events from URL: " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Events fetched from server: " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject eventObject = response.getJSONObject(i);
                                long id = eventObject.getLong("id");
                                String title = eventObject.getString("title");
                                String description = eventObject.getString("description");
                                String dateString = eventObject.getString("date");
                                String startTimeString = eventObject.getString("startTime");
                                String endTimeString = eventObject.getString("endTime");

                                Log.d(TAG, "Event data: id=" + id + ", title=" + title + ", description=" + description +
                                        ", date=" + dateString + ", startTime=" + startTimeString +
                                        ", endTime=" + endTimeString);

                                LocalDate date = LocalDate.parse(dateString);
                                LocalTime startTime = LocalTime.parse(startTimeString, DateTimeFormatter.ISO_TIME);
                                LocalTime endTime = LocalTime.parse(endTimeString, DateTimeFormatter.ISO_TIME);

                                Event event = new Event(id, date, title, description, startTime, endTime);
                                weekEvents.add(event);
                            } catch (JSONException e) {
                                Log.e(TAG, "Error parsing event object: " + e.getMessage());
                            }
                        }
                        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), weekEvents, WeekViewActivity.this);
                        eventListView.setAdapter(eventAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching events from server: " + error.getMessage());
                        if (error.networkResponse != null) {
                            Log.e(TAG, "Network response status code: " + error.networkResponse.statusCode);
                            Log.e(TAG, "Network response data: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(WeekViewActivity.this, "Error fetching events from server.", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void newEventAction(View view) {
        Intent intent = new Intent(WeekViewActivity.this, EventEditActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEventClick(Event event) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEvent(event);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteEvent(Event event) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/user/calendar/" + userId + "/" + event.getId();

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(WeekViewActivity.this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                        setEventAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WeekViewActivity.this, "Failed to delete event", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error deleting event: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}