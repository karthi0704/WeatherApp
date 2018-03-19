package com.agiletrailblazers.weatherapp;

import android.support.test.runner.AndroidJUnit4;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testSunrise() throws Exception {

        long sunrise=1521468737;
        Location location = new Location(-122.08, 37.39);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/New_York");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sunrise);

        Assert.assertEquals(calculator.getOfficialSunriseForDate(calendar),"01:17");
    }

}
