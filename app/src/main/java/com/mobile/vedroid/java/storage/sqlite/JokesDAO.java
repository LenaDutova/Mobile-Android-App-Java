package com.mobile.vedroid.java.storage.sqlite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mobile.vedroid.java.model.entityes.Joke;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface JokesDAO {
    @Query("SELECT * FROM jokes")
    Flowable<List<Joke>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Joke> jokes);

    @Delete
    Completable  deleteAll(List<Joke> jokes);
}
