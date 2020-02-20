package com.example.globalweatherapp.di.auth;


import com.example.globalweatherapp.network.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @Provides
    static AuthApi provideauthapi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
