package com.example.globalweatherapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.Repository.SearchRepository;
import com.example.globalweatherapp.model.PlaceDetails;
import com.example.globalweatherapp.model.PlacesRoom;
import com.example.globalweatherapp.network.SearchApi;

import java.util.List;


import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class SearchViewModel extends ViewModel {


    public  SearchRepository searchRepository;

    @Inject
    public SearchViewModel(SearchRepository searchRepository){

        this.searchRepository = searchRepository;
    }



    public Observable<Response<ResponseBody>> getPlacesDetails(String address){
        return this.searchRepository.getPlacesDetailsFromApi(address);

    }




}
