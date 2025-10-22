package com.mobile.vedroid.java.network;

import com.mobile.vedroid.java.model.ApiJoke;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
doc: https://jokeapi.dev/
examples:
https://v2.jokeapi.dev/joke/Programming?amount=10
https://v2.jokeapi.dev/joke/Programming?type=single&amount=10
https://v2.jokeapi.dev/joke/Programming?type=twopart&amount=10
 */
public interface JokeApiService {
    String URL_JOKEAPI = "https://v2.jokeapi.dev/";

    @GET("joke/Programming?amount=10")
    Call<ApiJoke.ApiJokesList> getJokes();

    @GET("joke/Programming?amount={count}")
    Call<ApiJoke.ApiJokesList> getJokes (@Path("count") int count);

    @GET("joke/Programming?type=single&amount={count}")
    Call<ApiJoke.ApiJokesList> getSingleJokes (@Path("count") int count);

    @GET("joke/Programming?type=twopart&amount={count}")
    Call<ApiJoke.ApiJokesList> getTwopartJokes (@Path("count") int count);
}
