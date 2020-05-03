package com.example.globalweatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.globalweatherapp.R;
import com.example.globalweatherapp.controls.TextViewRegular;
import com.example.globalweatherapp.converters.UnitsConverter;
import com.example.globalweatherapp.databinding.ActivityMainBinding;
import com.example.globalweatherapp.model.DayDataRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

import static android.view.View.GONE;

public class DayRecyclerAdapter extends RecyclerView.Adapter<DayRecyclerAdapter.DayRcyclerViewHolder> {


    public Context context;
    RealmList<DayDataRealm> dayDataRealmList = new RealmList<>();
    RealmList<String> stribglist = new RealmList<>();
    String c_or_f;
    RequestOptions requestOptions;
    ActivityMainBinding binding;

    public DayRecyclerAdapter(Context context, RealmList<DayDataRealm> dayDataRealmList, RealmList<String> stribglist, String c_or_f, ActivityMainBinding binding) {
        this.context = context;
        this.dayDataRealmList = dayDataRealmList;
        this.stribglist = stribglist;
        this.c_or_f = c_or_f;
        this.binding = binding;
        requestOptions = new RequestOptions();

    }

    @NonNull
    @Override
    public DayRecyclerAdapter.DayRcyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_inflate, null, true);

        return new DayRcyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DayRecyclerAdapter.DayRcyclerViewHolder holder, int position) {

        int icon;
        DayDataRealm dayDataRealm = dayDataRealmList.get(position);
        String daystring = stribglist.get(position);
        holder.day_summary.setText(dayDataRealm.getSummary());
        holder.dayname.setText(daystring);

        if (c_or_f.equalsIgnoreCase("C")) {

            holder.hourly_day_one.setText(String.valueOf(UnitsConverter.fahrenhietToCelsius(Double.parseDouble(dayDataRealm.getTemperaturehigh()))));
            holder.hourly_day_two.setText(String.valueOf(UnitsConverter.fahrenhietToCelsius(Double.parseDouble(dayDataRealm.getTemperaturehigh()))));
        } else {

            holder.hourly_day_one.setText(dayDataRealm.getTemperaturelow());
            holder.hourly_day_two.setText(dayDataRealm.getTemperaturehigh());
        }

        switch (dayDataRealm.getIcon()) {

            case "partly-cloudy-day":
                icon = R.mipmap.partly_cloudy_day;
                break;

            case "partly-cloudy-night":
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
                into(holder.day_icon);
        if (position == 0) {

            holder.bottomview.setVisibility(GONE);

        }else{
            holder.bottomview.setVisibility(View.VISIBLE);
            holder.bottomview.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) holder.bottomview.getLayoutParams();
                    layoutParams.width = binding.viewSeperator.getWidth();
                    holder.bottomview.setLayoutParams(layoutParams);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return dayDataRealmList.size();
    }


    public class DayRcyclerViewHolder extends RecyclerView.ViewHolder {

        public TextViewRegular dayname;
        public ImageView day_icon;
        public TextViewRegular day_summary;
        public TextViewRegular hourly_day_one;
        public TextViewRegular hourly_day_two;
        public View bottomview;

        public DayRcyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dayname = itemView.findViewById(R.id.dayname);
            bottomview = itemView.findViewById(R.id.bottomview);
            day_icon = itemView.findViewById(R.id.day_icon);
            hourly_day_two = itemView.findViewById(R.id.hourly_day_two);
            hourly_day_one = itemView.findViewById(R.id.hourly_day_one);

            day_summary = itemView.findViewById(R.id.day_summary);

        }
    }
}
