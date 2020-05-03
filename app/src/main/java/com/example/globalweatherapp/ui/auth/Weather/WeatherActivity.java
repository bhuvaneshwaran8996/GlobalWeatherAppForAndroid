package com.example.globalweatherapp.ui.auth.Weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.adapter.DayRecyclerAdapter;
import com.example.globalweatherapp.adapter.HourlyRecyclerAdapter;
import com.example.globalweatherapp.databinding.ActivityMainBinding;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.CurrentGeoLocation;
import com.example.globalweatherapp.model.CurrentWeather;
import com.example.globalweatherapp.model.CurrentWeatherData;
import com.example.globalweatherapp.model.CurretWeatherDataRealm;
import com.example.globalweatherapp.model.Data;
import com.example.globalweatherapp.model.DayDataRealm;
import com.example.globalweatherapp.model.DayRealm;
import com.example.globalweatherapp.model.DayRetofit;
import com.example.globalweatherapp.model.DayRoom;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.GeoLocation;
import com.example.globalweatherapp.model.HourlyDataRoomDB;
import com.example.globalweatherapp.model.HourlyRealm;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.model.RealmHourly;
import com.example.globalweatherapp.model.RealmHourlyData;
import com.example.globalweatherapp.ui.auth.search.SearchActivity;
import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;
import com.example.globalweatherapp.viewmodels.WeatherViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
    public String temp;
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
        if (sharedPreferences.getString("C_or_F", "C") != null && !sharedPreferences.getString("C_or_F", "C").equalsIgnoreCase("")) {
            C_or_F = sharedPreferences.getString("C_or_F", "C"); // the values should be in celsius or fahrenhiet
//            observabledegres.set(C_or_F);
        } else {
            sharedPreferences.edit().putString("C_or_F", "C").commit();
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
        observerHourlyInsertion();
        observeDayImsertion();
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

                Log.d(TAG, "init currentgeolocation "+realm.where(CurrentGeoLocation.class).count());
                Log.d(TAG, "init hurlyweatherdata "+realm.where(CurretWeatherDataRealm.class).count());
                Log.d(TAG, "init RealmHourly "+realm.where(RealmHourly.class).count());
                Log.d(TAG, "init DayRealm "+realm.where(DayRealm.class).count());


//                realm.where(CurrentGeoLocation.class).findAll().deleteAllFromRealm();
//                realm.where(CurretWeatherDataRealm.class).findAll().deleteAllFromRealm();
//                realm.where(RealmHourly.class).findAll().deleteAllFromRealm();
//                realm.where(DayRealm.class).findAll().deleteAllFromRealm();




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

                Log.d(TAG, "execute: realm" + realm.where(RealmHourly.class).count());
                RealmHourly realmHourly = realm.where(RealmHourly.class).findFirst();

                if (realmHourly != null) {

                    Collections.reverse(realmHourly.getRealmHourlyDataRealmList());
                    HourlyRecyclerAdapter hourlyRecyclerAdapter = new HourlyRecyclerAdapter(WeatherActivity.this, realmHourly.getRealmHourlyDataRealmList(), C_or_F, realmHourly.getStringRealmList());
                    binding.hourlyrecyclerview.setLayoutManager(new LinearLayoutManager(WeatherActivity.this, LinearLayoutManager.HORIZONTAL, true));
                    binding.hourlyrecyclerview.setHasFixedSize(true);
                    binding.hourlyrecyclerview.setAdapter(hourlyRecyclerAdapter);
                    binding.hourlyrecyclerview.scrollToPosition(0);
                    hourlyRecyclerAdapter.notifyDataSetChanged();

                }

                DayRealm dayRealm = realm.where(DayRealm.class).findFirst();
                if (dayRealm != null) {
                    Log.d(TAG, "execute: dayreal " + realm.where(DayRealm.class).count());
                    DayRecyclerAdapter dayRecyclerAdapter = new DayRecyclerAdapter(WeatherActivity.this,dayRealm.getData(),dayRealm.getStringdaylist(),C_or_F,binding);
                    binding.dayrecyclerview.setLayoutManager(new LinearLayoutManager(WeatherActivity.this,LinearLayoutManager.VERTICAL,true));
                    binding.dayrecyclerview.setHasFixedSize(true);
                    binding.dayrecyclerview.setAdapter(dayRecyclerAdapter);

                }

//                Type typeOfObjectsList = new TypeToken<List<HourlyRealm>>() {
//                }.getType();
//                HourAndDayRealm hourAndDayRealm = realm.where(HourlyRealm.class).findFirst();
//                if(hourAndDayRealm!=null){
//                    List<HourlyRealm> hourlyRealmList = hourAndDayRealm.getHourlylist();
//                    if(hourlyRealmList!=null) {
//
//                        HourlyRecyclerAdapter hourlyRecyclerAdapter = ((HourlyRecyclerAdapter)binding.hourlyrecyclerview.getAdapter());
//                        if (hourlyRecyclerAdapter!= null) {
//
//                            ((HourlyRecyclerAdapter) binding.hourlyrecyclerview.getAdapter()).updateList(hourlyRealmList);
//                            ((HourlyRecyclerAdapter) binding.hourlyrecyclerview.getAdapter()).notifyDataSetChanged();
//                        }
//
//                    }
//
////               HourAndDayRealm hourAndDayRealm =  realm.where(HourAndDayRealm.class).findFirst();
////               RealmList<HourlyRoom.Data> hourlydata =  hourAndDayRealm.getHourlylist();
////               List<HourlyRoom.Data> hourlyfirtdata = (List<HourlyRoom.Data>) hourlydata.get(0);
////               if(hourlyfirtdata!=null){
//
////                 }
////               }
//                }
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

                                                    final Type typeOfObjectsList = new TypeToken<CurrentWeatherData>() {
                                                    }.getType();
                                                    CurrentWeatherData currentWeatherData = new Gson().fromJson(responseBodyResponse.body(), typeOfObjectsList);
                                                    Log.d(TAG, "onNext: " + currentWeatherData);

                                                    final String weathericon = currentWeatherData.getCurrent().getIcon();


                                                    final CurretWeatherDataRealm curretWeatherDataRealm = new CurretWeatherDataRealm();

                                                    if (C_or_F.equalsIgnoreCase("F")) {
                                                        temp = String.valueOf(Double.parseDouble(currentWeatherData.getCurrent().getTemperature()));
                                                        C_or_F = "F";

                                                        curretWeatherDataRealm.setApparentTempertaure(String.valueOf(Double.parseDouble(currentWeatherData.getCurrent().getApparentTemperature())));

                                                    } else {
                                                        temp = String.valueOf(fahrenhietToCelsius(Double.parseDouble(currentWeatherData.getCurrent().getTemperature())));
                                                        curretWeatherDataRealm.setApparentTempertaure(String.valueOf(fahrenhietToCelsius(Double.parseDouble(currentWeatherData.getCurrent().getApparentTemperature()))));
                                                        C_or_F = "C";
                                                    }


                                                    curretWeatherDataRealm.CityName = placesRoom.name;
                                                    curretWeatherDataRealm.WeatherSummary = currentWeatherData.getCurrent().getSummary();
                                                    curretWeatherDataRealm.WeatherDisplay = temp.toString();
                                                    curretWeatherDataRealm.C_or_F = C_or_F;
                                                    final String[] datsplit = currentGeoLocation.date_time_txt.split(",");
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
                                        weatherViewModel.getHourlyData(device.token, currentWeather)// Reftrofit api calls for hourlydata
                                        .observeOn(AndroidSchedulers.mainThread())
                                          .subscribe(new io.reactivex.Observer<HourlyRoom>() {
                                            @Override
                                              public void onSubscribe(Disposable d) {
                                               compositeDisposable.add(d);
                                               }

                                                @Override
                                                 public void onNext(final HourlyRoom hourlyRoom) {


                                                                            if (hourlyRoom != null) {

                                                                                Log.d(TAG, "onNext: " + hourlyRoom);


                                                                                Log.d(TAG, "onNext: hourlysize" + hourlyRoom.getData().size());

                                                                                final List<HourlyRoom.Data> two_days_data = new ArrayList<>();

                                                                                for (int i = 0; i < hourlyRoom.getData().size(); i++) {
                                                                                    if (i == 49) {

                                                                                        break;
                                                                                    }

                                                                                    two_days_data.add(hourlyRoom.getData().get(i));

                                                                                }
                                                                                Log.d(TAG, "onNext: " + two_days_data);


                                                                                String hourlyjsonstring = new Gson().toJson(two_days_data);

                                                                                Type typeOfObjectsList = new TypeToken<List<Data>>() {
                                                                                }.getType();
                                                                                if (hourlyjsonstring != null && !hourlyjsonstring.equalsIgnoreCase("")) {
                                                                                    Log.d(TAG, "onNext: hourlyjson " + hourlyjsonstring);
                                                                                    final List<Data> hourlyRealmList = new Gson().fromJson(hourlyjsonstring, typeOfObjectsList);
                                                                                    Log.d(TAG, "onNext: json" + hourlyRealmList);
//
                                                                                    HourlyDataRoomDB hourlyDataRoomDB = new HourlyDataRoomDB();
                                                                                    hourlyDataRoomDB.setData(hourlyRealmList);
                                                                                    hourlyDataRoomDB.setSumary(hourlyRoom.getSumary());
                                                                                    hourlyDataRoomDB.setIcon(hourlyRoom.getIcon());

                                                                                    weatherViewModel.insertHourly(hourlyDataRoomDB, WeatherActivity.this);


                                                                                    //day data-----------------------------------------------//
                                                                                    weatherViewModel.getDayWeatherData(device.token, currentWeather)
                                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                                            .subscribe(new io.reactivex.Observer<DayRetofit>() {
                                                                                                @Override
                                                                                                public void onSubscribe(Disposable d) {
                                                                                                    compositeDisposable.add(d);
                                                                                                }

                                                                                                @Override
                                                                                                public void onNext(DayRetofit dayRetofit) {


                                                                                                    if (dayRetofit != null) {

                                                                                                        String jsontoroom = new Gson().toJson(dayRetofit);
                                                                                                        if (jsontoroom != null) {

                                                                                                            DayRoom dayRoom = new Gson().fromJson(jsontoroom, DayRoom.class);

                                                                                                            if (dayRoom != null) {
                                                                                                                Log.d(TAG, "onNext: dayroom" + dayRoom);
                                                                                                                weatherViewModel.insertDayDataRoom(WeatherActivity.this, dayRoom);
                                                                                                            }
                                                                                                        }
                                                                                                    }

                                                                                                }

                                                                                                @Override
                                                                                                public void onError(Throwable e) {

                                                                                                    Log.d(TAG, "onError: " + e.getMessage());
                                                                                                }

                                                                                                @Override
                                                                                                public void onComplete() {

                                                                                                }
                                                                                            });
//                                                                                    Log.d(TAG, "onNext: hourlyjsonroomobject" + hourlyRealmList);
//
//                                                                                    List<String> timeformat  = getTimeFormatList();
//                                                                                    Log.d(TAG, "onNext: "+timeformat);
//                                                                                    HourlyRecyclerAdapter hourlyRecyclerAdapter = new HourlyRecyclerAdapter(WeatherActivity.this, hourlyRealmList, C_or_F,timeformat);
//                                                                                    binding.hourlyrecyclerview.setLayoutManager(new LinearLayoutManager(WeatherActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                                                                                    binding.hourlyrecyclerview.setHasFixedSize(true);
//                                                                                    binding.hourlyrecyclerview.setAdapter(hourlyRecyclerAdapter);
//                                                                                    binding.hourlyrecyclerview.scrollToPosition(0);
//
//                                                                                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                                                                                        @Override
//                                                                                        public void execute(Realm realm) {
//
//                                                                                        realm.copyToRealmOrUpdate(hourlyRealmList);
//                                                                                         //   realm.commitTransaction();
//                                                                                        }
//                                                                                    });

//                                                                                weatherViewModel.getHourlyDataFromRoom(WeatherActivity.this, hourlyRoom)
//                                                                                        .observe(WeatherActivity.this, new Observer<List<HourlyRoom>>() {
//                                                                                            @Override
//                                                                                            public void onChanged(List<HourlyRoom> hourlyRooms) {
//
//                                                                                                HourlyRoom hourlyRoom1last = hourlyRooms.get(hourlyRooms.size() - 1);
//                                                                                                List<HourlyRoom.Data> hourlydata = hourlyRoom1last.getData();
//
//
//                                                                                                Log.d(TAG, "onChanged: hourly" + hourlydata.size());
//                                                                                                binding.setHourlydata(hourlydata);
//
//
//                                                                                            }
//                                                                                        });

                                                                                    //   Log.d(TAG, "onNext: data"+hourlyRoom.getData().size());


//


                                                                                }


//                                                                               Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                                                                                   @Override
//                                                                                   public void execute(Realm realm) {
//                                                                                     final   List<HourlyRoom.Data> hourlylist = hourlyRoom.getData();
//                                                                                      realm.copyToRealmOrUpdate(hourlylist)
//                                                                                   }
//                                                                               });


                                                                                //}


                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onError(Throwable e) {

                                                                            Log.d(TAG, "onError: hurly" + e.getLocalizedMessage());
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

    private List<String> getTimeFormatList() {
        final List<String> timestring = new ArrayList<>();
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CurrentGeoLocation currentGeoLocation = realm.where(CurrentGeoLocation.class).findFirst();
                String time12 = currentGeoLocation.time_12;
                Log.d(TAG, "execute: time12" + time12);
                String[] split = time12.split(":");


                for (String s : split) {
                    Log.d(TAG, "execute: splitime" + s);
                }


                timestring.add(split[0]);

                int timevalue = Integer.parseInt(split[0]);
                for (int i = 0; i < 48; i++) {
                    timevalue++;
                    if (timevalue > 12) {
                        timevalue = 1;
                        timestring.add(String.valueOf(timevalue));
                    } else {

                        timestring.add(String.valueOf(timevalue));
                    }
                }

            }
        });

        return timestring;
    }

    public List<String> getDayNames(String today) {

        List<String> stringList = new ArrayList<>();

        String name = today.toLowerCase();
        switch (name) {


            case "sunday":
                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Monday");
                    stringList.add("Tuesday");
                    stringList.add("Wednesday");
                    stringList.add("Thursday");
                    stringList.add("Friday");
                    stringList.add("Saturday");
                    stringList.add("Sunday");
                }

                break;

            case "monday":
                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Tuesday");
                    stringList.add("Wednesday");
                    stringList.add("Thursday");
                    stringList.add("Friday");
                    stringList.add("Saturday");
                    stringList.add("Sunday");
                    stringList.add("Monday");
                }

                break;

            case "tuesday":
                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Wednesday");
                    stringList.add("Thursday");
                    stringList.add("Friday");
                    stringList.add("Saturday");
                    stringList.add("Sunday");
                    stringList.add("Monday");
                    stringList.add("Tuesday");
                }
                break;

            case "wednesday":
                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Thursday");
                    stringList.add("Friday");
                    stringList.add("Saturday");
                    stringList.add("Sunday");
                    stringList.add("Monday");
                    stringList.add("Tuesday");
                    stringList.add("Wednesday");
                }
                break;
            case "thursday":
                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Friday");
                    stringList.add("Saturday");
                    stringList.add("Sunday");
                    stringList.add("Monday");
                    stringList.add("Tuesday");
                    stringList.add("Wednesday");
                    stringList.add("Thursday");
                }
                break;
            case "friday":
                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Saturday");
                    stringList.add("Sunday");
                    stringList.add("Monday");
                    stringList.add("Tuesday");
                    stringList.add("Wednesday");
                    stringList.add("Thursday");
                    stringList.add("Friday");
                }
                break;
            case "saturday":

                for (int i = 0; i <= 8; i++) {
                    if (i == 0) {
                        stringList.add("Today");
                    }
                    stringList.add("Sunday");
                    stringList.add("Monday");
                    stringList.add("Tuesday");
                    stringList.add("Wednesday");
                    stringList.add("Thursday");
                    stringList.add("Friday");
                    stringList.add("Saturday");
                }
                break;
        }

        return stringList;
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


    public void observerHourlyInsertion() {

        weatherViewModel.getHourlyList().observe(WeatherActivity.this, new Observer<List<HourlyDataRoomDB>>() {
            @Override
            public void onChanged(final List<HourlyDataRoomDB> hourlyDataRoomDBS) {

                if (hourlyDataRoomDBS != null) {
                    Log.d(TAG, "onChanged: hourly" + hourlyDataRoomDBS);
                    final List<String> timeformatdtrings = getTimeFormatList();


                    Type typeOfObjectsList = new TypeToken<List<RealmHourlyData>>() {
                    }.getType();

                    String realmHourlyData = new Gson().toJson(hourlyDataRoomDBS.get(hourlyDataRoomDBS.size() - 1).getData());
                    final List<RealmHourlyData> realmdatalist = new Gson().fromJson(realmHourlyData, typeOfObjectsList);

                    final RealmHourly realmHourly = new RealmHourly();

                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            realmHourly.setIcon(hourlyDataRoomDBS.get(hourlyDataRoomDBS.size() - 1).getIcon());
                            realmHourly.setSumary(hourlyDataRoomDBS.get(hourlyDataRoomDBS.size() - 1).getSumary());
                            realmHourly.setRealmHourlyDataRealmList(realmdatalist);
                            realmHourly.setStringRealmList(timeformatdtrings);




                                if(realm.where(RealmHourly.class).count()>0){
                                    while (!(realm.where(RealmHourly.class).count()==0)){


                                        realm.where(RealmHourly.class).findFirst().deleteFromRealm();
                                    }
                                }else if(realm.where(RealmHourly.class).count() == 0){
                                    realm.insertOrUpdate(realmHourly);
                                }





                            Log.d(TAG, "execute: realm" + realm.where(RealmHourly.class).count());

                        }
                    });


                    Collections.reverse(realmHourly.getStringRealmList());
                    HourlyRecyclerAdapter hourlyRecyclerAdapter = new HourlyRecyclerAdapter(WeatherActivity.this, realmHourly.getRealmHourlyDataRealmList(), C_or_F, realmHourly.getStringRealmList());
                    binding.hourlyrecyclerview.setLayoutManager(new LinearLayoutManager(WeatherActivity.this, LinearLayoutManager.HORIZONTAL, true));
                    binding.hourlyrecyclerview.setHasFixedSize(true);
                    binding.hourlyrecyclerview.setAdapter(hourlyRecyclerAdapter);
                    binding.hourlyrecyclerview.scrollToPosition(0);


                }

            }
        });
    }

    public void observeDayImsertion() {

        weatherViewModel.getDayRoom().observe(WeatherActivity.this, new Observer<List<DayRoom>>() {
            @Override
            public void onChanged(List<DayRoom> dayRooms) {
                if (dayRooms != null) {

                    int pos = dayRooms.size() - 1;
                    final DayRoom dayRoom = dayRooms.get(pos);
                    if (dayRoom != null) {
                        Log.d(TAG, "onChanged: day" + dayRoom);


                        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                String dayroomstring = new Gson().toJson(dayRoom);
                                DayRealm dayRealm = new Gson().fromJson(dayroomstring, DayRealm.class);



                                CurretWeatherDataRealm curretWeatherDataRealm = realm.where(CurretWeatherDataRealm.class).findFirst();
                                List<String> stringlist = getDayNames(curretWeatherDataRealm.getMonth());
//                                Collections.reverse(stringlist);
                                dayRealm.setStringdaylist(stringlist);

                                Log.d(TAG, "execute: dayrealm " + realm.where(DayRealm.class).count());




                                List<DayDataRealm> dayDataRealmList = new ArrayList<>();
                                if(dayRealm.getData().size() > 8){

                                    for(int i=0 ; i < dayRealm.getData().size() ; i++){

                                        if(i==8){
                                            break;
                                        }
                                     dayDataRealmList.add(dayRealm.getData().get(i));
                                        Collections.reverse(dayDataRealmList);




                                    }
                                }else{

                                }

                                realm.where(DayRealm.class).findAll().deleteAllFromRealm();
                                if(realm.where(DayRealm.class).count() == 0){
                                    realm.insertOrUpdate(dayRealm);
                                }else{

                                    while (!(realm.where(DayRealm.class).count()==0)){
                                        realm.where(DayRealm.class).findAll().deleteAllFromRealm();
                                    }
                                    realm.insertOrUpdate(dayRealm);
                                }
                                DayRecyclerAdapter dayRecyclerAdapter = new DayRecyclerAdapter(WeatherActivity.this,dayRealm.getData(),dayRealm.getStringdaylist(),C_or_F,binding);
                                binding.dayrecyclerview.setLayoutManager(new LinearLayoutManager(WeatherActivity.this,LinearLayoutManager.VERTICAL,true));
                                binding.dayrecyclerview.setHasFixedSize(true);
                                binding.dayrecyclerview.setAdapter(dayRecyclerAdapter);


                            }
                        });
                    }
                }
            }
        });

    }

    public double celsiusToFahrenhiet(double calcius) {

        return (calcius * 1.8) + 32;
    }

    public Integer fahrenhietToCelsius(double fahrenhiet) {
        BigDecimal bigDecimal = new BigDecimal(((fahrenhiet - 32) / 18) * 10);


        return bigDecimal.intValue();
    }


}




