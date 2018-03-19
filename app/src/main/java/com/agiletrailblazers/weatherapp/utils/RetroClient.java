package com.agiletrailblazers.weatherapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sgundavarapu on 17-03-2018.
 */

public class RetroClient {

    private static RetroClient retroClient;

    private Retrofit retrofit;

    public RetroClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetroClient getInstance() {
        if (retroClient == null) {
            return new RetroClient();
        }
        return retroClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
