package com.example.globalweatherapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.controls.TextViewMedium;
import com.example.globalweatherapp.controls.TextViewRegular;
import com.example.globalweatherapp.converters.DataConverters;
import com.example.globalweatherapp.converters.UnitsConverter;
import com.example.globalweatherapp.model.Data;
import com.example.globalweatherapp.model.HourlyRealm;
import com.example.globalweatherapp.model.HourlyRoom;
import com.example.globalweatherapp.model.RealmHourly;
import com.example.globalweatherapp.model.RealmHourlyData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;


public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.Hourlyholder> {


    Context context;
    private static final String TAG = "HourlyRecyclerAdapter";

    SharedPreferences sharedPreferences;
    List<String> timeformat = new ArrayList<>();
    String C_or_F;
    RealmList<RealmHourlyData> realmdatalist = new RealmList<>();
    RealmHourly realmHourly;
    RequestOptions requestOptions ;
    public HourlyRecyclerAdapter(Context context, RealmList<RealmHourlyData> realmdatalist, String C_or_F, List<String> timeformat){
        this.context = context;

        this.C_or_F = C_or_F;
        this.realmHourly = realmHourly;
        this.timeformat = timeformat;
        this.realmdatalist = realmdatalist;
        requestOptions = new RequestOptions();

    }

    @NonNull
    @Override
    public HourlyRecyclerAdapter.Hourlyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_inflate,null, false);

        return new Hourlyholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyRecyclerAdapter.Hourlyholder holder, int position) {


        int icon;
        RealmHourlyData data = realmdatalist.get(0);


        if(data!=null){
            Log.d(TAG, "onBindViewHolder: "+data);

        //    holder.hourly_label.setText(data.getTemperature());

            if(C_or_F.equalsIgnoreCase("C")){

                holder.hourly_label.setText(String.valueOf(UnitsConverter.fahrenhietToCelsius(Double.parseDouble(data.getTemperature()))));
            }else{

                holder.hourly_label.setText(data.getTemperature());

            }

            holder.hourly_time.setText(timeformat.get(position)+":00");

            switch (data.getIcon()) {

                case "partly-cloudy-day":
                    icon = R.mipmap.partly_cloudy_day;
                    break;

                case  "partly-cloudy-night":
                    icon = R.mipmap.partly_cloudy_night;
                    break;

                case "clear-day":
                    icon = R.mipmap.clear_day;
                    break;

                case "clear-night":
                    icon = R.mipmap.clear_night;
                    break;

                case "rain":
                    icon = R.mipmap.rain;
                    break;

                case "cloudy":
                    icon = R.mipmap.cloudy;
                    break;

                case "fog":
                    icon = R.mipmap.cloudy;
                    break;

                default:
                    icon = R.mipmap.cloudy;
                    break;

            }


            requestOptions.placeholder(R.mipmap.clear_night).error(R.mipmap.cloudy);



            Glide.with(context).setDefaultRequestOptions(requestOptions).load(icon).
                    into(holder.weather_icon);

        }else{
            Log.d(TAG, "onBindViewHolder: "+"null");
        }

    }

    public void updateList(List<RealmHourlyData> hourlyRoomList){
        this.realmdatalist.addAll(hourlyRoomList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return realmdatalist.size();
    }

    public class Hourlyholder extends RecyclerView.ViewHolder{

        TextViewRegular hourly_label;
        ImageView weather_icon;
        TextViewRegular hourly_time;

        public Hourlyholder(@NonNull View itemView) {
            super(itemView);
            hourly_label = itemView.findViewById(R.id.hourly_label);
            weather_icon = itemView.findViewById(R.id.weather_icon_hourly);
            hourly_time = itemView.findViewById(R.id.time_hourly);
        }
    }
}
