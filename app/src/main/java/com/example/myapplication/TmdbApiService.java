package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApiService {

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovies(  // Cambié el nombre del método a searchMovies
                                       @Query("api_key") String apiKey,
                                       @Query("language") String language,
                                       @Query("query") String query,
                                       @Query("page") int page,
                                       @Query("include_adult") boolean includeAdult
    );
}
