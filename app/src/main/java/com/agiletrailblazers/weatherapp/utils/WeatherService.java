package com.agiletrailblazers.weatherapp.utils;

import com.agiletrailblazers.weatherapp.core.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sgundavarapu on 17-03-2018.
 */

public interface WeatherService {

    @GET("weather")
    Call<WeatherData> getWeatherDetails(@Query("zip") String zip, @Query("APPID") String appId);

}
