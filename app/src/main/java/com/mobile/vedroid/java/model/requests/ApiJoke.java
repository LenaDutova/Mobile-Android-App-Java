package com.mobile.vedroid.java.model.requests;

import com.mobile.vedroid.java.model.JokeAdapterModel;

import java.util.ArrayList;
import java.util.Objects;

/*
 jokes: [
     {
        "category": "Programming",
        "type": "single",
        "joke": "Programming is 10% science, 20% ingenuity, and 70% getting the ingenuity to work with the science.",
        "flags": {
            "nsfw": false,
            "religious": false,
            "political": false,
            "racist": false,
            "sexist": false,
            "explicit": false
        },
        "id": 37,
        "safe": true,
        "lang": "en"
    },
    {
        "category": "Programming",
        "type": "twopart",
        "setup": "What is a dying programmer's last program?",
        "delivery": "Goodbye, world!",
        "flags": {
            "nsfw": false,
            "religious": false,
            "political": false,
            "racist": false,
            "sexist": false,
            "explicit": false
        },
        "id": 55,
        "safe": true,
        "lang": "en"
    }
]
 */
public class ApiJoke implements JokeAdapterModel {

    private final int id;
    private final String type;
    private final String joke;
    private final String setup;
    private final String delivery;

    public ApiJoke(int id, String type, String joke, String setup, String delivery) {
        this.id = id;
        this.type = type;
        this.joke = joke;
        this.setup = setup;
        this.delivery = delivery;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApiJoke apiJoke = (ApiJoke) o;
        return id == apiJoke.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // region // Pattern-Adapter

    public int getId() {
        return id;
    }

    public boolean isSingleJoke(){
        return type.equals("single");
    }

    public String getJokeSetup() {
        return isSingleJoke() ? joke : setup;
    }

    public String getJokeDelivery() {
        return delivery;
    }

    // endregion

    // region // inner class to parse param "jokes" into JSON-request

    public class ApiJokesList {
        public ArrayList<ApiJoke> jokes;
    }

    // endregion
}
