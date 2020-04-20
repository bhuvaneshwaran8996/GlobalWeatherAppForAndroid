package com.example.globalweatherapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;


@Database(entities = {PlacesRoom.class,HourlyRoom.class},  version = 23, exportSchema = false)
public abstract class PlacesDataBase extends RoomDatabase {
    public static String DB_NAME = "places_db";

    public static PlacesDataBase placesDataBase;


    public static synchronized PlacesDataBase getPlacesDataBase(Context context) {

        placesDataBase = Room.databaseBuilder(context, PlacesDataBase.class, DB_NAME)
                .fallbackToDestructiveMigration().build();
        return placesDataBase;
    }

    public abstract PlacesDao placesDao();

}
