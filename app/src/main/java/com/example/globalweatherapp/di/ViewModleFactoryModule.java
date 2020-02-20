package com.example.globalweatherapp.di;


import androidx.lifecycle.ViewModelProvider;

import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract  class ViewModleFactoryModule {


    @Binds
    public abstract ViewModelProvider.Factory bindviewmodelfactory (ViewModelProviderFactory viewModelProviderFactory);
}
