package com.example.globalweatherapp.db;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.globalweatherapp.model.DayRoom;
import com.example.globalweatherapp.model.HourlyDataRoomDB;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.PlacesRoom;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PlacesDao {

    @Query("SELECT * FROM City")
    LiveData<List<PlacesRoom>> getPlacesDetails();

    @Insert(onConflict = REPLACE)
    void insert(PlacesRoom placeDetails);


//    @Query("SELECT * FROM HourlyData")
//   List<HourlyRoom> getHoyrlyDetails();

    @Query("SELECT * FROM HOURLY")
    LiveData<List<HourlyDataRoomDB>> getHourlyData();

    @Update
    void update(HourlyDataRoomDB hourlyDataRoomDB);

    @Insert(onConflict = REPLACE)
    void insert(HourlyDataRoomDB hourlyDataRoomDB);

//
//    @Insert
//    long insert(HourlyRoom hourlyRoom);
//
//    @Delete
//    void delete(HourlyRoom hourlyRoom);



    @Update
    void update(PlacesRoom placeDetails);

    @Delete
    void delete(PlacesRoom placeDetails);

    @Query("DELETE FROM City")
    void deleteAll();

    @Insert
    void insert(DayRoom dayRoom);

    @Query("SELECT * FROM Day")
     LiveData<List<DayRoom>> getDayRoomData();
}
