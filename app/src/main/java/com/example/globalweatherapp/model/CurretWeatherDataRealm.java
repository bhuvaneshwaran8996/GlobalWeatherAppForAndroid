package com.example.globalweatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class CurretWeatherDataRealm extends RealmObject implements Serializable {


    public String CityName;
    public String WeatherSummary;
    public String WeatherDisplay;
    public String C_or_F;
    public String Day;
    public String Month;
    public String FeelsLike;
    public String ApparentTempertaure;


    public CurretWeatherDataRealm(){}

    public CurretWeatherDataRealm(String cityName, String weatherSummary, String weatherDisplay, String c_or_F, String day, String month, String feelsLike, String ApparentTempertaure) {
        ApparentTempertaure = ApparentTempertaure;
        CityName = cityName;
        WeatherSummary = weatherSummary;
        WeatherDisplay = weatherDisplay;
        C_or_F = c_or_F;
        Day = day;
        Month = month;
        FeelsLike = feelsLike;
    }

    public void setApparentTempertaure(String apparentTempertaure){
        this.ApparentTempertaure = apparentTempertaure;
    }
    public String getApparentTempertaure(){
        return this.ApparentTempertaure;
    }
    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getWeatherSummary() {
        return WeatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        WeatherSummary = weatherSummary;
    }

    public String getWeatherDisplay() {
        return WeatherDisplay;
    }

    public void setWeatherDisplay(String weatherDisplay) {
        WeatherDisplay = weatherDisplay;
    }

    public String getC_or_F() {
        return C_or_F;
    }

    public void setC_or_F(String c_or_F) {
        C_or_F = c_or_F;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getFeelsLike() {
        return FeelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        FeelsLike = feelsLike;
    }
}
