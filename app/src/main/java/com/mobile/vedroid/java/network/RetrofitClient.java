package com.mobile.vedroid.java.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static JokeApiService apiService = null;
    private static JokeDenoService denoService = null;

    private RetrofitClient(){}

    public static JokeApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JokeApiService.URL_JOKEAPI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(JokeApiService.class);
        }
        return apiService;
    }

    public static JokeDenoService getDenoService() {
        if (denoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JokeDenoService.URL_DENO)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            denoService = retrofit.create(JokeDenoService.class);
        }
        return denoService;
    }
}
