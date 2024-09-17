package com.example.myapplication.Calendar;

import static com.example.myapplication.Calendar.CalendarUtils.daysInMonthArray;
import static com.example.myapplication.Calendar.CalendarUtils.monthYearFromDate;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Calendar.CalendarAdapter;
import com.example.myapplication.Calendar.CalendarUtils;
import com.example.myapplication.Calendar.WeekViewActivity;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class calendar_page extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarpage);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
    private void checkForEvents() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String eventDetailsString = sharedPreferences.getString("eventDetails", null);

        if (eventDetailsString != null) {
            try {
                JSONObject eventDetails = new JSONObject(eventDetailsString);
                LocalDate eventDate = LocalDate.parse(eventDetails.getString("date"));

                // Now, you have the event details and can compare the event date
                // with your calendar dates to show the event marker or details.
                // This is a simplified example to log the event title
                if (eventDate.equals(CalendarUtils.selectedDate)) {
                    String title = eventDetails.getString("title");
                    // Display event title or details here. This example uses a log.
                    Log.d("CalendarPage", "Event on selected date: " + title);
                    // Alternatively, show a Toast or update your RecyclerView adapter to display the event.
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}