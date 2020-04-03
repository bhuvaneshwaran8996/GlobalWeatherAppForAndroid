package com.example.globalweatherapp.network;

import com.example.globalweatherapp.model.GeoLocation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeatherApi {

    @GET
    Single<Response<GeoLocation>> getGeoocation(@Url String geolocation, @Query("apiKey") String apikey, @Query("lat") String lat, @Query("long") String lon);


    @POST("api/getcurrent")
    Observable<Response<JsonObject>> getCurrentWeather(@Header("Authorization") @Body String bearer, @Body String lat, @Body String lon, String lang);


}
