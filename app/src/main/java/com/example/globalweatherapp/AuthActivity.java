package com.example.globalweatherapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.RequestManager;
import com.example.globalweatherapp.di.DaggerAppComponent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    @Inject
    Drawable logo;

    @Inject RequestManager requestManager;


    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        imageView = findViewById(R.id.splashimage);
        requestManager.load(logo).into(imageView);




    }

}
