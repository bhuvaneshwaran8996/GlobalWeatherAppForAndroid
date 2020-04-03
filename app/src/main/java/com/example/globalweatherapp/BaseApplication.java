package com.example.globalweatherapp;


import androidx.room.Room;

import com.example.globalweatherapp.db.PlacesDataBase;
import com.example.globalweatherapp.di.DaggerAppComponent;


import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.DaggerAppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseApplication extends DaggerApplication {



    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);



    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this)
                .build();
    }
}
