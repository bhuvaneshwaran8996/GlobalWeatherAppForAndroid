package com.example.globalweatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentWeather implements Serializable {

    @SerializedName("lat")
    public String lat;
    @SerializedName("lon")
    public String lon;
    @SerializedName("lang")
    public String lang;


    public CurrentWeather(String lat, String lon, String lang) {
        this.lat = lat;
        this.lon = lon;
        this.lang = lang;
    }
}
