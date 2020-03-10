package com.example.globalweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.Device;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Device device = RealmManager.createDeviceDao().loadAll();

        Log.d(TAG, "onCreate: "+device);
    }



}
