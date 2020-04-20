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

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


@Module
public class AppModule {

    public static String PREF_FILE_NAME = "WeatherDetails";


    @Singleton
    @Provides
    static Retrofit provideRetrofit() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(httpLoggingInterceptor);

        return new Retrofit.Builder().baseUrl(Constants.LOCAL_URL)

                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

    }


    @Singleton
    @Provides
    static RequestOptions provideRequestionOption(Application application) {
        return RequestOptions.placeholderOf(R.drawable.main_cloudysun)
                .error(R.drawable.main_cloudysun);
    }

    @Singleton
    @Provides
    static RequestManager provideRequestManager(Application application, RequestOptions requestoption) {
        return Glide.with(application).setDefaultRequestOptions(requestoption);
    }

    @Singleton
    @Provides
    static Drawable provideSplashImage(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.main_cloudysun);
    }

    @Singleton
    @Provides
    static Context ProvideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    static SharedPreferences provideSharedPreference(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }


}
