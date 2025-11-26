package com.mobile.vedroid.java.storage;

import com.mobile.vedroid.java.model.JokeAdapterModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface OfflineStorage {

    Flowable<List<JokeAdapterModel>> load();
    Completable save (List<JokeAdapterModel> items);

}
