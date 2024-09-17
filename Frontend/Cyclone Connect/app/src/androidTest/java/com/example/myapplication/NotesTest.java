package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.myapplication.Notes.NoteActivity;

import org.junit.Rule;

public class NotesTest {

    @Rule
    public ActivityScenarioRule<NoteActivity> activityScenarioRule = new ActivityScenarioRule<>(NoteActivity.class);
}
