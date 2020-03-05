package com.example.globalweatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewDevice implements Serializable {

    @SerializedName("DeviceName")
    public String DeviceName;

    @SerializedName("DeviceLat")
    public String DeviceLat;

    @SerializedName("DeviceLon")
    public String DeviceLon;

    @SerializedName("GPSPermitted")
    public Boolean GPSPermitted;

    @SerializedName("DeviceId")
    public String DeviceId;

    @SerializedName("token")
    public String token;

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDeviceLat() {
        return DeviceLat;
    }

    public void setDeviceLat(String deviceLat) {
        DeviceLat = deviceLat;
    }

    public String getDeviceLon() {
        return DeviceLon;
    }

    public void setDeviceLon(String deviceLon) {
        DeviceLon = deviceLon;
    }

    public Boolean getGPSPermitted() {
        return GPSPermitted;
    }

    public void setGPSPermitted(Boolean GPSPermitted) {
        this.GPSPermitted = GPSPermitted;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NewDevice(String deviceName, String deviceLat, String deviceLon, Boolean GPSPermitted, String deviceId, String token) {
        DeviceName = deviceName;
        DeviceLat = deviceLat;
        DeviceLon = deviceLon;
        this.GPSPermitted = GPSPermitted;
        DeviceId = deviceId;
        this.token = token;
    }
}
