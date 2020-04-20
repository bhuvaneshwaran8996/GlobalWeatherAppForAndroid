package com.example.globalweatherapp.Repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.CheckDevice;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.DeviceDetails;
import com.example.globalweatherapp.model.Token;
import com.example.globalweatherapp.network.AuthApi;
import com.example.globalweatherapp.ui.auth.AuthActivity;
import com.example.globalweatherapp.ui.auth.AuthResource;
import com.example.globalweatherapp.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.logging.Handler;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AuthRepository {

    public MediatorLiveData<AuthResource<Device>> signupmediatorlivedata = new MediatorLiveData<>();
    public MediatorLiveData<String> authcheckdevice = new MediatorLiveData<>();
    public MediatorLiveData<AuthResource<Device>> devicedetailslivedata = new MediatorLiveData<>();
    private static final String TAG = "AuthRepository";
    public String error = "";
    public final AuthApi authApi;

    @Inject SharedPreferences sharedPreferences;

    @Inject
    Context context;

    Device device;

    @Inject
    Token token;
    @Inject
    public AuthRepository(AuthApi authApi) {

        this.authApi = authApi;


    }


    public void signupDevice(final String deviceId , Activity thisActivity) {


        signupmediatorlivedata.setValue(AuthResource.loading((Device) null));

        if (Util.isNetworkConnectionAvailable(thisActivity)) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {

                final    LiveData<AuthResource<Device>> livedatasourcesignup =
                        LiveDataReactiveStreams
                                .fromPublisher(authApi.saveDevice(new CheckDevice(deviceId))
                                        .onErrorReturn(new Function<Throwable, Response<ResponseBody>>() {
                                            @Override
                                            public Response<ResponseBody> apply(Throwable throwable) throws Exception {

                                                Log.d(TAG, "apply: signup"+throwable.getMessage());
                                                return null;
                                            }
                                        }).map(new Function<Response<ResponseBody>, AuthResource<Device>>() {
                                            @Override
                                            public AuthResource<Device> apply(Response<ResponseBody> responseBodyResponse) throws Exception {
                                                if(responseBodyResponse!=null && responseBodyResponse.isSuccessful()){
                                                    String inputjson = responseBodyResponse.body().string();
                                                    device = new Gson().fromJson(inputjson,Device.class);

                                                    Log.d(TAG, "apply: signup"+device.toString());
//                                                saveToDB(device);
                                                    return AuthResource.authenticated(device);
                                                }
                                                return AuthResource.error("device not saved",(Device)null);
                                            }
                                        })
                                        .subscribeOn(Schedulers.io()));

                signupmediatorlivedata.addSource(livedatasourcesignup, new Observer<AuthResource<Device>>() {
                    @Override
                    public void onChanged(AuthResource<Device> deviceAuthResource) {
                        signupmediatorlivedata.setValue(deviceAuthResource);
                        signupmediatorlivedata.removeSource(livedatasourcesignup);
                    }
                });
            }


        }else{
            Log.d(TAG, "signupDevice: "+"connection not available");
        }




    }
    public void logindevice(final String deviceId,Activity thisActivity) {

        devicedetailslivedata.setValue(AuthResource.loading((Device) null));

        if(Util.isNetworkConnectionAvailable(thisActivity)){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {


                final    LiveData<AuthResource<Device>> livedatalogindevice =
                        LiveDataReactiveStreams
                                .fromPublisher(authApi.login(new CheckDevice(deviceId))
                                        .onErrorReturn(new Function<Throwable, Response<ResponseBody>>() {
                                            @Override
                                            public Response<ResponseBody> apply(Throwable throwable) throws Exception {
                                                return null;
                                            }
                                        }).map(new Function<Response<ResponseBody>, AuthResource<Device>>() {
                                            @Override
                                            public AuthResource<Device> apply(Response<ResponseBody> responseBodyResponse) throws Exception {
                                                if(responseBodyResponse!=null && responseBodyResponse.isSuccessful()){
                                                    String inputjson = responseBodyResponse.body().string();
                                                    device = new Gson().fromJson(inputjson,Device.class);

//                                                 saveToDB(device);

                                                    Log.d(TAG, "apply: login "+device.toString());
                                                    return AuthResource.authenticated(device);
                                                }
                                                return AuthResource.error("not logegd in",(Device)null);
                                            }
                                        })
                                        .subscribeOn(Schedulers.io()));


//        final LiveData<AuthResource<JsonObject>> deviceDetailsLiveData = LiveDataReactiveStreams.fromPublisher(
//                authApi.login(new CheckDevice(deviceId))
//                .onErrorReturn(new Function<Throwable, JsonObject>() {
//                    @Override
//                    public JsonObject apply(Throwable throwable) throws Exception {
//                        Log.d(TAG, "apply: "+throwable.getMessage());
//
//
//                        return null;
//                    }
//                }).map(new Function<JsonObject, AuthResource<JsonObject>>() {
//                    @Override
//                    public AuthResource<JsonObject> apply(JsonObject device) throws Exception {
//                        Log.d(TAG, "apply: "+device);
//
//
//                        return AuthResource.authenticated(device);
//                    }
//                })
//        );

                devicedetailslivedata.addSource(livedatalogindevice, new Observer<AuthResource<Device>>() {
                    @Override
                    public void onChanged(AuthResource<Device> deviceDetailsAuthResource) {
                        devicedetailslivedata.setValue(deviceDetailsAuthResource);
                        devicedetailslivedata.removeSource(livedatalogindevice);
                    }
                });

            }
        }else{

            Log.d(TAG, "logindevice: "+"connectio not available");
        }


    }

    public void setDeviceId(String id , Activity thisActivity) {


        if(Util.isNetworkConnectionAvailable(thisActivity)) {


            final LiveData<String> source = LiveDataReactiveStreams
                    .fromPublisher(authApi.checkDevice(new CheckDevice(id))
                            .onErrorReturn(new Function<Throwable, String>() {
                                @Override
                                public String apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: " + throwable.getMessage());

                                    error = throwable.getMessage();
                                    return error;
                                }
                            })
                            .map(new Function<String, String>() {
                                @Override
                                public String apply(String s) throws Exception {
//                                if(!s.equalsIgnoreCase("0") || !s.equalsIgnoreCase("1")){
//
//                                    Log.d(TAG,s);
//                                    return AuthResource.error(s,(String)null);
//                                }
                                    if (!s.equalsIgnoreCase("0") && !s.equalsIgnoreCase("1")) {
                                        return s;//returns error message there
                                    }


                                    Log.d(TAG, "apply: " + s);


                                    return s;

                                }
                            })
                            .subscribeOn(Schedulers.io()));

            authcheckdevice.addSource(source, new Observer<String>() {
                @Override
                public void onChanged(String string) {
                    authcheckdevice.setValue(string);
                    authcheckdevice.removeSource(source);
                }
            });
        }else{
            Log.d(TAG, "setDeviceId: "+"network not available");
        }
    }


    public LiveData<String> getCheckDevice() {
        return authcheckdevice;

    }

    public LiveData<AuthResource<Device>> getSignedDevice(){

        return signupmediatorlivedata;
    }

    public LiveData<AuthResource<Device>> getDeviceDetails() {
        return devicedetailslivedata;
    }

//    public void saveToDB(Device device){
//        RealmManager.createDeviceDao().save(device);
//
//
//    }
}
