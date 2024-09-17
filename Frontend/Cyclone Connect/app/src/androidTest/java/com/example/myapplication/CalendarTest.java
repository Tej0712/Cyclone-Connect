package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.myapplication.Calendar.WeekViewActivity;
import com.example.myapplication.Notes.NoteActivity;

import org.junit.Rule;

import java.util.Calendar;

public class CalendarTest {

    @Rule
    public ActivityScenarioRule<WeekViewActivity> activityScenarioRule = new ActivityScenarioRule<>(WeekViewActivity.class);
}
