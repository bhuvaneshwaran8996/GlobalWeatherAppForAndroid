package com.example.globalweatherapp.db;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update
    void update(PlacesRoom placeDetails);

    @Delete
    void delete(PlacesRoom placeDetails);

    @Query("DELETE FROM City")
    void deleteAll();
}
