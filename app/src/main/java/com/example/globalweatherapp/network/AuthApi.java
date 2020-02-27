package com.example.globalweatherapp.network;

import com.example.globalweatherapp.model.CheckDevice;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {



    @POST("checkdevice")
    Flowable<String> checkDevice(@Body CheckDevice checkDevice);
}
