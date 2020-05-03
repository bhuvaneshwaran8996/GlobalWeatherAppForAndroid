package com.example.globalweatherapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.globalweatherapp.converters.DataConverters;
import com.example.globalweatherapp.converters.DayDataConverters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Day")
public class DayRoom {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String sumary;

    @TypeConverters(DayDataConverters.class)
    private List<DayDataRoom> data = new ArrayList<>();

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public List<DayDataRoom> getData() {
        return data;
    }

    public void setData(List<DayDataRoom> data) {
        this.data = data;
    }

    public DayRoom(String sumary, List<DayDataRoom> data) {
        this.sumary = sumary;
        this.data = data;
    }
}
