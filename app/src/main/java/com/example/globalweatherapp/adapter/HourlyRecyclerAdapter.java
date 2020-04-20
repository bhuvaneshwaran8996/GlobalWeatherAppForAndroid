package com.example.globalweatherapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.globalweatherapp.converters.DataConverters;
import com.example.globalweatherapp.converters.UnitsConverter;
import com.example.globalweatherapp.model.HourlyRoom;

import java.util.List;

import javax.inject.Inject;

public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.Hourlyholder> {


    Context context;
    @Inject
    SharedPreferences sharedPreferences;
    String C_or_F;
    List<HourlyRoom.Data> hourlyRoomList;
    RequestOptions requestOptions ;
    public HourlyRecyclerAdapter(Context context, List<HourlyRoom.Data> hourlyRoomList){
        this.context = context;
        this.hourlyRoomList = hourlyRoomList;
        requestOptions = new RequestOptions();
        if (sharedPreferences.getString("C_or_F", "F") != null && !sharedPreferences.getString("C_or_F", "F").equalsIgnoreCase("")) {
            C_or_F = sharedPreferences.getString("C_or_F", "F"); // the values should be in celsius or fahrenhiet
//            observabledegres.set(C_or_F);

        } else {
            sharedPreferences.edit().putString("C_or_F", "F").commit();
            C_or_F = "F";
//            observabledegres.set(C_or_F);
        }
    }

    @NonNull
    @Override
    public HourlyRecyclerAdapter.Hourlyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_inflate,null, false);
        return new Hourlyholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyRecyclerAdapter.Hourlyholder holder, int position) {


        String icon;
        HourlyRoom.Data data = hourlyRoomList.get(position);

        holder.weather_label.setText(data.getTemperature());

        if(C_or_F.equalsIgnoreCase("C")){

           holder.hourly_time.setText(String.valueOf(UnitsConverter.fahrenhietToCelsius(Double.parseDouble(data.getTemperature()))));
        }else{

            holder.hourly_time.setText(data.getTemperature());

        }
        holder.hourly_time.setText("8:00");

        switch (data.getIcon()) {

            case "partly-cloudy-day":
                icon = "partly-cloudy-day";

            case "partly-cloudy-night":
                icon = "partly-cloudy-night";

            case "clear-day":
                icon = "clear-day";

            case "clear-night":
                icon = "clear-night";

            case "rain":
                icon = "rain";

            case "cloudy":
                icon = "cloudy";

            case "fog":
                icon = "fog";

            default:
                icon = "cloudy";

        }


        requestOptions.placeholder(R.mipmap.cloudy).error(R.mipmap.cloudy);



        Glide.with(context).setDefaultRequestOptions(requestOptions).load(icon).
                into(holder.weather_icon);

    }

    @Override
    public int getItemCount() {
        return hourlyRoomList.size();
    }

    public class Hourlyholder extends RecyclerView.ViewHolder{

        TextViewMedium weather_label;
        ImageView weather_icon;
        TextViewMedium hourly_time;

        public Hourlyholder(@NonNull View itemView) {
            super(itemView);
            weather_label = itemView.findViewById(R.id.weatherlabel);
            weather_icon = itemView.findViewById(R.id.weather_icon_hourly);
            hourly_time = itemView.findViewById(R.id.time_hourly);
        }
    }
}
