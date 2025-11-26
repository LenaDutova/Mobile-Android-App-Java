package com.mobile.vedroid.java.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.storage.OfflineStorage;
import com.mobile.vedroid.java.storage.sqlite.SQLiteManager;
import com.mobile.vedroid.java.ui.activity.SingleActivity;
import com.mobile.vedroid.java.ui.adapter.JokesAdapter;
import com.mobile.vedroid.java.databinding.FragmentFinalBinding;
import com.mobile.vedroid.java.model.requests.ApiJoke;
import com.mobile.vedroid.java.model.requests.DenoJoke;
import com.mobile.vedroid.java.model.JokeAdapterModel;
import com.mobile.vedroid.java.network.NetworkUtils;
import com.mobile.vedroid.java.network.RetrofitClient;
import com.mobile.vedroid.java.storage.FileManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalFragment
        extends DebuggingFragment {

    private final static boolean DENO_OR_API_JOKES = true;

    private FragmentFinalBinding fragmentBinding;
    private SwipeRefreshLayout swipeRefreshLayout;
    private JokesAdapter adapter;

    private OfflineStorage storage = null;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentBinding = FragmentFinalBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
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
                downloadJokes();
            }
        });

        adapter = new JokesAdapter();
        RecyclerView recyclerView = fragmentBinding.messagesRecyclerView;
        recyclerView.setAdapter(adapter);

        // Change storage vault
//        this.storage = new FileManager();
        this.storage = new SQLiteManager();

        disposables.add(storage.load()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                jokes -> {
                    if (jokes.isEmpty()){
                        debugging("No saved jokes");
                        downloadJokes();
                    } else {
                        debugging("Load jokes from storage: " + jokes.size());
                        adapter.addItems((ArrayList<JokeAdapterModel>) jokes);
                        checkPlaceholder();
                    }
                },
                throwable -> {
                    debugging("Something wrong");
                }
        ));
    }

    private void checkPlaceholder(){
        fragmentBinding.messagesPlaceholder.setVisibility( (adapter.getItemCount() > 0) ? View.GONE : View.VISIBLE);
    }

    private void downloadJokes(){
        swipeRefreshLayout.setRefreshing(true);

        if (!NetworkUtils.isOnline(getActivity()))
            ((SingleActivity)getActivity()).showSnackBar(getString(R.string.warning_text_no_internet));
        else {
            if (DENO_OR_API_JOKES) loadDenoJokes();
            else loadApiJokes();
        }
    }

    private void showJokes(ArrayList<JokeAdapterModel> jokes){
        List <JokeAdapterModel> newItems = adapter.addItems(jokes);
        if (!newItems.isEmpty() ) {
            debugging("Download " + newItems.size() + " jokes");
            checkPlaceholder();
        }

        //store jokes
        disposables.add(storage.save((ArrayList) newItems)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> debugging("Save new jokes")
                ));
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
