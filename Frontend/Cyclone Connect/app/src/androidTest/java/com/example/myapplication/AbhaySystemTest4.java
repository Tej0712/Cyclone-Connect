//package com.example.myapplication;
//
//import androidx.test.espresso.Espresso;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import com.example.myapplication.Login.UserLoginActivity;
//import com.example.myapplication.profile.Profile;
//import com.example.myapplication.Weather.Weather_Page;
//import com.example.myapplication.R;
//import static org.hamcrest.Matchers.equalToIgnoringCase;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class AbhaySystemTest4 {
//    @Rule
//    public ActivityScenarioRule<UserLoginActivity> activityRule = new ActivityScenarioRule<>(UserLoginActivity.class);
//
//    /**
//     * Test to verify that successful login transitions the user to the HomePage, navigates to the Weather page, and checks weather details.
//     */
//    @Test
//    public void loginAndCheckWeatherDisplaysCorrectly() {
//        // Log in
//        onView(withId(R.id.email_edit_text)).perform(typeText("abhay14@iastate.edu"), closeSoftKeyboard());
//        onView(withId(R.id.password_edit_text)).perform(typeText("Abhay@123"), closeSoftKeyboard());
//        onView(withId(R.id.loginbutton)).perform(click());
//
//        // Ensure login is successful and HomePage is displayed
//        onView(withId(R.id.Weather)).check(matches(isDisplayed()));
//
//        // Navigate to the weather activity
//        onView(withId(R.id.Weather)).perform(click());
//
//        // Check if the city name is displayed and matches a specific text
//        onView(withId(R.id.id_city)).check(matches(isDisplayed()));
//        onView(withId(R.id.id_city)).check(matches(withText(equalToIgnoringCase("ames"))));
//
//        // Check if temperature and weather info are displayed
//        onView(withId(R.id.id_degree)).check(matches(isDisplayed()));
//        onView(withId(R.id.id_main)).check(matches(isDisplayed()));
//
//        // Check if humidity, wind, and feels like data are displayed
//        onView(withId(R.id.id_humidity)).check(matches(isDisplayed()));
//        onView(withId(R.id.id_wind)).check(matches(isDisplayed()));
//        onView(withId(R.id.id_realfeel)).check(matches(isDisplayed()));
//
//        // Check if forecast data is displayed
//        onView(withId(R.id.id_forecastDay1)).check(matches(isDisplayed()));
//        onView(withId(R.id.id_forecastTemp1)).check(matches(isDisplayed()));
//        onView(withId(R.id.id_forecastIcon1)).check(matches(isDisplayed()));
//    }
//}
