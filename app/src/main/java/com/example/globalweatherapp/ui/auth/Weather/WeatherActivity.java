package com.example.globalweatherapp.ui.auth.Weather;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.databinding.ActivityMainBinding;
import com.example.globalweatherapp.db.PlacesDataBase;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.CurrentGeoLocation;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.PlaceDetails;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.ui.auth.search.SearchActivity;
import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;
import com.example.globalweatherapp.viewmodels.WeatherViewModel;
import com.google.gson.JsonObject;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;


import java.io.File;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.view.View.GONE;

public class WeatherActivity extends DaggerAppCompatActivity {


    ActivityMainBinding binding;
    public static final int ROOM_CODE = 1000;

    CompositeDisposable compositeDisposable;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    Device device;
    private static final String TAG = "WeatherActivity";

    WeatherViewModel weatherViewModel;

    @Inject
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RealmManager.open();
        compositeDisposable = new CompositeDisposable();
        weatherViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(WeatherViewModel.class);

        device = RealmManager.createDeviceDao().loadAll();

        Log.d(TAG, "onCreate: " + device);


        binding.mainLayout.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WeatherActivity.this, SearchActivity.class), ROOM_CODE);
            }
        });


        init();
        binding.swipeRefersh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    init();

                binding.swipeRefersh.setRefreshing(false);
            }
        });

        getWeatherDetailsFromRoomDB();


    }



    public void init() {


        binding.mainProgress.setVisibility(View.VISIBLE);
        RealmManager.open().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                CurrentGeoLocation currentGeoLocation = realm.where(CurrentGeoLocation.class).findFirst();
                if(currentGeoLocation!=null){
                    Log.d(TAG, "execute: "+currentGeoLocation.city_name);
                    binding.mainLayout.cityName.setText(currentGeoLocation.city_name);
                    binding.mainLayout.cityTime.setText(currentGeoLocation.time_12);
                }else{
                    Log.d(TAG, "execute: "+"null");
                }

                binding.mainProgress.setVisibility(GONE);
            }
        });
    }

    public void getWeatherDetailsFromRoomDB() {


        weatherViewModel.getPlacesRoom(this).observe(this, new Observer<List<PlacesRoom>>() {
            @Override
            public void onChanged(List<PlacesRoom> placesRooms) {


                Log.d(TAG, "onChanged: " + placesRooms.get(placesRooms.size() - 1));
                final PlacesRoom placesRoom = placesRooms.get(placesRooms.size() - 1);

                weatherViewModel.getGeolocation(Constants.IP_GEOLOCATION, getResources().getString(R.string.ip_geoclocation), placesRoom.lat, placesRoom.lon)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new io.reactivex.Observer<Response<GeoLocation>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(Response<GeoLocation> geoLocationResponse) {

                                Log.d(TAG, "onNext: " + geoLocationResponse.body().getTimezone());


                                final CurrentGeoLocation currentGeoLocation = new CurrentGeoLocation();
                                currentGeoLocation.city_name = placesRoom.name;
                                currentGeoLocation.time_12 = geoLocationResponse.body().getTime_12();
                                currentGeoLocation.timezone = geoLocationResponse.body().getTimezone();
                                RealmManager.open().executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.delete(CurrentGeoLocation.class);
                                        realm.insertOrUpdate(currentGeoLocation);

                                    }
                                });
                                binding.mainLayout.cityTime.setText(geoLocationResponse.body().getTime_12());
                                binding.mainLayout.cityName.setText(placesRoom.name);




                                String lang = sharedPreferences.getString("lang","en");
                                weatherViewModel.getCurrentWeather(device.token,placesRoom.lat,placesRoom.lon,lang)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new io.reactivex.Observer<Response<ResponseBody>>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(Response<ResponseBody> responseBodyResponse) {

                                               if(responseBodyResponse.isSuccessful()){
                                                  ResponseBody responseBody  = responseBodyResponse.body();

                                               }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        })








                                binding.mainProgress.setVisibility(GONE);


                            }

                            @Override
                            public void onError(Throwable e) {

                                Log.d(TAG, "onError: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                                Log.d(TAG, "onComplete: ");
                            }
                        });
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        binding.mainProgress.setVisibility(View.VISIBLE);
        if (requestCode == ROOM_CODE) {


            if (data != null && data.getSerializableExtra("placesroom") != null) {
                final PlacesRoom placesRoom = (PlacesRoom) data.getSerializableExtra("placesroom");
                Log.d(TAG, "onActivityResult: " + placesRoom.name);


                weatherViewModel.insertPlacesDetails(WeatherActivity.this, placesRoom);
            }

        }


    }
}




