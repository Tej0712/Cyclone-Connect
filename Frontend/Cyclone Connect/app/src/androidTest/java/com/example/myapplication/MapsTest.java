package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.myapplication.Maps.Maps;

import org.junit.Rule;

public class MapsTest {

    @Rule
    public ActivityScenarioRule<Maps> activityScenarioRule = new ActivityScenarioRule<>(Maps.class);
}
