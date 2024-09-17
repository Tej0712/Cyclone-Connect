//package com.example.myapplication;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.rule.ActivityTestRule;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.StringRequest;
//import com.example.myapplication.admin.Users.admin_fetchusers;
//import com.example.myapplication.helper.VolleySingleton;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(AndroidJUnit4.class)
//public class AdminFetchUsersTest {
//
//    @Rule
//    public ActivityTestRule<admin_fetchusers> activityRule = new ActivityTestRule<>(admin_fetchusers.class, true, true);
//
//    @Mock
//    private VolleySingleton mockVolleySingleton;
//    @Mock
//    private RequestQueue mockRequestQueue;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        // Ensure Volley uses our mocked request queue
//        when(mockVolleySingleton.getRequestQueue()).thenReturn(mockRequestQueue);
//        // Replace the singleton instance with the mock
//        VolleySingleton.setInstance(mockVolleySingleton);
//    }
//
//    @Test
//    public void fetchUsersTest() {
//        // Trigger the button click
//        onView(withId(R.id.btnFetchUsers)).perform(click());
//
//        // Verify that a request was added to the queue
//        verify(mockRequestQueue).add(any(StringRequest.class));
//
//        // Optionally, assume the response was immediately processed and check UI changes
//        // For this to work in a real test, you'd need to simulate a response
//        onView(withId(R.id.tvUsers)).check(matches(withText("abhay14@iastate.edu\n")));
//    }
//}
