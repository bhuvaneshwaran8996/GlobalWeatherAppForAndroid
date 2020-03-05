package com.example.globalweatherapp.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.Repository.AuthRepository;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.DeviceDetails;
import com.google.gson.JsonObject;

import javax.inject.Inject;

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

    public LiveData<AuthResource<Device>> getLoginDEvice(){

        return  this.authRepository.getDeviceDetails();
    }

    public void loginDevice(String deviceId){

         this.authRepository.logindevice(deviceId);

    }

    public void signupDevice(String id){

         this.authRepository.signupDevice(id);
    }

    public LiveData<AuthResource<Device>> getSavedDevice(){
        return this.authRepository.getSignedDevice();
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
