package com.example.globalweatherapp.model;

import androidx.databinding.adapters.Converters;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.globalweatherapp.converters.DataConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmObject;


public class HourlyRoom  implements Serializable   //retrofit model
{

    List<HourlyRoom.Data> data;

    public HourlyRoom() {
        data = new ArrayList<>();
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private String icon;

    private String sumary;

    public List<HourlyRoom.Data> getData() {
        return data;
    }

    public void setData(List<HourlyRoom.Data> data) {
        this.data = data;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + ", icon = " + icon + ", sumary = " + sumary + "]";
    }


    public class Data implements Serializable {

        public Data() {

        }

        private String summary;

        public Data(String summary, String visibility, String icon, String temperature, String humidity, String windspeed, String time, String pressure, String ozone, String cloudcover) {
            this.summary = summary;
            this.visibility = visibility;
            this.icon = icon;
            this.temperature = temperature;
            this.humidity = humidity;
            this.windspeed = windspeed;
            this.time = time;
            this.pressure = pressure;
            this.ozone = ozone;
            this.cloudcover = cloudcover;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
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

        public String getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(String windspeed) {
            this.windspeed = windspeed;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public String getCloudcover() {
            return cloudcover;
        }

        public void setCloudcover(String cloudcover) {
            this.cloudcover = cloudcover;
        }

        @Override
        public String toString() {
            return "ClassPojo [summary = " + summary + ", visibility = " + visibility + ", icon = " + icon + ", temperature = " + temperature + ", humidity = " + humidity + ", windspeed = " + windspeed + ", time = " + time + ", pressure = " + pressure + ", ozone = " + ozone + ", cloudcover = " + cloudcover + "]";
        }

    }

}
