package com.example.globalweatherapp.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.globalweatherapp.model.PlaceDetails;

import java.util.List;

@Dao
public interface PlacesDao {

    @Query("SELECT * FROM City")
    List<PlaceDetails> getPlacesDetails();

    @Insert
    void insert(PlaceDetails placeDetails);

    @Update
    void update(PlaceDetails placeDetails);

    @Delete
    void delete(PlaceDetails placeDetails);
}
