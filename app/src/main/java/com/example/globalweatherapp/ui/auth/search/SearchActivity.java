package com.example.globalweatherapp.ui.auth.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.globalweatherapp.R;
import com.example.globalweatherapp.controls.TextViewBold;
import com.example.globalweatherapp.controls.TextViewMedium;
import com.example.globalweatherapp.controls.TextViewRegular;
import com.example.globalweatherapp.databinding.ActivitySearchBinding;
import com.example.globalweatherapp.db.PlacesDataBase;
import com.example.globalweatherapp.db.RealmManager;
import com.example.globalweatherapp.model.PlaceDetails;
import com.example.globalweatherapp.viewmodels.SearchViewModel;
import com.example.globalweatherapp.viewmodels.ViewModelProviderFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding3.widget.RxSearchView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.util.Log.d;
import static android.view.View.GONE;

public class SearchActivity extends DaggerAppCompatActivity {


    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    SearchViewModel searchViewModel;
    ActivitySearchBinding binding;
    CompositeDisposable disposable;
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.searchLayout.searchToolbar.setVisibility(View.VISIBLE);
        binding.searchLayout.firstToolbar.setVisibility(GONE);
        disposable = new CompositeDisposable();
        searchViewModel = ViewModelProviders.of(SearchActivity.this, viewModelProviderFactory).get(SearchViewModel.class);
        binding.searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        binding.searchList.setHasFixedSize(true);
        binding.searchLayout.locationsearch.setBackgroundColor(Color.TRANSPARENT);

        binding.searchLayout.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        RxSearchView.queryTextChanges(binding.searchLayout.locationsearch)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {

                        SearchAdapter searchAdapter = (SearchAdapter) binding.searchList.getAdapter();
                        if (charSequence.toString().isEmpty()) {

//                            if (searchAdapter != null) {
//                                searchAdapter.clear();
//                                binding.searchList.setVisibility(GONE);
//                            }


                            if (((SearchAdapter) binding.searchList.getAdapter()) != null) {
                                ((SearchAdapter) binding.searchList.getAdapter()).clear();
                            }

                            binding.searchList.setVisibility(GONE);

                            return false;

                        } else {
//                            binding.searchList.setVisibility(View.VISIBLE);
                            return true;
                        }
                    }
                })
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return charSequence.toString();
                    }
                })
                .debounce(10, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap(new Function<String, ObservableSource<Response<ResponseBody>>>() {
                    @Override
                    public ObservableSource<Response<ResponseBody>> apply(String s) throws Exception {
                        return searchViewModel.getPlacesDetails(s);
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.isSuccessful()) {
                            Log.d(TAG, "onNext: " + responseBodyResponse.body());
                            Type typeOfObjectsList = new TypeToken<List<PlaceDetails>>() {
                            }.getType();
                            try {
                                List<PlaceDetails> placesList = new Gson().fromJson(responseBodyResponse.body().string(), typeOfObjectsList);
                                if (placesList.size() > 0 && !binding.searchLayout.locationsearch.getQuery().toString().equalsIgnoreCase("")) {

                                    binding.searchList.setVisibility(View.VISIBLE);
                                    binding.searchList.setAdapter(new SearchAdapter(placesList));

                                } else {

                                    if (((SearchAdapter) binding.searchList.getAdapter()) != null) {
                                        ((SearchAdapter) binding.searchList.getAdapter()).clear();
                                    }

                                    binding.searchList.setVisibility(GONE);
                                }
                            } catch (IOException e) {
                                d(TAG, "onNext: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        d(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
        List<PlaceDetails> placeDetailsList;

        public SearchAdapter(List<PlaceDetails> placeDetailsList) {
            this.placeDetailsList = placeDetailsList;
        }

        @NonNull
        @Override
        public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_inflate, null, false);
            return new SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SearchAdapter.SearchViewHolder holder, final int position) {

            final PlaceDetails placeDetails = placeDetailsList.get(position);   //name - city , place - state

            holder.name.setText(placeDetails.name);
            holder.place.setText(placeDetails.place);
            holder.view_seperator.post(new Runnable() {
                @Override
                public void run() {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.view_seperator.getLayoutParams();
                    layoutParams.width = binding.viewSeperator.getWidth();
                    holder.view_seperator.setLayoutParams(layoutParams);
                }
            });
            holder.search_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlacesDataBase.placesDataBase.placesDao().insert(placeDetails);
                }
            });

        }

        public void clear() {
            this.placeDetailsList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return this.placeDetailsList.size();
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder {

            TextViewMedium name;
            TextViewRegular place;
            LinearLayout view_seperator;

            LinearLayout search_item;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                search_item = itemView.findViewById(R.id.search_item);
                name = itemView.findViewById(R.id.name);
                place = itemView.findViewById(R.id.place);
                view_seperator = itemView.findViewById(R.id.view_seperator_places);
            }

        }
    }
}
