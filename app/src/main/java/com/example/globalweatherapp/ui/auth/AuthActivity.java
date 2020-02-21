package com.example.globalweatherapp.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    @Inject
    Drawable logo;

    private static final String TAG = "AuthActivity";

    @Inject RequestManager requestManager;


    private  AuthViewModel authViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        imageView = findViewById(R.id.splashimage);
        requestManager.load(logo).into(imageView);
        authViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(AuthViewModel.class);
        authViewModel.setDeviceId("sdsdsdsd");
        subscribeobservers();



    }

    private void subscribeobservers() {
        
        authViewModel.getCheckDevice().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    Log.d(TAG, "onChanged: authactivty obserevd "+s);
                }
            }
        });
    }

}
