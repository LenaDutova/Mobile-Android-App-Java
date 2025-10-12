package com.mobile.vedroid.java.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/*
{
    "id": 16,
    "type": "programming",
    "setup": "What's the object-oriented way to become wealthy?",
    "punchline": "Inheritance"
}
 */
public class DenoJoke {

    private final int id;
    private final String setup;
    @SerializedName("punchline")
    private final String delivery;

    public DenoJoke(int id, String setup, String delivery) {
        this.id = id;
        this.setup = setup;
        this.delivery = delivery;
    }

    public String getSetup() {
        return setup;
    }

    public String getDelivery() {
        return delivery;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DenoJoke denoJoke = (DenoJoke) o;
        return id == denoJoke.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
