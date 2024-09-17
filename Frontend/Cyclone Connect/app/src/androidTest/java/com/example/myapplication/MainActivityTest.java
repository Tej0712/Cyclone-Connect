package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import com.example.myapplication.Login.UserLoginActivity;
import com.example.myapplication.SignUp.WhoActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // Use IntentsTestRule to initialize Espresso-Intents before each test
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testLoginButton() {
        // Perform a click on the login button
        onView(withId(R.id.login_button)).perform(click());

        // Assert that an Intent was started to open the UserLoginActivity
        intended(hasComponent(UserLoginActivity.class.getName()));
    }

    @Test
    public void testSignupButton() {
        // Perform a click on the signup button
        onView(withId(R.id.signup_button)).perform(click());

        // Assert that an Intent was started to open the WhoActivity
        intended(hasComponent(WhoActivity.class.getName()));
    }
}
