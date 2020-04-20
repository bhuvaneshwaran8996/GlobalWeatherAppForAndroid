package com.example.globalweatherapp.binding;

import android.graphics.Paint;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalweatherapp.adapter.HourlyRecyclerAdapter;
import com.example.globalweatherapp.model.HourlyRoom;

import java.util.List;

public class RecyclerBinding {



//    @BindingAdapter("productlist")
//    public static void bindviewproduct(RecyclerView recyclerView , List<Product> productsList){
//
//        if(productsList == null){
//            return;
//        }
//
//        ViewProductAdapter viewProductAdapter = (ViewProductAdapter) recyclerView.getAdapter();
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//        if(layoutManager ==null){
//
//            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(),2));
//        }
//
//        if(viewProductAdapter == null) {
//            viewProductAdapter = new ViewProductAdapter(recyclerView.getContext(),productsList);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(viewProductAdapter);
//
//        }
//    }

    @BindingAdapter("hourlylist")
    public static void bindhourlydata(final RecyclerView recyclerView, List<HourlyRoom.Data> hourlyRoomsList){
        if(hourlyRoomsList == null){
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager==null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        }
        HourlyRecyclerAdapter hourlyRecyclerAdapter =(HourlyRecyclerAdapter) recyclerView.getAdapter();
        if(hourlyRecyclerAdapter==null){
            hourlyRecyclerAdapter = new HourlyRecyclerAdapter(recyclerView.getContext(),hourlyRoomsList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(hourlyRecyclerAdapter);

        }


    }

}
