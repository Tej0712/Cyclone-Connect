package com.example.myapplication;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.R;
import com.example.myapplication.SignUp.WhoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WhoActivitySystemTest {

    @Rule
    public ActivityScenarioRule<WhoActivity> activityScenarioRule = new ActivityScenarioRule<>(WhoActivity.class);

    @Test
    public void testStudentButton() {
        // Click on the student button
        onView(withId(R.id.student_button)).perform(ViewActions.click());

        // Check if the CreateAccountActivity is displayed
        onView(withId(R.id.view)).check(matches(isDisplayed()));
    }

    @Test
    public void testProfessorButton() {
        // Click on the professor button
        onView(withId(R.id.faculty_button)).perform(ViewActions.click());

        // Check if the Create_Faculty_Account activity is displayed
        onView(withId(R.id.view)).check(matches(isDisplayed()));
    }

    @Test
    public void testAdminButton() {
        // Click on the admin button
        onView(withId(R.id.Admin)).perform(ViewActions.click());

        // Check if the CreateAccountActivity is displayed
        onView(withId(R.id.view)).check(matches(isDisplayed()));
    }

    @Test
    public void testAdvisorButton() {
        // Click on the advisor button
        onView(withId(R.id.advisorbtn)).perform(ViewActions.click());

        // Check if the CreateAccountActivity is displayed
        onView(withId(R.id.view)).check(matches(isDisplayed()));
    }
}
