package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private ListView listViewMovies;
    private SearchView searchView;
    private List<Movie> originalMovieList;
    private List<Movie> displayedMovieList;
    private static final String TAG = "MovieListActivity";
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listViewMovies = findViewById(R.id.listViewMovies);
        searchView = findViewById(R.id.searchView);
        TextView appNameTextView = findViewById(R.id.appNameTextView);

        // Inicializa tu ApiService utilizando ApiManager
        apiService = ApiManager.getApiService();

        originalMovieList = getIntent().getParcelableArrayListExtra("movieList");

        if (originalMovieList != null) {
            displayedMovieList = new ArrayList<>(originalMovieList);
            showMovieList(displayedMovieList);
        } else {
            Log.e(TAG, "La lista de películas es nula");
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovieList(newText);
                return true;
            }
        });

        listViewMovies.setOnItemClickListener((parent, view, position, id) -> {
            Movie selectedMovie = displayedMovieList.get(position);
            Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
            intent.putExtra("selectedMovie", selectedMovie);
            startActivity(intent);
        });
    }

    private void showMovieList(List<Movie> movieList) {
        try {
            if (movieList != null) {
                List<String> movieInfoList = new ArrayList<>();
                for (Movie movie : movieList) {
                    String movieInfo = "ID: " + movie.getId() +
                            "\nTitle: " + movie.getTitle() +
                            "\nOverview: " + movie.getOverview() +
                            "\nRelease Date: " + movie.getReleaseDate() +
                            "\nPoster Path: " + movie.getPosterPath();

                    movieInfoList.add(movieInfo);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieInfoList);
                listViewMovies.setAdapter(adapter);
            } else {
                Log.e(TAG, "La lista de películas es nula");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Excepción al mostrar la lista de películas: " + e.getMessage());
        }
    }

    private void filterMovieList(String query) {
        if (originalMovieList != null) {
            displayedMovieList.clear();

            if (TextUtils.isEmpty(query)) {
                displayedMovieList.addAll(originalMovieList);
            } else {
                for (Movie movie : originalMovieList) {
                    if (String.valueOf(movie.getId()).contains(query) ||
                            movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getOverview().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getPosterPath().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getReleaseDate().toLowerCase().contains(query.toLowerCase())) {
                        displayedMovieList.add(movie);
                    }
                }
            }
            showMovieList(displayedMovieList);
        }
    }
}
