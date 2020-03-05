package com.example.globalweatherapp.di;

import android.app.Application;

import com.example.globalweatherapp.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;


@Component(
        modules = {AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModleFactoryModule.class

        }
)
@Singleton
public interface AppComponent extends AndroidInjector<BaseApplication> {


    @Component.Builder
    interface  Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}
