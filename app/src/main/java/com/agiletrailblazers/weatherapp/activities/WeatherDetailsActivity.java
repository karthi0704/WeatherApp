package com.agiletrailblazers.weatherapp.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agiletrailblazers.weatherapp.R;
import com.agiletrailblazers.weatherapp.core.WeatherData;
import com.agiletrailblazers.weatherapp.utils.AppConstants;
import com.agiletrailblazers.weatherapp.utils.RetroClient;
import com.agiletrailblazers.weatherapp.utils.WeatherService;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_POSTAL_CODE = "POSTAL_CODE";
    private TextView weatherMainTextView;
    private TextView weatherDescTextView;
    private TextView tempTextView;
    private TextView windspeedTextView;
    private TextView cloudinessTextView;
    private TextView pressureTextView;
    private TextView humidityTextView;
    private TextView sunriseTextView;
    private TextView locDetailsTextview;
    private View weatherContainer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        weatherContainer = findViewById(R.id.weather_container);
        weatherMainTextView = (TextView) findViewById(R.id.weather_main_textview);
        weatherDescTextView = (TextView) findViewById(R.id.weather_desc_textview);
        tempTextView = (TextView) findViewById(R.id.temp_textView);
        windspeedTextView = (TextView) findViewById(R.id.windspeed_textview);
        cloudinessTextView = (TextView) findViewById(R.id.cloudiness_textview);
        pressureTextView = (TextView) findViewById(R.id.pressure_textview);
        humidityTextView = (TextView) findViewById(R.id.humidity_textview);
        sunriseTextView = (TextView) findViewById(R.id.sunrise_textview);
        locDetailsTextview = (TextView) findViewById(R.id.loc_details_textview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String postalCode = getIntent().getStringExtra(EXTRA_POSTAL_CODE);
        WeatherService weatherService = RetroClient.getInstance().getRetrofit().create(WeatherService.class);
        Call<WeatherData> weatherDetails = weatherService.getWeatherDetails(postalCode + ",us", AppConstants.API_KEY);
        weatherDetails.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    WeatherData weatherData = response.body();
                    weatherContainer.setVisibility(View.VISIBLE);
                    weatherMainTextView.setText(weatherData.getWeather().get(0).getMain());
                    weatherDescTextView.setText(weatherData.getWeather().get(0).getDescription());

                    float temp = (float) (9 / 5 * (weatherData.getMain().getTemp() - 273) + 32);
                    tempTextView.setText(String.valueOf(temp));
                    windspeedTextView.setText(String.valueOf(weatherData.getWind().getSpeed()));
                    cloudinessTextView.setText(String.valueOf(weatherData.getClouds().getAll()));
                    pressureTextView.setText(String.format("%d hpa", weatherData.getMain().getPressure()));
                    humidityTextView.setText(weatherData.getMain().getHumidity() + "%");

                    Location location = new Location(weatherData.getCoord().getLat(), weatherData.getCoord().getLon());
                    SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/New_York");

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(weatherData.getSys().getSunrise());
                    sunriseTextView.setText(calculator.getOfficialSunriseForDate(calendar));
                    locDetailsTextview.setText(getString(R.string.loc_deatils, weatherData.getName()));
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
