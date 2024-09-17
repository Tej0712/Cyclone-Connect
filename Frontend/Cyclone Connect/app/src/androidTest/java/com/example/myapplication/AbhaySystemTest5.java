//package com.example.myapplication;
//
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import com.example.myapplication.Login.UserLoginActivity;
//import com.example.myapplication.admin.AdminPage;
//import com.example.myapplication.admin.Help.EmergencyContacts.admin_Emergency_addcontact;
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
//
///**
// * System tests for adding navigation item as an admin.
// */
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class AbhaySystemTest5 {
//
//    @Rule
//    public ActivityScenarioRule<UserLoginActivity> activityRule = new ActivityScenarioRule<>(UserLoginActivity.class);
//
//    @Test
//    public void addEmergencyContactAsAdmin() {
//        // Step 1: Login as Admin
//        onView(withId(R.id.email_edit_text)).perform(typeText("admin@iastate.edu"), closeSoftKeyboard());
//        onView(withId(R.id.password_edit_text)).perform(typeText("adminPassword123"), closeSoftKeyboard());
//        onView(withId(R.id.loginbutton)).perform(click());
//
//        // Wait for login to process and check if Home page is displayed
//        try {
//            Thread.sleep(2000); // Consider using Idling Resources instead of sleep
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        onView(withId(R.id.profile_button)).check(matches(isDisplayed()));
//
//        // Step 2: Navigate to the profile page
//        onView(withId(R.id.profile_button)).perform(click());
//
//        // Step 3: Scroll to make the adminbutton visible
//        onView(withId(R.id.adminbutton)).perform(ViewActions.scrollTo());
//
//        // Step 4: Navigate to the Admin page
//        onView(withId(R.id.adminbutton)).perform(click());
//
//        // Step 5: Navigate to Emergency Contact Updates page
//        onView(withId(R.id.NavUpdates)).perform(click());
//
//        // Step 6: Navigate to Add Emergency Contact page
//        onView(withId(R.id.Add)).perform(click());
//
//        // Step 3: Enter the details for a new navigation item
//        onView(withId(R.id.etDestination)).perform(typeText("New Destination"), closeSoftKeyboard());
//        onView(withId(R.id.etDescription)).perform(typeText("This is a description for New Destination"), closeSoftKeyboard());
//
//        // Step 4: Submit the navigation item
//        onView(withId(R.id.btnSubmitNavigationItem)).perform(click());
//    }
//
//}
