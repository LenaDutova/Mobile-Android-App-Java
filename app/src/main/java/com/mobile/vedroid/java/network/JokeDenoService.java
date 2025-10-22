package com.mobile.vedroid.java.network;

import com.mobile.vedroid.java.model.DenoJoke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
doc: https://github.com/UltiRequiem/joke-api
examples: https://joke.deno.dev/type/programming/10
 */
public interface JokeDenoService {
    String URL_DENO = "https://joke.deno.dev/";

    @GET("type/programming/10")
    Call<List<DenoJoke>> getJokes();

    @GET("type/programming/{count}")
    Call<List<DenoJoke>> getJokes (@Path("count") int count);
}
