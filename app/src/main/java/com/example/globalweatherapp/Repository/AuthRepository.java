package com.example.globalweatherapp.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.globalweatherapp.model.CheckDevice;
import com.example.globalweatherapp.network.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthRepository {

    public MediatorLiveData<String> authcheckdevice = new MediatorLiveData<>();
    private static final String TAG = "AuthRepository";
    public final AuthApi authApi;
    @Inject
    public AuthRepository(AuthApi authApi){

        this.authApi = authApi;

    }
    public void setDeviceId(String id){
        final LiveData<String> source = LiveDataReactiveStreams
                .fromPublisher(authApi.checkDevice(new CheckDevice(id))
                        .onErrorReturn(new Function<Throwable, String>() {
                            @Override
                            public String apply(Throwable throwable) throws Exception {
                                Log.d(TAG, "apply: "+throwable.getMessage());

                                return null;
                            }
                        })
                        .subscribeOn(Schedulers.io()));

        authcheckdevice.addSource(source, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                authcheckdevice.setValue(s);
                authcheckdevice.removeSource(source);
            }
        });
    }

    public LiveData<String> getCheckDevice(){
        return  authcheckdevice;
    }
}
