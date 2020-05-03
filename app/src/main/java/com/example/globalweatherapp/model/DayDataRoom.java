package com.example.globalweatherapp.model;

import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class DayDataRoom {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String summary;

    private String uvindex;

    private String dewpoint;

    private String temperaturehigh;

    private String icon;

    private String pressure;

    private String ozone;

    private String moonphase;

    private String sunsetTime;

    private String temperaturelow;

    private String humidity;

    private String windspeed;

    private String time;

    private String sunriseTime;

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public String getUvindex ()
    {
        return uvindex;
    }

    public void setUvindex (String uvindex)
    {
        this.uvindex = uvindex;
    }

    public String getDewpoint ()
    {
        return dewpoint;
    }

    public void setDewpoint (String dewpoint)
    {
        this.dewpoint = dewpoint;
    }

    public String getTemperaturehigh ()
    {
        return temperaturehigh;
    }

    public void setTemperaturehigh (String temperaturehigh)
    {
        this.temperaturehigh = temperaturehigh;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getPressure ()
    {
        return pressure;
    }

    public void setPressure (String pressure)
    {
        this.pressure = pressure;
    }

    public String getOzone ()
    {
        return ozone;
    }

    public void setOzone (String ozone)
    {
        this.ozone = ozone;
    }

    public String getMoonphase ()
    {
        return moonphase;
    }

    public void setMoonphase (String moonphase)
    {
        this.moonphase = moonphase;
    }

    public String getSunsetTime ()
    {
        return sunsetTime;
    }

    public void setSunsetTime (String sunsetTime)
    {
        this.sunsetTime = sunsetTime;
    }

    public String getTemperaturelow ()
    {
        return temperaturelow;
    }

    public void setTemperaturelow (String temperaturelow)
    {
        this.temperaturelow = temperaturelow;
    }

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    public String getWindspeed ()
    {
        return windspeed;
    }

    public void setWindspeed (String windspeed)
    {
        this.windspeed = windspeed;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getSunriseTime ()
    {
        return sunriseTime;
    }

    public void setSunriseTime (String sunriseTime)
    {
        this.sunriseTime = sunriseTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", uvindex = "+uvindex+", dewpoint = "+dewpoint+", temperaturehigh = "+temperaturehigh+", icon = "+icon+", pressure = "+pressure+", ozone = "+ozone+", moonphase = "+moonphase+", sunsetTime = "+sunsetTime+", temperaturelow = "+temperaturelow+", humidity = "+humidity+", windspeed = "+windspeed+", time = "+time+", sunriseTime = "+sunriseTime+"]";
    }

}
