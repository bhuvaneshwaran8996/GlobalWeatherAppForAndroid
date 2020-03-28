package com.example.globalweatherapp.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "City")
public class PlacesRoom {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String place;

    public String lat;

    public String lon;

    public PlacesRoom(int id, String name, String place, String lat, String lon) {
        this.id = id;
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
}
