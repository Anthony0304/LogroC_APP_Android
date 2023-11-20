package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class SearchResult {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    // getters y setters
}
