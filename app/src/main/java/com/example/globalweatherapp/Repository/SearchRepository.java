package com.example.globalweatherapp.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.globalweatherapp.databinding.ActivitySearchBinding;
import com.example.globalweatherapp.db.PlacesDataBase;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.PlaceDetails;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.network.SearchApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SearchRepository {



    Device device;
    SearchApi searchApi;
    @Inject
    public SearchRepository(SearchApi searchApi){

        this.searchApi = searchApi;
//         device = RealmManager.open().where(Device.class).findFirst();
    }





    public Observable<Response<ResponseBody>> getPlacesDetailsFromApi(String address){

        RealmManager.open().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                device = realm.where(Device.class).findFirst();
            }
        });
       return searchApi.getPlaces("Bearer "+device.token,address)
               .subscribeOn(Schedulers.io());

    }
}
