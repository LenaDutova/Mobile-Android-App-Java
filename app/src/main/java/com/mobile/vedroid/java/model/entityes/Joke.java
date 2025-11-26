package com.mobile.vedroid.java.model.entityes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mobile.vedroid.java.model.JokeAdapterModel;

@Entity (tableName = "jokes")
public class Joke
        implements JokeAdapterModel {

    @PrimaryKey
    private final int id;
    private final boolean isSingle;
    private final String setup;
    private final String delivery;

    public Joke(int id, boolean isSingle, String setup, String delivery) {
        this.id = id;
        this.isSingle = isSingle;
        this.setup = setup;
        this.delivery = delivery;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isSingle() {
        return isSingle;
    }

    @Override
    public String getSetup() {
        return setup;
    }

    @Override
    public String getDelivery() {
        return delivery;
    }

}
