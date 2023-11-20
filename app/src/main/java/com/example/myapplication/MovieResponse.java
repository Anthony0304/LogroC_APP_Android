package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
    public class SearchResponse {
        @SerializedName("results")
        private List<SearchResult> results;

        public List<SearchResult> getResults() {
            return results;
        }
    }

}

