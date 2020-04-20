package com.example.globalweatherapp.ui.auth.Weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.databinding.ActivityMainBinding;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.CurrentGeoLocation;
import com.example.globalweatherapp.model.CurrentWeather;
import com.example.globalweatherapp.model.CurrentWeatherData;
import com.example.globalweatherapp.model.CurretWeatherDataRealm;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.ui.auth.search.SearchActivity;
import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;
import com.example.globalweatherapp.viewmodels.WeatherViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;


import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;


import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
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

    public String lang;
    WeatherViewModel weatherViewModel;
    public Integer temp;
    //    ObservableField<String> observabledegres = new ObservableField<>();
    @Inject
    SharedPreferences sharedPreferences;
    String C_or_F;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RealmManager.open();
        compositeDisposable = new CompositeDisposable();
        weatherViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(WeatherViewModel.class);


        if (sharedPreferences.getString("lang", "en") != null && !sharedPreferences.getString("lang", "en").equalsIgnoreCase("")) {

            lang = sharedPreferences.getString("lang", "en");

        } else {
            sharedPreferences.edit().putString("lang", "en");
            lang = "en";
        }

        if (sharedPreferences.getString("C_or_F", "F") != null && !sharedPreferences.getString("C_or_F", "F").equalsIgnoreCase("")) {
            C_or_F = sharedPreferences.getString("C_or_F", "C"); // the values should be in celsius or fahrenhiet
//            observabledegres.set(C_or_F);

        } else {
            sharedPreferences.edit().putString("C_or_F", "F").commit();
            C_or_F = "C";
//            observabledegres.set(C_or_F);
        }
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
                if (currentGeoLocation != null) {
                    Log.d(TAG, "execute: " + currentGeoLocation.city_name);
//                    binding.mainLayout.cityName.setText(currentGeoLocation.city_name);
                    binding.mainLayout.cityTime.setText(currentGeoLocation.time_12);
                } else {
                    Log.d(TAG, "execute: " + "null");
                }

                CurretWeatherDataRealm curretWeatherDataRealm = realm.where(CurretWeatherDataRealm.class).findFirst();
                if (curretWeatherDataRealm != null) {

                    binding.o.setVisibility(View.VISIBLE);
                    binding.o2.setVisibility(View.VISIBLE);
                    binding.feelslike.setVisibility(View.VISIBLE);
                    binding.cOrF.setVisibility(View.VISIBLE);
                    binding.setCurrentweather(curretWeatherDataRealm);
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
                            public void onNext(final Response<GeoLocation> geoLocationResponse) {
                                Log.d(TAG, "onNext: " + geoLocationResponse.body().getTimezone());
                                final CurrentGeoLocation currentGeoLocation = new CurrentGeoLocation();
                                currentGeoLocation.city_name = placesRoom.name;
                                currentGeoLocation.time_12 = geoLocationResponse.body().getTime_12();
                                currentGeoLocation.timezone = geoLocationResponse.body().getTimezone();
                                currentGeoLocation.date = geoLocationResponse.body().getDate();
                                currentGeoLocation.date_time_txt = geoLocationResponse.body().getDate_time_txt();
                                RealmManager.open().executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.delete(CurrentGeoLocation.class);
                                        realm.insertOrUpdate(currentGeoLocation);
                                    }
                                });
                                binding.mainLayout.cityTime.setText(geoLocationResponse.body().getTime_12());
