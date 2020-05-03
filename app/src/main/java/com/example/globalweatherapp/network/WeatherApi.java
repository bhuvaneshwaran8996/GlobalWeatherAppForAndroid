package com.example.globalweatherapp.network;

import com.example.globalweatherapp.model.CurrentWeather;
import com.example.globalweatherapp.model.CurrentWeatherData;
import com.example.globalweatherapp.model.DayRetofit;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.HourlyRoom;
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
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface WeatherApi {

    @GET
    Single<Response<GeoLocation>> getGeoocation(@Url String geolocation, @Query("apiKey") String apikey, @Query("lat") String lat, @Query("long") String lon);


    @Headers({"Content-Type:application/json"})
    @POST("api/getcurrent")
    Observable<Response<JsonObject>> getCurrentWeather(@Header("Authorization")  String bearer, @Body CurrentWeather currentWeather);


    @Headers({"Content-Type:application/json"})
    @POST("api/gethourly")
    Observable<HourlyRoom> getHourlyData(@Header("Authorization") String bearer, @Body CurrentWeather currentWeather);

    @Headers({"Content-Type:application/json"})
    @POST("api/getday")
    Observable<DayRetofit> getDayData(@Header("Authorization") String bearer, @Body CurrentWeather currentWeather);
}
