package com.example.globalweatherapp.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.globalweatherapp.db.PlacesDataBase;
import com.example.globalweatherapp.model.CurrentWeather;
import com.example.globalweatherapp.model.DayRetofit;
import com.example.globalweatherapp.model.DayRoom;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.HourlyDataRoomDB;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.network.WeatherApi;
import com.google.gson.JsonObject;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
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
    MediatorLiveData<List<DayRoom>> daymediator = new MediatorLiveData<>();
//    List<HourlyRoom> hourlydatamediator = new ArrayList<>();
//

    MediatorLiveData<List<HourlyDataRoomDB>> placeshourlydb = new MediatorLiveData<>();


    public WeatherApi weatherApi;
    @Inject
    public WeatherRepository(WeatherApi weatherApi) {

        this.weatherApi = weatherApi;
    }

    public LiveData<List<PlacesRoom>> getPlacesDataFromDB(Context context) {


        return pleacesmediator;


    }

    public LiveData<List<DayRoom>> getDayaData(){
        return daymediator;
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
//
//    public void setHourlyData(final Context context , HourlyRoom hourlyRoom){
//
//        new InsertHourlyData(context).execute(hourlyRoom);
//        //new GetAllHourlyAsyncTask(context).execute();
//
//    }

    public Observable<Response<JsonObject>> getCurrentData(String token, CurrentWeather currentWeather){
       return weatherApi.getCurrentWeather(token,currentWeather)
                .subscribeOn(Schedulers.io());

    }

    public Observable<DayRetofit> getDayWeatherData(String token, CurrentWeather currentWeather){
        return weatherApi.getDayData(token,currentWeather).
                subscribeOn(Schedulers.io());
    }

    public Observable<HourlyRoom> getHourlyData(String token, CurrentWeather currentWeather){
        return weatherApi.getHourlyData(token,currentWeather).subscribeOn(Schedulers.io());
    }
    public Single<Response<GeoLocation>> getGeolocation(String url, String ApiKey, String lat, String lon){


       return weatherApi.getGeoocation(url,ApiKey,lat,lon)
               .subscribeOn(Schedulers.io());


    }

    public void insertHourlyData(HourlyDataRoomDB hourlyDataRoomDB, Context context){
        new InsertHourlyData(context).execute(hourlyDataRoomDB);
    }

    public void insertDayDataRoom(Context context, DayRoom dayRoom){
        new InsertDayData(context).execute(dayRoom);

    }
    public LiveData<List<HourlyDataRoomDB>> getHourlyData(){
        return placeshourlydb;
    }

//    public List<HourlyRoom>  getHourlyRoom(Context context){
//
//        new GetHourlyData(context).execute();
//        return hourlydatamediator;
//    }



//    public class GetHourlyData extends AsyncTask<Void, Void, Void>{
//
//        Context context;
//        public GetHourlyData(Context context){
//            this.context = context;
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            hourlydatamediator = PlacesDataBase.getPlacesDataBase(context).placesDao().getHoyrlyDetails();
//
//            return null;
//        }
//
//    }
//    public class InsertHourlyData  extends AsyncTask<HourlyRoom,Void, Void>{
//
//
//        Context context;
//        public  InsertHourlyData(Context context){
//            this.context = context;
//        }
//
//        @Override
//        protected Void doInBackground(HourlyRoom... hourlyRooms) {
//         long insert   = PlacesDataBase.getPlacesDataBase(context)
//                    .placesDao().insert(hourlyRooms[0]);
//            Log.d(TAG, "doInBackground: "+insert);
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//
////            new Handler().postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    hourlyRoomList  = PlacesDataBase.getPlacesDataBase(context).placesDao().getHoyrlyDetails();
////                    Log.d(TAG, "run: "+hourlyRoomList.size());
////                    HourlyRoom.Data data = (HourlyRoom.Data) hourlyRoomList.get(hourlyRoomList.size()-1).getData();
////                    Log.d(TAG, "run: "+data);
////
////                }
////            },200);
////
////            Log.d(TAG, "onPostExecute: "+"data placesdb");
//
//
//
//        }
//    }
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

//
//    public class GetAllHourlyAsyncTask extends AsyncTask<Void, Void, Void>{
//
//        Context context;
//        public GetAllHourlyAsyncTask(Context context){
//            this.context = context;
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            hourlyRoomList =    PlacesDataBase.getPlacesDataBase(context).placesDao().getHoyrlyDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//    }


    public class InsertDayData extends AsyncTask<DayRoom, Void, Void>{

        Context context;
        public InsertDayData(Context context){
            this.context = context;
        }
        @Override
        protected Void doInBackground(DayRoom... dayRooms) {

            PlacesDataBase.getPlacesDataBase(context).placesDao().insert(dayRooms[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final LiveData<List<DayRoom>> darroomlivedata = PlacesDataBase.getPlacesDataBase(context).placesDao().getDayRoomData();
            daymediator.addSource(darroomlivedata, new Observer<List<DayRoom>>() {
                @Override
                public void onChanged(List<DayRoom> dayRooms) {
                    daymediator.setValue(dayRooms);
                    daymediator.removeSource(darroomlivedata);
                }
            });
            super.onPostExecute(aVoid);
        }
    }


    public class InsertHourlyData extends AsyncTask<HourlyDataRoomDB, Void, Void>{

        Context context;
        public InsertHourlyData(Context context){
            this.context = context;

        }
        @Override
        protected Void doInBackground(HourlyDataRoomDB... hourlyDataRoomDBS) {
            PlacesDataBase.getPlacesDataBase(context).placesDao().insert(hourlyDataRoomDBS[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final LiveData<List<HourlyDataRoomDB>> listLiveDatahourly = PlacesDataBase.getPlacesDataBase(context)
                    .placesDao().getHourlyData();
            placeshourlydb.addSource(listLiveDatahourly, new Observer<List<HourlyDataRoomDB>>() {
                @Override
                public void onChanged(List<HourlyDataRoomDB> hourlyDataRoomDBS) {
                    placeshourlydb.setValue(hourlyDataRoomDBS);
                    placeshourlydb.removeSource(listLiveDatahourly);
                }
            });

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
