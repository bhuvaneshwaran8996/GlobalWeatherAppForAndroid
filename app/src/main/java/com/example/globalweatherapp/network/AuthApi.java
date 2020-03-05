package com.example.globalweatherapp.network;

import com.example.globalweatherapp.model.CheckDevice;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.DeviceDetails;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {



    @POST("checkdevice")
    Flowable<String> checkDevice(@Body CheckDevice checkDevice);

    @POST("login")
    Flowable<Response<ResponseBody>> login(@Body CheckDevice deviceDetails);


    @POST("savedevice")
    Flowable<Response<ResponseBody>> saveDevice(@Body  CheckDevice checkDevice);

}
