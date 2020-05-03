package com.example.globalweatherapp.model;

import androidx.room.PrimaryKey;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmHourly extends RealmObject {

    @PrimaryKey
    private int id;

    public String icon;

    public String sumary;

    public String getSumary() {
        return sumary;
    }

    public RealmHourly(){}
    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public RealmList<RealmHourlyData> realmHourlyDataRealmList;

    public RealmList<String> getStringRealmList() {
        return stringRealmList;
    }

    public void setStringRealmList(List<String> stringRealmList) {
        this.stringRealmList = new RealmList<>();
        this.stringRealmList.addAll(stringRealmList);
    }

    public RealmList<String> stringRealmList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<RealmHourlyData> getRealmHourlyDataRealmList() {
        return realmHourlyDataRealmList;
    }

    public void setRealmHourlyDataRealmList(List<RealmHourlyData> realmHourlyData) {
        this.realmHourlyDataRealmList = new RealmList<>();
        this.realmHourlyDataRealmList.addAll(realmHourlyData);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