//                                binding.mainLayout.cityName.setText(placesRoom.name);
                                final CurrentWeather currentWeather = new CurrentWeather(placesRoom.lat, placesRoom.lon, lang);
                                weatherViewModel.getCurrentWeather(device.token, currentWeather)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new io.reactivex.Observer<Response<JsonObject>>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                                compositeDisposable.add(d);
                                            }

                                            @Override
                                            public void onNext(Response<JsonObject> responseBodyResponse) {
                                                if (responseBodyResponse.isSuccessful()) {
                                                    JsonObject responseBody = responseBodyResponse.body();

                                                    Type typeOfObjectsList = new TypeToken<CurrentWeatherData>() {
                                                    }.getType();
                                                    CurrentWeatherData currentWeatherData = new Gson().fromJson(responseBodyResponse.body(), typeOfObjectsList);
                                                    Log.d(TAG, "onNext: " + currentWeatherData);

                                                    final String weathericon = currentWeatherData.getCurrent().getIcon();



                                                    final CurretWeatherDataRealm curretWeatherDataRealm = new CurretWeatherDataRealm();

                                                    if (C_or_F.equalsIgnoreCase("F")) {
                                                        temp = Integer.parseInt(currentWeatherData.getCurrent().getTemperature());
                                                        C_or_F = "F";

                                                        curretWeatherDataRealm.setApparentTempertaure(String.valueOf(Integer.parseInt(currentWeatherData.getCurrent().getApparentTemperature())));

                                                    } else {
                                                        temp = fahrenhietToCelsius(Double.parseDouble(currentWeatherData.getCurrent().getTemperature()));
                                                        curretWeatherDataRealm.setApparentTempertaure(String.valueOf(fahrenhietToCelsius(Double.parseDouble(currentWeatherData.getCurrent().getApparentTemperature()))));
                                                        C_or_F = "C";
                                                    }


                                                    curretWeatherDataRealm.CityName = placesRoom.name;
                                                    curretWeatherDataRealm.WeatherSummary = currentWeatherData.getCurrent().getSummary();
                                                    curretWeatherDataRealm.WeatherDisplay = temp.toString();
                                                    curretWeatherDataRealm.C_or_F = C_or_F;
                                                    String[] datsplit = currentGeoLocation.date_time_txt.split(",");
                                                    curretWeatherDataRealm.Day = datsplit[1];
                                                    curretWeatherDataRealm.Month = datsplit[0];


                                                    RealmManager.open().executeTransaction(new Realm.Transaction() {
                                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                                        @Override
                                                        public void execute(final Realm realm) {
                                                            realm.delete(CurretWeatherDataRealm.class);
                                                            realm.insertOrUpdate(curretWeatherDataRealm);

                                                            binding.o.setVisibility(View.VISIBLE);
                                                            binding.o2.setVisibility(View.VISIBLE);
                                                            binding.feelslike.setVisibility(View.VISIBLE);
                                                            binding.cOrF.setVisibility(View.VISIBLE);
                                                            binding.setCurrentweather(curretWeatherDataRealm);


                                                            //-------------------------hourly daya-------------------------//



                                                            weatherViewModel.getHourlyData(device.token,currentWeather)// Reftrofit api calls for hourlydata
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                    .subscribe(new io.reactivex.Observer<HourlyRoom>() {
                                                                        @Override
                                                                        public void onSubscribe(Disposable d) {
                                                                            compositeDisposable.add(d);
                                                                        }

                                                                        @Override
                                                                        public void onNext(final HourlyRoom hourlyRoom) {

                                                                            if(hourlyRoom!=null){
                                                                                Log.d(TAG, "onNext: "+hourlyRoom.getData().size());

                                                                                weatherViewModel.getHourlyDataFromRoom(WeatherActivity.this,hourlyRoom)
                                                                                        .observe(WeatherActivity.this, new Observer<List<HourlyRoom>>() {
                                                                                            @Override
                                                                                            public void onChanged(List<HourlyRoom> hourlyRooms) {

                                                                                               HourlyRoom hourlyRoom1last =  hourlyRooms.get(hourlyRooms.size()-1);
                                                                                               List<HourlyRoom.Data> hourlydata = hourlyRoom1last.getData();


                                                                                                Log.d(TAG, "onChanged: hourly"+hourlydata.size());
                                                                                               binding.setHourlydata(hourlydata);



                                                                                            }
                                                                                        });


                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onError(Throwable e) {

                                                                            Log.d(TAG, "onError: "+e.getMessage());
                                                                        }

                                                                        @Override
                                                                        public void onComplete() {

                                                                            Log.d(TAG, "onComplete: hourlydata");
                                                                        }
                                                                    });


                                                        }
                                                    });


                                                }
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

                                binding.mainProgress.setVisibility(GONE);


                            }

                            @Override
                            public void onError(Throwable e) {

                                Log.d(TAG, "onError: " + e.getMessage());
                            }

                            @RequiresApi(api = Build.VERSION_CODES.O)
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

    public double celsiusToFahrenhiet(double calcius) {

        return (calcius * 1.8) + 32;
    }

    public Integer fahrenhietToCelsius(double fahrenhiet) {
        BigDecimal bigDecimal = new BigDecimal( ((fahrenhiet - 32) / 18)*10);


        return bigDecimal.intValue();
    }


}




