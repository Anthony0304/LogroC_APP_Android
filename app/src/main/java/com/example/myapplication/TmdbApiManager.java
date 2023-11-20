package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbApiManager {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "43bb95cae941badc90476b9f10f04134"; // Tu clave de API

    private static TmdbApiService tmdbApiService;

    public static TmdbApiService getTmdbApiService() {
        if (tmdbApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbApiService = retrofit.create(TmdbApiService.class);
        }
        return tmdbApiService;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
