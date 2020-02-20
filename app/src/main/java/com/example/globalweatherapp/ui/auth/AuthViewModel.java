package com.example.globalweatherapp.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.globalweatherapp.network.AuthApi;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    public final AuthApi authApi;
    private static final String TAG = "AuthViewModel";
//injecting authapi into viewmodel
    @Inject
    public AuthViewModel(AuthApi authApi){



        this.authApi = authApi;
        Log.d(TAG, "AuthViewModel: ");
        
    }
}
