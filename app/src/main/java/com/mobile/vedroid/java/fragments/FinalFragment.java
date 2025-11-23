package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.activity.SingleActivity;
import com.mobile.vedroid.java.adapter.JokesAdapter;
import com.mobile.vedroid.java.databinding.FragmentFinalBinding;
import com.mobile.vedroid.java.model.ApiJoke;
import com.mobile.vedroid.java.model.DenoJoke;
import com.mobile.vedroid.java.model.JokeModelAdapter;
import com.mobile.vedroid.java.network.NetworkUtils;
import com.mobile.vedroid.java.network.RetrofitClient;
import com.mobile.vedroid.java.storage.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalFragment
        extends DebuggingFragment {

    private final static boolean DENO_OR_API_JOKES = true;

    private FragmentFinalBinding fragmentBinding;
    private SwipeRefreshLayout swipeRefreshLayout;
    private JokesAdapter adapter;

    private FileManager fileManager = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentBinding = FragmentFinalBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        debugging("HI");

        this.swipeRefreshLayout = fragmentBinding.swipeToRefresh;
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                debugging("Swiped to refreshing");
                loadJokes();
            }
        });

        adapter = new JokesAdapter();
        RecyclerView recyclerView = fragmentBinding.messagesRecyclerView;
        recyclerView.setAdapter(adapter);

        this.fileManager = new FileManager();
        if (fileManager.checkBuckUpExists()){
            // load jokes from file
            fileManager.readFromBuckUp();
            loadJokes(); // can not parse them yet :(
        } else loadJokes();
    }

    private void checkPlaceholder(){
        fragmentBinding.messagesPlaceholder.setVisibility( (adapter.getItemCount() > 0) ? View.GONE : View.VISIBLE);
    }

    private void loadJokes(){
        swipeRefreshLayout.setRefreshing(true);

        if (!NetworkUtils.isOnline(getActivity()))
            ((SingleActivity)getActivity()).showSnackBar(getString(R.string.warning_text_no_internet));
        else {
            if (DENO_OR_API_JOKES) loadDenoJokes();
            else loadApiJokes();
        }
    }

    private void showJokes(ArrayList<JokeModelAdapter> jokes){
        List <JokeModelAdapter> newItems = adapter.addItems(jokes);
        if (newItems.size() > 0 ) {
            debugging("Load " + newItems.size() + " elements");
            checkPlaceholder();
        }

        //store jokes
        try {
            fileManager.writeToBuckUp((ArrayList) newItems);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

                    showJokes((ArrayList) response.body());
                }else debugging ("Error: " + response.code());
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

                    showJokes((ArrayList) response.body().jokes);
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
