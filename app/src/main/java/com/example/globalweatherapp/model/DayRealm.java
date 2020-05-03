package com.example.globalweatherapp.model;

import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.globalweatherapp.converters.DayDataConverters;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DayRealm extends RealmObject {



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey
    private int id;
    private String sumary;


    private RealmList<String> stringdaylist = new RealmList<>();
    private RealmList<DayDataRealm> data = new RealmList<>();

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public RealmList<DayDataRealm> getData() {
        return data;
    }

    public void setStringdaylist(List<String> strlist){

            stringdaylist.clear();
            stringdaylist.addAll(strlist);
    }
    public RealmList<String> getStringdaylist(){
        return stringdaylist;
    }

    public void setData(List<DayDataRealm> data)
    {
        if(data==null){
            data = new RealmList<>();
        }else{
            data.clear();
        }

        this.data.addAll(data);
    }


  public DayRealm(){}
}
