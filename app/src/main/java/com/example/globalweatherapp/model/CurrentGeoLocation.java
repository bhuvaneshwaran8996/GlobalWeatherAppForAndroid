package com.example.globalweatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class CurrentGeoLocation extends RealmObject implements Serializable {

    @SerializedName("time_12")
    public String time_12;
    @SerializedName("city_name")
    public String city_name;
    @SerializedName("timezone")
    public String timezone;

    public String getTime_12() {
        return time_12;
    }

    public void setTime_12(String time_12) {
        this.time_12 = time_12;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public CurrentGeoLocation(){

    }
    public CurrentGeoLocation(String time_12, String city_name, String timezone) {
        this.time_12 = time_12;
        this.city_name = city_name;
        this.timezone = timezone;
    }
}
