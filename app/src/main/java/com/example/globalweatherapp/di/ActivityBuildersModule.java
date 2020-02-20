package com.example.globalweatherapp.di;

import com.example.globalweatherapp.AuthActivity;
import com.example.globalweatherapp.di.auth.AuthModule;
import com.example.globalweatherapp.di.auth.AuthViewmodlesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector (modules = {AuthViewmodlesModule.class, AuthModule.class})
    abstract AuthActivity authactivity();


}
