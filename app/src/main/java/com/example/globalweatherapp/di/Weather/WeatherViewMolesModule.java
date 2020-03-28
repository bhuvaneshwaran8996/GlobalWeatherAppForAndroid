package com.example.globalweatherapp.di.Weather;

import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.di.ViewModelKey;
import com.example.globalweatherapp.viewmodels.WeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class WeatherViewMolesModule {




    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    public abstract ViewModel bindauthmodule(WeatherViewModel authViewModule);

}
