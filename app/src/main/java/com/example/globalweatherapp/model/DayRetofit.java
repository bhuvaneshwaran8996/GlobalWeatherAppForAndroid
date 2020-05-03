package com.example.globalweatherapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DayRetofit implements Serializable {


    public DayRetofit(){

    }

    private List<DayDataRetrofit> data = new ArrayList<>();

    public List<DayDataRetrofit> getDayDataRetrofitList() {
        return data;
    }

    public void setDayDataRetrofitList(List<DayDataRetrofit> data) {
        this.data = data;
    }

    private String sumary;



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
        return "ClassPojo [data = "+data+", sumary = "+sumary+"]";
    }



}
