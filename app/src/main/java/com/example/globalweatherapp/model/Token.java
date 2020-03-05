package com.example.globalweatherapp.model;


import java.io.Serializable;

import javax.inject.Inject;

public class Token implements Serializable
{

    public String token;


    @Inject
    public Token( ) {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

