package com.mobile.vedroid.java.storage.sqlite;

import com.mobile.vedroid.java.model.JokeAdapterModel;
import com.mobile.vedroid.java.model.entityes.Joke;
import com.mobile.vedroid.java.model.entityes.JokeConverters;
import com.mobile.vedroid.java.storage.OfflineStorage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

/**
 * Need singleton instance of the database
 */
public class SQLiteManager
        implements OfflineStorage {

    private MobileDatabase db = MobileDatabase.getInstance();


    @Override
    public Flowable<List<JokeAdapterModel>> load() {
        return db.jokesDAO().getAll()
                .map(jokes -> {
                    List<JokeAdapterModel> items = new ArrayList<>();
                    for (Joke joke : jokes) {
                        items.add(joke);
                    }
                    return items;
                });
    }

    @Override
    public Completable save(List<JokeAdapterModel> models) {
        List<Joke> jokes = new ArrayList<>();
        for (JokeAdapterModel item : models) {
            jokes.add(JokeConverters.toJoke(item));
        }

        return db.jokesDAO().insertAll(jokes);
    }
}
