//package com.example.myapplication;
//
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import com.example.myapplication.SignUp.WhoActivity;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
///**
// * Tests for verifying the signup functionality for all account types.
// */
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class AbhaySystemTest2 {
//
//    @Rule
//    public ActivityScenarioRule<WhoActivity> activityRule = new ActivityScenarioRule<>(WhoActivity.class);
//
//    private void performSignUp(int accountTypeButtonId, String email, String firstName, String lastName, String password, String confirmPassword) {
//        // Navigate to the appropriate signup page based on the account type
//        onView(withId(accountTypeButtonId)).perform(click());
//
//        // Fill out the form
//        onView(withId(R.id.first_name_edit_text)).perform(typeText(firstName), closeSoftKeyboard());
//        onView(withId(R.id.last_name_edit_text)).perform(typeText(lastName), closeSoftKeyboard());
//        onView(withId(R.id.email_edit_text)).perform(typeText(email), closeSoftKeyboard());
//        onView(withId(R.id.password_edit_text)).perform(typeText(password), closeSoftKeyboard());
//        onView(withId(R.id.confirm_password_edit_text)).perform(typeText(confirmPassword), closeSoftKeyboard());
//
//        // Click the create account button
//        onView(withId(R.id.create_account_button)).perform(click());
//    }
//
//    private String generateUniqueEmail(String base, String domain) {
//        long timestamp = System.currentTimeMillis();
//        return base + timestamp + domain;
//    }
//
//    @Test
//    public void testStudentSignUp() {
//        performSignUp(R.id.student_button,
//                generateUniqueEmail("student", "@iastate.edu"),
//                "Test", "Student", "Password123!", "Password123!");
//    }
//
//    @Test
//    public void testProfessorSignUp() {
//        performSignUp(R.id.faculty_button,
//                generateUniqueEmail("professor", "@iastate.edu"),
//                "Test", "Professor", "Password123!", "Password123!");
//    }
//
//    @Test
//    public void testAdminSignUp() {
//        performSignUp(R.id.Admin,
//                generateUniqueEmail("admin", "@iastate.edu"),
//                "Test", "Admin", "Password123!", "Password123!");
//    }
//
//    @Test
//    public void testAdvisorSignUp() {
//        performSignUp(R.id.advisorbtn,
//                generateUniqueEmail("advisor", "@iastate.edu"),
//                "Test", "Advisor", "Password123!", "Password123!");
//    }
//}
