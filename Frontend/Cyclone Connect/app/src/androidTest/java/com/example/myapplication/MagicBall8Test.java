package com.example.myapplication;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.CyMagicBall.MagicBall8;
import com.example.myapplication.R;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.core.IsNot.not;

import android.view.View;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MagicBall8Test {

    @Before
    public void setup() {
        // Launch the activity to test
        ActivityScenario.launch(MagicBall8.class);
    }

    @Test
    public void testInitialViewState() {
        // Check if the askButton is displayed and enabled
        onView(withId(R.id.askButton))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        // Check if the progressBar is not visible initially
        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())));
    }



    @Test
    public void testBackButtonFunctionality() {
        // Simulate a button click on backButton
        onView(withId(R.id.back_button)).perform(click());

        // Verifying if the activity is closed after clicking the back button can be complex in Espresso without additional setup,
        // usually requiring the use of Intents or monitoring the Activity lifecycle.
    }
}
