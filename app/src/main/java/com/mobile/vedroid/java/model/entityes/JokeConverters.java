package com.mobile.vedroid.java.model.entityes;

import com.mobile.vedroid.java.model.JokeAdapterModel;

public class JokeConverters {

    public static JokeAdapterModel fromJoke (Joke value){
        return value == null ? null : (JokeAdapterModel) value;
    }

    public static Joke toJoke (JokeAdapterModel value){
        return value == null ? null : new Joke(value.getId(), value.isSingle(), value.getSetup(), value.getDelivery());
    }
}
