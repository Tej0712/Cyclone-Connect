package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.myapplication.Weather.Weather_Page;

import org.junit.Rule;

public class HomePageTest {

    @Rule
    public ActivityScenarioRule<HomePage> activityScenarioRule = new ActivityScenarioRule<>(HomePage.class);
}
