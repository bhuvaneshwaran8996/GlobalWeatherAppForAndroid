package com.example.globalweatherapp.model;

import androidx.databinding.adapters.Converters;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.globalweatherapp.converters.DataConverters;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "HourlyData")
public class HourlyRoom implements Serializable
{


    public HourlyRoom(){


    }


    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @TypeConverters(DataConverters.class)
    private List<Data> data;

    private String icon;

    private String sumary;

    public List<Data> getData ()
    {
        return data;
    }

    public void setData (List<Data> data)
    {
        this.data = data;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getSumary ()
    {
        return sumary;
    }

    public void setSumary (String sumary)
    {
        this.sumary = sumary;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", icon = "+icon+", sumary = "+sumary+"]";
    }


    public class Data
    {
        private String summary;

        private String visibility;

        private String icon;

        private String temperature;

        private String humidity;

        private String windspeed;

        private String time;

        private String pressure;

        private String ozone;

        private String cloudcover;

        public String getSummary ()
        {
            return summary;
        }

        public void setSummary (String summary)
        {
            this.summary = summary;
        }

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
            return "ClassPojo [summary = "+summary+", visibility = "+visibility+", icon = "+icon+", temperature = "+temperature+", humidity = "+humidity+", windspeed = "+windspeed+", time = "+time+", pressure = "+pressure+", ozone = "+ozone+", cloudcover = "+cloudcover+"]";
        }
    }

}

