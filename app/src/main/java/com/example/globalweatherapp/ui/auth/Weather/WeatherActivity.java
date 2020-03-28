package com.example.globalweatherapp.ui.auth.Weather;

import android.content.Intent;
import android.os.Bundle;

import com.example.globalweatherapp.databinding.ActivityMainBinding;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.Device;
import com.example.globalweatherapp.ui.auth.search.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

public class WeatherActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    Device device;
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RealmManager.open();

        device = RealmManager.createDeviceDao().loadAll();
        Log.d(TAG, "onCreate: " + device);

        binding.mainLayout.search.setOnClickListener(new On_Cick());
    }



    public class On_Cick implements View.OnClickListener{


        @Override
        public void onClick(View v) {

            startActivity(new Intent(WeatherActivity.this, SearchActivity.class));


        }
    }

}
