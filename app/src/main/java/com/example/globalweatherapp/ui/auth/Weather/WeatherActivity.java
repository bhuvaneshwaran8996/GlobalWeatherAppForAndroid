package com.example.globalweatherapp.ui.auth.Weather;

import android.os.Bundle;

import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.model.Token;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import com.example.globalweatherapp.R;

import javax.inject.Inject;

public class WeatherActivity extends AppCompatActivity {


    @Inject
    Token token;

    @Inject
    Device device;
    private static final String TAG = "WeatherActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        Log.d(TAG, "onCreate:  "+device);
        Log.d(TAG, "onCreate: "+token);
    }

}
