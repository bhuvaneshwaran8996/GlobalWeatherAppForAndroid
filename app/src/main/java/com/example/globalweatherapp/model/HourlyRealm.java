package com.example.globalweatherapp.model;

import io.realm.RealmObject;

public class HourlyRealm extends RealmObject {


        private String time;
        private String summary;
        private String icon;
        private String temperature;
        private String humidity;
        private String pressure;
        private String ozone;
        private String windspeed;
        private String visibility;
        private String  cloudcover;


        // Getter Methods

    public HourlyRealm(){

    }

    public HourlyRealm(String time, String summary, String icon, String temperature, String humidity, String pressure, String ozone, String windspeed, String visibility, String cloudcover) {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.ozone = ozone;
        this.windspeed = windspeed;
        this.visibility = visibility;
        this.cloudcover = cloudcover;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getOzone() {
        return ozone;
    }

    public void setOzone(String ozone) {
        this.ozone = ozone;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }
}


