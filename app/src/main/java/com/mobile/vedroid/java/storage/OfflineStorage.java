package com.mobile.vedroid.java.storage;

import com.mobile.vedroid.java.model.JokeAdapterModel;

import java.util.List;

public interface OfflineStorage {

    boolean isExists();
    List<JokeAdapterModel> load();
    void save (List<JokeAdapterModel> items);

}
