package com.example.globalweatherapp.converters;

import androidx.room.TypeConverter;

import com.example.globalweatherapp.model.Data;
import com.example.globalweatherapp.model.DayDataRoom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DayDataConverters {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<DayDataRoom> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DayDataRoom>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<DayDataRoom> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DayDataRoom>>() {
        }.getType();
        List<DayDataRoom> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}
