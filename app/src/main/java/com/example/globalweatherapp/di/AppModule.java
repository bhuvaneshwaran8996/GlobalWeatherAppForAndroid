package com.example.globalweatherapp.di;


import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.R;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public  class AppModule {

    @Provides
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(Constants.LOCAL_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    static RequestOptions provideRequestionOption(Application application){
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);
    }

    @Provides
    static RequestManager provideRequestManager(Application application , RequestOptions requestoption){
        return Glide.with(application).setDefaultRequestOptions(requestoption);
    }

    @Provides
    static Drawable provideSplashImage(Application application){
        return ContextCompat.getDrawable(application,R.drawable.main_cloudysun);
    }
}
