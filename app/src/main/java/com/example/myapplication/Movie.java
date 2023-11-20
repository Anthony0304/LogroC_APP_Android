package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private String revenue;
    private String budget;
    private String genres;

    public Movie(int id, String title, String overview, String posterPath, String releaseDate, String revenue, String budget, String genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = posterPath;
        this.release_date = releaseDate;
        this.revenue = revenue;
        this.budget = budget;
        this.genres = genres;
    }


    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        revenue = in.readString(); // Asegúrate de agregar esto en el orden correcto
        budget = in.readString();  // Asegúrate de agregar esto en el orden correcto
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getReleaseDate() {
        return release_date;
    }
    public String getRevenue() { return revenue;   }

    public String getBudget() { return budget;}
    public List<String> getGenres() {
        // implementación
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeString(revenue); // Agregar esta línea para escribir revenue en el Parcel
        dest.writeString(budget);

    }
}
