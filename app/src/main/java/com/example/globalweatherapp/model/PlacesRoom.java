package com.example.globalweatherapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import dagger.Component;

@Entity(tableName = "City")
public class PlacesRoom implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lon")
    public String lon;

    public PlacesRoom(String name, String place, String lat, String lon) {

        this.name = name;
        this.place = place;
        this.lat = lat;
        this.lon = lon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name "+name ;
    }
}
