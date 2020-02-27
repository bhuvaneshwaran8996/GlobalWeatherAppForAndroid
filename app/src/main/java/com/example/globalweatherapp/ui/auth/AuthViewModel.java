package com.example.globalweatherapp.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.Repository.AuthRepository;
import com.example.globalweatherapp.model.CheckDevice;
import com.example.globalweatherapp.network.AuthApi;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {


    private static final String TAG = "AuthViewModel";
    //injecting authapi into viewmodel
    public AuthRepository authRepository;
    MediatorLiveData<String> authcheckdevice = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;

    }

    public void setDevice(String deviceId){

        this.authRepository.setDeviceId(deviceId);

    }


    public LiveData<String> getDevice(){
        return this.authRepository.getCheckDevice();
    }

//    public void setDeviceId(String id){
//        final LiveData<String> source = LiveDataReactiveStreams
//                .fromPublisher(authApi.checkDevice(new CheckDevice(id))
//                        .onErrorReturn(new Function<Throwable, String>() {
//                            @Override
//                            public String apply(Throwable throwable) throws Exception {
//                                Log.d(TAG, "apply: "+throwable.getMessage());
//
//                                return null;
//                            }
//                        })
//                        .subscribeOn(Schedulers.io()));
//
//        authcheckdevice.addSource(source, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                authcheckdevice.setValue(s);
//                authcheckdevice.removeSource(source);
//            }
//        });
//    }
//
//    public LiveData<String> getCheckDevice(){
//        return  authcheckdevice;
//    }
}
