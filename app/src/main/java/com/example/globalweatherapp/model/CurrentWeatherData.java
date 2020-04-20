package com.example.globalweatherapp.model;


import java.io.Serializable;

public class CurrentWeatherData implements Serializable
{
    private Current current;

    private String moonphase;

    private String sunsettime;

    private String sunrisetime;

    public Current getCurrent ()
    {
        return current;
    }

    public void setCurrent (Current current)
    {
        this.current = current;
    }

    public String getMoonphase ()
    {
        return moonphase;
    }

    public void setMoonphase (String moonphase)
    {
        this.moonphase = moonphase;
    }

    public String getSunsettime ()
    {
        return sunsettime;
    }

    public void setSunsettime (String sunsettime)
    {
        this.sunsettime = sunsettime;
    }

    public String getSunrisetime ()
    {
        return sunrisetime;
    }

    public void setSunrisetime (String sunrisetime)
    {
        this.sunrisetime = sunrisetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [current = "+current+", moonphase = "+moonphase+", sunsettime = "+sunsettime+", sunrisetime = "+sunrisetime+"]";
    }


    public class Current
    {
        private String summary;

        private String precipProbability;

        private String visibility;

        private String windGust;

        private String precipIntensity;

        private String icon;

        private String cloudCover;

        private String windBearing;

        private String apparentTemperature;

        private String pressure;

        private String dewPoint;

        private String ozone;

        private String nearestStormBearing;

        private String nearestStormDistance;

        private String temperature;

        private String humidity;

        private String time;

        private String windSpeed;

        private String uvIndex;

        public String getSummary ()
        {
            return summary;
        }

        public void setSummary (String summary)
        {
            this.summary = summary;
        }

        public String getPrecipProbability ()
        {
            return precipProbability;
        }

        public void setPrecipProbability (String precipProbability)
        {
            this.precipProbability = precipProbability;
        }

        public String getVisibility ()
        {
            return visibility;
        }

        public void setVisibility (String visibility)
        {
            this.visibility = visibility;
        }

        public String getWindGust ()
        {
            return windGust;
        }

        public void setWindGust (String windGust)
        {
            this.windGust = windGust;
        }

        public String getPrecipIntensity ()
        {
            return precipIntensity;
        }

        public void setPrecipIntensity (String precipIntensity)
        {
            this.precipIntensity = precipIntensity;
        }

        public String getIcon ()
        {
            return icon;
        }

        public void setIcon (String icon)
        {
            this.icon = icon;
        }

        public String getCloudCover ()
        {
            return cloudCover;
        }

        public void setCloudCover (String cloudCover)
        {
            this.cloudCover = cloudCover;
        }

        public String getWindBearing ()
        {
            return windBearing;
        }

        public void setWindBearing (String windBearing)
        {
            this.windBearing = windBearing;
        }

        public String getApparentTemperature ()
        {
            return apparentTemperature;
        }

        public void setApparentTemperature (String apparentTemperature)
        {
            this.apparentTemperature = apparentTemperature;
        }

        public String getPressure ()
        {
            return pressure;
        }

        public void setPressure (String pressure)
        {
            this.pressure = pressure;
        }

        public String getDewPoint ()
        {
            return dewPoint;
        }

        public void setDewPoint (String dewPoint)
        {
            this.dewPoint = dewPoint;
        }

        public String getOzone ()
        {
            return ozone;
        }

        public void setOzone (String ozone)
        {
            this.ozone = ozone;
        }

        public String getNearestStormBearing ()
        {
            return nearestStormBearing;
        }

        public void setNearestStormBearing (String nearestStormBearing)
        {
            this.nearestStormBearing = nearestStormBearing;
        }

        public String getNearestStormDistance ()
        {
            return nearestStormDistance;
        }

        public void setNearestStormDistance (String nearestStormDistance)
        {
            this.nearestStormDistance = nearestStormDistance;
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

        public String getTime ()
        {
            return time;
        }

        public void setTime (String time)
        {
            this.time = time;
        }

        public String getWindSpeed ()
        {
            return windSpeed;
        }

        public void setWindSpeed (String windSpeed)
        {
            this.windSpeed = windSpeed;
        }

        public String getUvIndex ()
        {
            return uvIndex;
        }

        public void setUvIndex (String uvIndex)
        {
            this.uvIndex = uvIndex;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [summary = "+summary+", precipProbability = "+precipProbability+", visibility = "+visibility+", windGust = "+windGust+", precipIntensity = "+precipIntensity+", icon = "+icon+", cloudCover = "+cloudCover+", windBearing = "+windBearing+", apparentTemperature = "+apparentTemperature+", pressure = "+pressure+", dewPoint = "+dewPoint+", ozone = "+ozone+", nearestStormBearing = "+nearestStormBearing+", nearestStormDistance = "+nearestStormDistance+", temperature = "+temperature+", humidity = "+humidity+", time = "+time+", windSpeed = "+windSpeed+", uvIndex = "+uvIndex+"]";
        }
    }


}

