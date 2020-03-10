package com.example.globalweatherapp.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.CheckDevice;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.DeviceDetails;
import com.example.globalweatherapp.network.AuthApi;
import com.example.globalweatherapp.ui.auth.Weather.WeatherActivity;
import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;

/*

created by bhuvaneswaran muthuraja

 */
public class AuthActivity extends DaggerAppCompatActivity {

    @Inject
    Drawable logo;

    private static final String TAG = "AuthActivity";

    @Inject
    RequestManager requestManager;

    private AuthViewModel authViewModel;

    @Inject
    AuthApi authApi;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    ImageView imageView;


    ProgressBar loading;

    String m_androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        loading = findViewById(R.id.loading);
        imageView = findViewById(R.id.splashimage);
        requestManager.load(logo).into(imageView);
         m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d(TAG, "onCreate: "+m_androidId);
        RealmManager.open();


        authViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel.class);
        authViewModel.setDevice(m_androidId);
        subscribeobservers();


    }

    private void subscribeobservers() {


        authViewModel.getDevice().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s.equalsIgnoreCase("1")){ //device already exists, so login
                    login(m_androidId);
                    subscribeloginobserver();
                }else if(s.equalsIgnoreCase("0")){ //device does not exist, we need to signup here
                    signup(m_androidId);
                    subscribesignupobserver();
                }else{
                    Log.d(TAG, "onChanged: CheckDevice"+s);
                }
            }
        });
    //    authViewModel.getDevice().observe(this, new Observer<AuthResource<String>>() {
//            @Override
//            public void onChanged(AuthResource<String> authResource) {
//                if (authResource != null) {
//
//                    switch (authResource.status) {
//
//                        case LOADING:
//                            showProgressbar(true);
//                            Log.d("onchanged", "loading");
//                            break;
//                        case NOT_AUTHENTICATED:
//                            showProgressbar(false);
//                            Log.d("onchanged", "not authenticated");
////                            Toast.makeText(AuthActivity.this,authResource.data,Toast.LENGTH_LONG);
//                            break;
//                        case AUTHENTICATED:
//                            showProgressbar(true);
//                            Log.d("onchanged", authResource.data);
//                            if (String.valueOf(authResource.data).equalsIgnoreCase("1")) {
//                                login(String.valueOf("dfdfdfdfdfdf"));
//                                subscribeloginobserver();
//                            } else {
//                                //  signup();
//                            }
//
//                            //  Toast.makeText(AuthActivity.this,authResource.data,Toast.LENGTH_LONG);
//                            break;
//                        case ERROR:
//                            showProgressbar(false);
//                            //Toast.makeText(AuthActivity.this,authResource.message,Toast.LENGTH_LONG);
//                            break;
//
//                    }
//
//                }
//            }
 //       });


    }

    private void subscribesignupobserver() {
        authViewModel.getSavedDevice().observe(this, new Observer<AuthResource<Device>>() {
            @Override
            public void onChanged(AuthResource<Device> deviceAuthResource) {
                switch (deviceAuthResource.status){

                    case NOT_AUTHENTICATED:
                        showProgressbar(true);
                        Log.d(TAG, "onChanged: "+"not authenticated");
                        break;


                    case AUTHENTICATED:
                        showProgressbar(true);

                        Log.d(TAG, "onChanged: "+deviceAuthResource.data);

                        saveToDB(deviceAuthResource.data);
                        startActivity(new Intent(AuthActivity.this, WeatherActivity.class));

                        break;


                    case ERROR:
                        showProgressbar(true);

                        Log.d(TAG, "onChanged: "+deviceAuthResource.message);
                        break;

                    case LOADING:
                        showProgressbar(true);
                        Log.d(TAG, "onChanged: "+"loading");
                        break;
                }
            }
        });
    }


    public void subscribeloginobserver() {
        authViewModel.getLoginDEvice().observe(this, new Observer<AuthResource<Device>>() {
            @Override
            public void onChanged(AuthResource<Device > deviceDetailsAuthResource) {
                switch (deviceDetailsAuthResource.status) {

                    case LOADING:
                        showProgressbar(true);
                        Log.d(TAG, "onChanged: "+"loading");
                        break;

                    case ERROR:
                        showProgressbar(true);
                        Log.d(TAG, "onChanged: "+deviceDetailsAuthResource.message);
                        break;

                    case AUTHENTICATED:
                        showProgressbar(true);
                        Log.d(TAG, "onChanged: "+deviceDetailsAuthResource.data);

                        saveToDB(deviceDetailsAuthResource.data);
                        startActivity(new Intent(AuthActivity.this, WeatherActivity.class));
                        break;

                    case NOT_AUTHENTICATED:
                        showProgressbar(true);
                        Log.d(TAG, "onChanged: "+"not authenticated");
                        break;
                }
            }
        });
    }

    public void signup(String id){
        authViewModel.signupDevice(id);

    }

    private void login(String id) {


        authViewModel.loginDevice(id);
    }

    public void showProgressbar(boolean isvisilbe) {
        if (isvisilbe) {

            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(GONE);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }


    public void saveToDB(final Device device){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                RealmManager.createDeviceDao().save(device);

            }
        });
    }
}
