package com.example.globalweatherapp.di;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


@Module
public  class AppModule {

    public static  String PREF_FILE_NAME = "UserDetails";

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        return new Retrofit.Builder().baseUrl(Constants.LOCAL_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }



    @Singleton
    @Provides
    static Token provideToken(){

        Token token = new Token();
        return token;

    }

    @Singleton
    @Provides
    static RequestOptions provideRequestionOption(Application application){
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);
    }

    @Singleton
    @Provides
    static RequestManager provideRequestManager(Application application , RequestOptions requestoption){
        return Glide.with(application).setDefaultRequestOptions(requestoption);
    }

    @Singleton
    @Provides
    static Drawable provideSplashImage(Application application){
        return ContextCompat.getDrawable(application,R.drawable.main_cloudysun);
    }

    @Singleton
    @Provides
   static Context ProvideContext(Application application){
        return application;
    }
    @Singleton
    @Provides
  static   SharedPreferences provideSharedPreference( Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    static Device provideDevice(){
        Device device = new Device();
        return device;
    }
}
