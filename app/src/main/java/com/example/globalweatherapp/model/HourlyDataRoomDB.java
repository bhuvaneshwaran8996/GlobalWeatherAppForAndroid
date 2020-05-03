package com.example.globalweatherapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.globalweatherapp.converters.DataConverters;

import java.io.Serializable;
import java.util.List;



@Entity(tableName = "Hourly")
public class HourlyDataRoomDB  implements Serializable   //room model
{
    public HourlyDataRoomDB(){
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @TypeConverters(DataConverters.class)
    private List<Data> data;

    private String icon;

    private String sumary;

    public List<Data> getData ()
    {
        return data;
    }

    public void setData (List<Data> data)
    {
        this.data = data;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getSumary ()
    {
        return sumary;
    }

    public void setSumary (String sumary)
    {
        this.sumary = sumary;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", icon = "+icon+", sumary = "+sumary+"]";
    }



}

