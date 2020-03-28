package com.example.globalweatherapp.di;

import com.example.globalweatherapp.di.Weather.WeatherModule;
import com.example.globalweatherapp.di.Weather.WeatherViewMolesModule;
import com.example.globalweatherapp.di.auth.AuthModule;
import com.example.globalweatherapp.di.auth.AuthViewmodlesModule;
import com.example.globalweatherapp.di.search.SearchModule;
import com.example.globalweatherapp.di.search.SearchViewModlesModule;
import com.example.globalweatherapp.ui.auth.AuthActivity;
import com.example.globalweatherapp.ui.auth.search.SearchActivity;
import com.example.globalweatherapp.ui.auth.Weather.WeatherActivity;
import com.example.globalweatherapp.viewmodels.SearchViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector (modules = {AuthViewmodlesModule.class, AuthModule.class})
    abstract AuthActivity authactivity();

    @ContributesAndroidInjector(modules = {WeatherViewMolesModule.class, WeatherModule.class})
    abstract WeatherActivity weatherActivity();

    @ContributesAndroidInjector(modules = {SearchViewModlesModule.class, SearchModule.class})
    abstract SearchActivity searchactivity();
}
