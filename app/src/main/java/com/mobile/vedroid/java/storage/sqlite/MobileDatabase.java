package com.mobile.vedroid.java.storage.sqlite;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mobile.vedroid.java.MobileApplication;
import com.mobile.vedroid.java.model.entityes.JokeConverters;
import com.mobile.vedroid.java.model.entityes.Joke;

@Database(entities = {Joke.class}, version = 1)
public abstract class MobileDatabase extends RoomDatabase {

    public abstract JokesDAO jokesDAO();

    private static MobileDatabase INSTANCE = null;
    public static synchronized MobileDatabase getInstance(){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(MobileApplication.mobileApplicationContext,
                    MobileDatabase.class, "DB").build();
        }
        return INSTANCE;
    }
}
