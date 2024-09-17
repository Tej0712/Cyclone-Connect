package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.myapplication.R;
import com.example.myapplication.admin.AdminPage;
import com.example.myapplication.admin.Help.EmergencyContacts.admin_EmergencyContactUpdatesActivity;
import com.example.myapplication.admin.Help.Navigation.admin_NavigationApp;
import com.example.myapplication.admin.Help.admin_bugs_chat;
import com.example.myapplication.admin.Users.admin_fetchusers;
import com.example.myapplication.admin.HeadStart.admin_headstart;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AdminPageTest {

    @Rule
    public IntentsTestRule<AdminPage> intentsTestRule = new IntentsTestRule<>(AdminPage.class);

    @Test
    public void testNavigationToFetchUsers() {
        onView(withId(R.id.btnFetchUsers)).perform(click());
        intended(hasComponent(admin_fetchusers.class.getName()));
    }

    @Test
    public void testNavigationToBugsChat() {
        onView(withId(R.id.btnBugsChat)).perform(click());
        intended(hasComponent(admin_bugs_chat.class.getName()));
    }

    @Test
    public void testNavigationToEmergencyUpdates() {
        onView(withId(R.id.EmerUpdates)).perform(click());
        intended(hasComponent(admin_EmergencyContactUpdatesActivity.class.getName()));
    }

    @Test
    public void testNavigationToNavigationApp() {
        onView(withId(R.id.NavUpdates)).perform(click());
        intended(hasComponent(admin_NavigationApp.class.getName()));
    }

    @Test
    public void testNavigationToHeadStart() {
        onView(withId(R.id.HeadStart)).perform(click());
        intended(hasComponent(admin_headstart.class.getName()));
    }

    // Add more tests for other buttons like Calender, Notes, Health, and User edit based on their functionality
    // For instance, if they only show a Toast, you could verify the Toast text, but this requires a third-party library or custom implementation as Espresso does not support Toast verification directly.
}
