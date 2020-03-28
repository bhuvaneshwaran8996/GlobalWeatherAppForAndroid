package com.example.globalweatherapp.di.search;

import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.di.ViewModelKey;
import com.example.globalweatherapp.viewmodels.SearchViewModel;
import com.example.globalweatherapp.viewmodels.WeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class SearchViewModlesModule {



    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    public abstract ViewModel bindasearchmodule(SearchViewModel authViewModule);
}
