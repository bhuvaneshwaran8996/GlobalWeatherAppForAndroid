package com.example.globalweatherapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.globalweatherapp.model.PlacesRoom;



@Database(entities = PlacesRoom.class,exportSchema = false,version = 1)
public abstract  class PlacesDataBase extends RoomDatabase {


    public static String DB_NAME = "places_db";
    public static PlacesDataBase placesDataBase;


    public static synchronized PlacesDataBase  getInstance(Context context){

        if(placesDataBase==null){

            placesDataBase = Room.databaseBuilder(context.getApplicationContext(),PlacesDataBase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return placesDataBase;
    }



    public abstract PlacesDao placesDao();
}
