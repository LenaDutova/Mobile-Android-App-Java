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
public class DenoJoke implements JokeModelAdapter {

    private final int id;
    private final String setup;
    @SerializedName("punchline")
    private final String delivery;

    public DenoJoke(int id, String setup, String delivery) {
        this.id = id;
        this.setup = setup;
        this.delivery = delivery;
    }
    public DenoJoke() {}

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

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder(getClass().getSimpleName());
        txt.append("\nid:");
        txt.append(id);
        txt.append("\nsingle:false\nsetup:");
        txt.append(setup);
        txt.append("\ndelivery:");
        txt.append(delivery);
        txt.append("\n");

        return String.valueOf(txt);
    }
    // region // Pattern-Adapter

    public int getId() {
        return id;
    }

    @Override
    public boolean isSingleJoke() {
        return false;
    }

    @Override
    public String getJokeSetup() {
        return setup;
    }

    @Override
    public String getJokeDelivery() {
        return delivery;
    }

    // endregion
}
