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
///**
// * System tests for {@link UserLoginActivity}, {@link Profile}, and {@link Weather_Page} to ensure they function correctly.
// */
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class AbhaySystemTest {
//
//    // Rule for UserLoginActivity
//    @Rule
//    public ActivityScenarioRule<UserLoginActivity> activityRule = new ActivityScenarioRule<>(UserLoginActivity.class);
//
//    /**
//     * Test to verify that successful login transitions the user to the HomePage, navigates to Profile, and checks the profile photo.
//     */
//    @Test
//    public void loginAndCheckProfilePhoto() {
//        // Log in
//        onView(withId(R.id.email_edit_text)).perform(typeText("abhay14@iastate.edu"), closeSoftKeyboard());
//        onView(withId(R.id.password_edit_text)).perform(typeText("Abhay@123"), closeSoftKeyboard());
//        onView(withId(R.id.loginbutton)).perform(click());
//
//        // Ensure login is successful and HomePage is displayed
//        onView(withId(R.id.profile_button)).check(matches(isDisplayed()));
//
//        // Navigate to the profile activity
//        onView(withId(R.id.profile_button)).perform(click());
//
//        // Wait for potential network operations to complete
//        try {
//            Thread.sleep(3000); // Replace with IdlingResource for production tests
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Check if the imageView displays some drawable
//        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
//    }
//
//
//}
