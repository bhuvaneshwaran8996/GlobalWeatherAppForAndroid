package com.example.globalweatherapp.ui.auth;

import androidx.annotation.Nullable;


/*
created by Bhuvaneswaran Muthuraja
 */
public class AuthResource<T> {

    @Nullable
    public final AuthStatus status;
    @Nullable
    public T data;
    @Nullable
    public String message;

    public AuthResource(@Nullable AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> AuthResource<T> error(String msg, T data) {
        return new AuthResource<>(AuthStatus.ERROR, data, msg);
    }


    public static <T> AuthResource<T> authenticated(T data) {
        return new AuthResource<>(AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> AuthResource<T> loading(T data) {
        return new AuthResource<>(AuthStatus.LOADING, data, null);
    }

    public static <T> AuthResource<T> logout() {
        return new AuthResource<>(AuthStatus.NOT_AUTHENTICATED, null, null);
    }


    public enum AuthStatus {
        AUTHENTICATED,
        ERROR,
        LOADING,
        NOT_AUTHENTICATED
    }
}
