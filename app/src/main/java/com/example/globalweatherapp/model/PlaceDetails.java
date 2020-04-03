package com.example.globalweatherapp.model;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class PlaceDetails implements Serializable {


    @PrimaryKey()
    @SerializedName("name")
    public String name;

    @SerializedName("place")
    public String place;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;


    public PlaceDetails(String name, String place, String lat, String lon) {
        this.name = name;
        this.place = place;
        this.lat = lat;
        this.lon = lon;
    }
}
