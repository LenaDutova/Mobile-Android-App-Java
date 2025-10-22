package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.SingleActivity;
import com.mobile.vedroid.java.adapter.ExpandableAdapter;
import com.mobile.vedroid.java.adapter.MixedJokesAdapter;
import com.mobile.vedroid.java.adapter.TwoPartJokesAdapter;
import com.mobile.vedroid.java.databinding.FragmentFinalBinding;
import com.mobile.vedroid.java.model.ApiJoke;
import com.mobile.vedroid.java.model.DenoJoke;
import com.mobile.vedroid.java.network.NetworkUtils;
import com.mobile.vedroid.java.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalFragment
        extends DebuggingFragment {

    private final static boolean DENO_OR_API_JOKES = true;

    private FragmentFinalBinding fragmentBinding;
    private ExpandableAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        debugging("HI");
        this.fragmentBinding = FragmentFinalBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.swipeRefreshLayout = fragmentBinding.swipeToRefresh;
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                debugging("Swiped to refreshing");
                loadJokes();
            }
        });

        if (DENO_OR_API_JOKES) adapter = new TwoPartJokesAdapter();
        else adapter = new MixedJokesAdapter();
        loadJokes();

        RecyclerView recyclerView = fragmentBinding.messagesRecyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void checkPlaceholder(){
        TextView placeholder = fragmentBinding.messagesPlaceholder;
        if (adapter.getItemCount() > 0){
            placeholder.setVisibility(View.GONE);
        } else {
            placeholder.setVisibility(View.VISIBLE);
        }
    }

    private void loadJokes(){
        swipeRefreshLayout.setRefreshing(true);

        if (!NetworkUtils.isOnline(getActivity()))
            ((SingleActivity)getActivity()).showSnackBar(getString(R.string.text_no_internet));
        else {
            if (DENO_OR_API_JOKES) loadDenoJokes();
            else loadApiJokes();
        }
    }

    private void loadDenoJokes (){
        // enqueue() performs the HTTP request on a background thread
        // execute() should be called from a background thread
        RetrofitClient.getDenoService().getJokes().enqueue(new Callback<List<DenoJoke>>() {
            @Override
            public void onResponse(@NonNull Call<List<DenoJoke>> call, @NonNull Response<List<DenoJoke>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    debugging ("Success: " + response.code() + ", size: " + response.body().size());

                    int newItems = adapter.addItems((ArrayList) response.body());
                    if (newItems > 0 ) {
                        debugging("Load " + newItems + " elements");
                        checkPlaceholder();
                    }
                }else {
                    debugging ("Error: " + response.code());
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<DenoJoke>> call, @NonNull Throwable t) {
                debugging ("Error: " + t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadApiJokes() {
        RetrofitClient.getApiService().getJokes().enqueue(new Callback<ApiJoke.ApiJokesList>() {
            @Override
            public void onResponse(@NonNull Call<ApiJoke.ApiJokesList> call, @NonNull Response<ApiJoke.ApiJokesList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    debugging ("Success: " + response.code() + ", size: " + response.body().jokes.size());

                    int newItems;
                    newItems = adapter.addItems(response.body().jokes);
                    if (newItems > 0 ) {
                        debugging("Load " + newItems + " elements");
                        checkPlaceholder();
                    }
                } else debugging ("Error with code: " + response.code());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ApiJoke.ApiJokesList> call, @NonNull Throwable t) {
                debugging ("Error: " + t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
