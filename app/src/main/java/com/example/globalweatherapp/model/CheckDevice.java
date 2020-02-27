package com.example.globalweatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckDevice implements Serializable {

    public String DeviceId;
    public  CheckDevice(String DeviceId){
        this.DeviceId = DeviceId;

    }

}
