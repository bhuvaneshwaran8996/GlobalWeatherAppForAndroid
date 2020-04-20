package com.example.globalweatherapp.converters;

import androidx.room.TypeConverter;

import com.example.globalweatherapp.model.HourlyRoom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverters implements Serializable {


    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<HourlyRoom.Data> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<HourlyRoom.Data>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<HourlyRoom.Data> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<HourlyRoom.Data>>() {
        }.getType();
        List<HourlyRoom.Data> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
