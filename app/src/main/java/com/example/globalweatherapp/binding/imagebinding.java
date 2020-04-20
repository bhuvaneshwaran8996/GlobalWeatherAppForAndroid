package com.example.globalweatherapp.binding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.globalweatherapp.Constants;
import com.example.globalweatherapp.R;

import javax.inject.Inject;


public class imagebinding {


    private static final String TAG = "imagebinding";
    @Inject
    RequestOptions requestOptions;
    @Inject
    Drawable drawable;
    @Inject
    Context context;

    @BindingAdapter({"imageurl"})
    public static void setimage(ImageView view , int image){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.cloudy).error(R.mipmap.cloudy);

        Context context = view.getContext();

        Glide.with(context).setDefaultRequestOptions(requestOptions).load(image).

                into(view);
    }


    @BindingAdapter({"imageurl"})
    public static void setimage(ImageView view , String image){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round);

        Context context = view.getContext();

        Glide.with(context).setDefaultRequestOptions(requestOptions).load(image).

                into(view);
    }



}
