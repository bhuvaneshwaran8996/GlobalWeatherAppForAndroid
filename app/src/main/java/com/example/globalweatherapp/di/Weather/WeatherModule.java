package com.example.globalweatherapp.di.Weather;


import android.graphics.Color;

import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.network.WeatherApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class WeatherModule {


    @Provides
    public static WeatherApi providegeolocaion(Retrofit retrofit){
       return retrofit.create(WeatherApi.class);


    }
}
