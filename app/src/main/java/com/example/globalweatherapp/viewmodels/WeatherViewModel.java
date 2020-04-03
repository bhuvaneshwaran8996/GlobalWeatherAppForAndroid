package com.example.globalweatherapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.Repository.WeatherRepository;
import com.example.globalweatherapp.di.Weather.WeatherViewMolesModule;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.PlacesRoom;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {



    public WeatherRepository weatherRepository;
    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository){
        this.weatherRepository = weatherRepository;
    }


    public LiveData<List<PlacesRoom>> getPlacesRoom(Context context){
        return  this.weatherRepository.getPlacesDataFromDB(context);


    }
    public void insertPlacesDetails(Context context , PlacesRoom placesRoom){
        this.weatherRepository.insertPlacesDetails(context,placesRoom);
    }

    public Observable<Response<ResponseBody>> getCurrentWeather(String token, String lat, String lon, String lang){
        this.weatherRepository.getCurrentData(token,lat,lon,lang);

    }
    public void deletePlacesDetails(Context context){
        weatherRepository.deleteAll(context);
    }

    public Single<Response<GeoLocation>> getGeolocation(String url, String Apikey, String lat, String lon){
        return weatherRepository.getGeolocation(url,Apikey,lat,lon);
    }

}
