package com.example.myapplication.Weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Weather_Page extends AppCompatActivity {

    private TextView textViewCityName, textViewTemperature, textViewWeatherInfo, textViewHumidity, textViewWind, textViewFeelsLike, textViewTime;
    private ImageView imageViewWeather;
    private final String city = "Ames";

    private final TextView[] forecastDays = new TextView[5];
    private final TextView[] forecastTemps = new TextView[5];
    private final ImageView[] forecastIcons = new ImageView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherpage);

        // Initialize TextViews and ImageView
        textViewCityName = findViewById(R.id.id_city);
        textViewTemperature = findViewById(R.id.id_degree);
        textViewWeatherInfo = findViewById(R.id.id_main);
        textViewHumidity = findViewById(R.id.id_humidity);
        textViewWind = findViewById(R.id.id_wind);
        textViewFeelsLike = findViewById(R.id.id_realfeel);
        textViewTime = findViewById(R.id.id_time);
        imageViewWeather = findViewById(R.id.id_weatherImage);

        // Initialize forecast TextViews and ImageViews
        forecastDays[0] = findViewById(R.id.id_forecastDay1);
        forecastDays[1] = findViewById(R.id.id_forecastDay2);
        forecastDays[2] = findViewById(R.id.id_forecastDay3);
        forecastDays[3] = findViewById(R.id.id_forecastDay4);
        forecastDays[4] = findViewById(R.id.id_forecastDay5);

        forecastTemps[0] = findViewById(R.id.id_forecastTemp1);
        forecastTemps[1] = findViewById(R.id.id_forecastTemp2);
        forecastTemps[2] = findViewById(R.id.id_forecastTemp3);
        forecastTemps[3] = findViewById(R.id.id_forecastTemp4);
        forecastTemps[4] = findViewById(R.id.id_forecastTemp5);

        forecastIcons[0] = findViewById(R.id.id_forecastIcon1);
        forecastIcons[1] = findViewById(R.id.id_forecastIcon2);
        forecastIcons[2] = findViewById(R.id.id_forecastIcon3);
        forecastIcons[3] = findViewById(R.id.id_forecastIcon4);
        forecastIcons[4] = findViewById(R.id.id_forecastIcon5);

        String lat = "42.03";
        String lon = "-93.62";
        WeatherByLatLon(lat, lon);

        ImageButton backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This will close the current activity
            }
        });
    }

    private void WeatherByLatLon(String lat, String lon) {
        //openweather api
        String API_KEY = "40dbe88e60f8f27562b2f1210084a6a7";
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);

                        // Current weather data
                        JSONArray list = json.getJSONArray("list");
                        JSONObject currentWeather = list.getJSONObject(0);
                        JSONArray currentWeatherArray = currentWeather.getJSONArray("weather");
                        JSONObject currentWeatherObject = currentWeatherArray.getJSONObject(0);

                        String description = currentWeatherObject.getString("description");
                        String icons = currentWeatherObject.getString("icon");

                        // Get today's date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
                        String currentDate = dateFormat.format(new Date());

                        JSONObject Main = currentWeather.getJSONObject("main");
                        double temperature = Main.getDouble("temp");
                        String Temp = Math.round(temperature) + "°C";
                        double Humidity = Main.getDouble("humidity");
                        String hum = Math.round(Humidity) + "%";
                        double FeelsLike = Main.getDouble("feels_like");
                        String feelsValue = Math.round(FeelsLike) + "°";

                        JSONObject Wind = currentWeather.getJSONObject("wind");
                        String windValue = Wind.getString("speed") + " " + "km/h";

                        JSONObject CityObject = json.getJSONObject("city");
                        String City = CityObject.getString("name");

                        setDataText(textViewCityName, City);
                        setDataText(textViewTemperature, Temp);
                        setDataText(textViewWeatherInfo, description);
                        setDataImage(imageViewWeather, icons);
                        setDataText(textViewTime, currentDate);
                        setDataText(textViewHumidity, hum);
                        setDataText(textViewFeelsLike, feelsValue);
                        setDataText(textViewWind, windValue);

                        // Forecast data
                        Calendar calendar = Calendar.getInstance();
                        for (int i = 0; i < forecastDays.length; i++) {
                            String forecastDate = dateFormat.format(calendar.getTime());
                            int index = i * 8;
                            if (index < list.length()) {
                                JSONObject forecastWeather = list.getJSONObject(index);
                                JSONObject forecastMain = forecastWeather.getJSONObject("main");
                                double forecastTemperature = forecastMain.getDouble("temp");
                                String forecastTemp = Math.round(forecastTemperature) + "°";
                                JSONArray forecastWeatherArray = forecastWeather.getJSONArray("weather");
                                JSONObject forecastWeatherObject = forecastWeatherArray.getJSONObject(0);
                                String forecastIcon = forecastWeatherObject.getString("icon");

                                setDataText(forecastDays[i], forecastDate);
                                setDataText(forecastTemps[i], forecastTemp);
                                setDataImage(forecastIcons[i], forecastIcon);
                            }
                            calendar.add(Calendar.DATE, 1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> showErrorToast());

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setDataText(final TextView text, final String value) {
        runOnUiThread(() -> text.setText(value));
    }

    private void setDataImage(final ImageView imageView, final String value) {
        runOnUiThread(() -> {
            switch (value) {
                case "01d":
                    imageView.setImageResource(R.drawable.w01d);
                    break;
                case "01n":
                    imageView.setImageResource(R.drawable.w01d);
                    break;
                case "02d":
                    imageView.setImageResource(R.drawable.w02d);
                    break;
                case "02n":
                    imageView.setImageResource(R.drawable.w02d);
                    break;
                case "03d":
                    imageView.setImageResource(R.drawable.w03d);
                    break;
                case "03n":
                    imageView.setImageResource(R.drawable.w03d);
                    break;
                case "04d":
                    imageView.setImageResource(R.drawable.w04d);
                    break;
                case "04n":
                    imageView.setImageResource(R.drawable.w04d);
                    break;
                case "09d":
                    imageView.setImageResource(R.drawable.w09d);
                    break;
                case "09n":
                    imageView.setImageResource(R.drawable.w09d);
                    break;
                case "10d":
                    imageView.setImageResource(R.drawable.w10d);
                    break;
                case "10n":
                    imageView.setImageResource(R.drawable.w10d);
                    break;
                case "11d":
                    imageView.setImageResource(R.drawable.w11d);
                    break;
                case "11n":
                    imageView.setImageResource(R.drawable.w11d);
                    break;
                case "13d":
                    imageView.setImageResource(R.drawable.w13d);
                    break;
                case "13n":
                    imageView.setImageResource(R.drawable.w13d);
                    break;
                default:
                    imageView.setImageResource(R.drawable.w03d);
            }
        });
    }

    public void navigateToHomePage(View v) {
        Intent intent = new Intent(Weather_Page.this, HomePage.class);
        startActivity(intent);
    }

    private void showErrorToast() {
        Toast.makeText(Weather_Page.this, "Failed to load weather data", Toast.LENGTH_SHORT).show();
    }
}