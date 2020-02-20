package com.example.globalweatherapp.di.auth;


import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.di.ViewModelKey;
import com.example.globalweatherapp.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewmodlesModule {


    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindauthmodule(AuthViewModel authViewModule);

}
