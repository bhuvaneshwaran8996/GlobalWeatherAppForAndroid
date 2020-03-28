package com.example.globalweatherapp.di.search;


import com.example.globalweatherapp.network.SearchApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class SearchModule {

    @Provides
    public static SearchApi provideSearchApi(Retrofit retrofit){
        return retrofit.create(SearchApi.class);
    }
}
