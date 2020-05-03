package com.example.globalweatherapp.model;


import androidx.room.PrimaryKey;

import io.realm.RealmObject;

public class RealmHourlyData extends RealmObject
{

    @PrimaryKey
    private  int id;

    public RealmHourlyData(){

    }




    private String visibility;

    private String icon;

    private String temperature;

    private String humidity;

    private String windspeed;

    private String time;

    private String pressure;

    private String ozone;

    private String cloudcover;



    public String getVisibility ()
    {
        return visibility;
    }

    public void setVisibility (String visibility)
    {
        this.visibility = visibility;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getTemperature ()
    {
        return temperature;
    }

    public void setTemperature (String temperature)
    {
        this.temperature = temperature;
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

    public String getCloudcover ()
    {
        return cloudcover;
    }

    public void setCloudcover (String cloudcover)
    {
        this.cloudcover = cloudcover;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ visibility = "+visibility+", icon = "+icon+", temperature = "+temperature+", humidity = "+humidity+", windspeed = "+windspeed+", time = "+time+", pressure = "+pressure+", ozone = "+ozone+", cloudcover = "+cloudcover+"]";
    }
}