package com.example.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.myapplication.Weather.Weather_Page;
import com.example.myapplication.helper.VolleySingleton;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

import java.lang.reflect.Field;

@RunWith(AndroidJUnit4.class)
public class WeatherPageTest {

    @Rule
    public ActivityScenarioRule<Weather_Page> activityScenarioRule = new ActivityScenarioRule<>(Weather_Page.class);

    @Mock
    private VolleySingleton mockVolleySingleton;
    @Mock
    private RequestQueue mockRequestQueue;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Setting up the mocked instance for VolleySingleton
        Field instanceField = VolleySingleton.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockVolleySingleton);
        Mockito.when(mockVolleySingleton.getRequestQueue()).thenReturn(mockRequestQueue);
    }

    @Test
    public void weatherDataLoadsCorrectly() {
        // Mock the API response
        String fakeResponse = "{\"city\":{\"name\":\"Ames\"},\"list\":[{\"dt_txt\":\"2021-06-01 09:00:00\",\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01d\"}],\"main\":{\"temp\":25.5,\"feels_like\":26.0,\"humidity\":60},\"wind\":{\"speed\":5.0}}]}";
        doAnswer(invocation -> {
            Response.Listener<String> successListener = invocation.getArgument(1);
            successListener.onResponse(fakeResponse);
            return null;
        }).when(mockRequestQueue).add(any(StringRequest.class));

        // Check that all weather information text views are not empty
        onView(withId(R.id.id_city)).check(matches(not(withText(""))));
        onView(withId(R.id.id_degree)).check(matches(not(withText(""))));
        onView(withId(R.id.id_main)).check(matches(not(withText(""))));
        onView(withId(R.id.id_humidity)).check(matches(not(withText(""))));
        onView(withId(R.id.id_realfeel)).check(matches(not(withText(""))));
        onView(withId(R.id.id_wind)).check(matches(not(withText(""))));
    }
}
