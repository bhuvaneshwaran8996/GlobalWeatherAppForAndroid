package com.example.globalweatherapp.network;

import com.example.globalweatherapp.model.PlaceDetails;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SearchApi {



    @Headers({"Content-Type:application/json"})
    @GET("api/getcities")
    Observable<Response<ResponseBody>> getPlaces(@Header("Authorization") String authorization, @Query("address") String address);
}
