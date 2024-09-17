package com.example.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.BMI.BmiActivity;
import com.example.myapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class BmiActivityTest {

    @Rule
    public ActivityTestRule<BmiActivity> mActivityTestRule = new ActivityTestRule<>(BmiActivity.class, true, true);

    @Test
    public void bmiActivityDisplaysValues() {
        // Check that BMI value is displayed and is not empty
        onView(withId(R.id.bmi_value))
                .check(matches(not(withText(""))));

        // Check that BMI category is displayed and is not empty
        onView(withId(R.id.bmi_category))
                .check(matches(not(withText(""))));

        // Optionally, check that tips related to BMI are displayed and not empty
        onView(withId(R.id.bmi_tips))
                .check(matches(not(withText(""))));
    }
}
