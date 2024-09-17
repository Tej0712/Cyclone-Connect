package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;

import com.example.myapplication.BMI.BMI_Calc;
import com.example.myapplication.BMI.BmiActivity;
import com.example.myapplication.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BMI_CalcTest {

    @Rule
    public IntentsTestRule<BMI_Calc> activityRule = new IntentsTestRule<>(BMI_Calc.class);

    @Test
    public void incrementWeight_ChecksTextChange() {
        // Check initial weight
        onView(withId(R.id.weight_counter_text)).check(matches(withText("50")));

        // Click to increment weight
        onView(withId(R.id.weight_btn_inc)).perform(click());

        // Check if weight text is updated
        onView(withId(R.id.weight_counter_text)).check(matches(withText("51")));
    }

    @Test
    public void incrementAge_ChecksTextChange() {
        // Check initial age
        onView(withId(R.id.age_counter_text)).check(matches(withText("25")));

        // Click to increment age
        onView(withId(R.id.age_btn_inc)).perform(click());

        // Check if age text is updated
        onView(withId(R.id.age_counter_text)).check(matches(withText("26")));
    }

    @Test
    public void ensureCalculateButton_IntentSent() {
        // Perform button click to trigger intent
        onView(withId(R.id.calculate_btn)).perform(click());

        // Verify that an intent was sent to start the BmiActivity
        intended(hasComponent(BmiActivity.class.getName()));

        // Verify that the intent includes an extra with key "bmiVal"
        intended(hasExtraWithKey("bmiVal"));
    }
}
