package com.example.globalweatherapp.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.globalweatherapp.db.PlacesDataBase;
import com.example.globalweatherapp.model.CurrentWeather;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.network.WeatherApi;
import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class WeatherRepository {


    private static final String TAG = "WeatherRepository";
    MediatorLiveData<List<PlacesRoom>> pleacesmediator = new MediatorLiveData<>();
    MediatorLiveData<List<HourlyRoom>> hourlydatamediator = new MediatorLiveData<>();


    public WeatherApi weatherApi;
    @Inject
    public WeatherRepository(WeatherApi weatherApi) {

        this.weatherApi = weatherApi;
    }

    public LiveData<List<PlacesRoom>> getPlacesDataFromDB(Context context) {


        return pleacesmediator;


    }


//    public LiveData<List<HourlyRoom>> getHourlyData(){
//        return hourlydatamediator;
//    }
    public void deleteAll(Context context){
        new DeleteAsyncTaskAll(context).execute();
    }
    public void insertPlacesDetails(Context context, PlacesRoom placesRoom) {

        new InsertAsyncTask(context).execute(placesRoom);


    }

    public LiveData<List<HourlyRoom>> setHourlyData(Context context , HourlyRoom hourlyRoom){

        new InsertHourlyData(context).execute(hourlyRoom);
        return hourlydatamediator;

    }

    public Observable<Response<JsonObject>> getCurrentData(String token, CurrentWeather currentWeather){
       return weatherApi.getCurrentWeather(token,currentWeather)
                .subscribeOn(Schedulers.io());

    }

    public Observable<HourlyRoom> getHourlyData(String token, CurrentWeather currentWeather){
        return weatherApi.getHourlyData(token,currentWeather);
    }
    public Single<Response<GeoLocation>> getGeolocation(String url, String ApiKey, String lat, String lon){


       return weatherApi.getGeoocation(url,ApiKey,lat,lon)
               .subscribeOn(Schedulers.io());


    }

    public class InsertHourlyData  extends AsyncTask<HourlyRoom,Void, Void>{


        Context context;
        public  InsertHourlyData(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(HourlyRoom... hourlyRooms) {
            PlacesDataBase.getPlacesDataBase(context)
                    .placesDao().insert(hourlyRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            final LiveData<List<HourlyRoom>> hourlylivedata = PlacesDataBase.getPlacesDataBase(context).placesDao().getHourlyData();
            hourlydatamediator.addSource(hourlylivedata, new Observer<List<HourlyRoom>>() {
                @Override
                public void onChanged(List<HourlyRoom> hourlyRooms) {
                    hourlydatamediator.setValue(hourlyRooms);
                    hourlydatamediator.removeSource(hourlylivedata);
                }
            });
        }
    }
    public class DeleteAsyncTaskAll extends AsyncTask<Void,Void,Void>{

        Context context;
        public DeleteAsyncTaskAll(Context context){
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids) {

            PlacesDataBase.getPlacesDataBase(context)
                    .placesDao().deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: "+"Deleted all");
        }
    }

    public class InsertAsyncTask extends AsyncTask<PlacesRoom, Void, Void> {

        Context context;

        public InsertAsyncTask(Context context) {
            this.context = context;

        }


        @Override
        protected Void doInBackground(PlacesRoom... placesRooms) {
            PlacesDataBase.getPlacesDataBase(context)
                    .placesDao().insert(placesRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final LiveData<List<PlacesRoom>> listLiveData = PlacesDataBase.getPlacesDataBase(context).placesDao().getPlacesDetails();

            pleacesmediator.addSource(listLiveData, new Observer<List<PlacesRoom>>() {
                @Override
                public void onChanged(List<PlacesRoom> placesRooms) {
                    pleacesmediator.setValue(placesRooms);
                    pleacesmediator.removeSource(listLiveData);
                }
            });
        }
    }
}