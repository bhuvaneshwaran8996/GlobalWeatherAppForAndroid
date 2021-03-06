package com.example.globalweatherapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.Repository.WeatherRepository;
import com.example.globalweatherapp.di.Weather.WeatherViewMolesModule;
import com.example.globalweatherapp.model.CurrentWeather;
import com.example.globalweatherapp.model.CurrentWeatherData;
import com.example.globalweatherapp.model.DayRetofit;
import com.example.globalweatherapp.model.DayRoom;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.HourlyDataRoomDB;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;
import com.google.gson.JsonObject;

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

    public Observable<Response<JsonObject>> getCurrentWeather(String token, CurrentWeather currentWeather){
    return    this.weatherRepository.getCurrentData(token,currentWeather);

    }

    public Observable<HourlyRoom> getHourlyData(String token, CurrentWeather currentWeather){
        return  this.weatherRepository.getHourlyData(token,currentWeather);
    }
    public void deletePlacesDetails(Context context){
        weatherRepository.deleteAll(context);
    }

    public Single<Response<GeoLocation>> getGeolocation(String url, String Apikey, String lat, String lon){
        return weatherRepository.getGeolocation(url,Apikey,lat,lon);
    }


    public void insertHourly(HourlyDataRoomDB hourlyDataRoomDB, Context context){
        weatherRepository.insertHourlyData(hourlyDataRoomDB,context);
    }
    public LiveData<List<HourlyDataRoomDB>> getHourlyList(){
        return weatherRepository.getHourlyData();
    }
    public Observable<DayRetofit> getDayWeatherData(String token, CurrentWeather currentWeather){
        return weatherRepository.getDayWeatherData(token, currentWeather);
    }
    public void insertDayDataRoom(Context context, DayRoom dayRoom){
        weatherRepository.insertDayDataRoom(context,dayRoom);
    }
    public LiveData<List<DayRoom>> getDayRoom(){
        return weatherRepository.getDayaData();
    }
//    public List<HourlyRoom> getHourlyRoom(Context context){
//        return  weatherRepository.getHourlyRoom(context);
//    }
//    public  void setHourlyDataFromRoom(Context context, HourlyRoom hourlyRoom){
//        weatherRepository.setHourlyData(context,hourlyRoom);
//    }
//

}
