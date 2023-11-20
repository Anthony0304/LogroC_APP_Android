package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private ListView listViewMovies;
    private SearchView searchView;
    private List<Movie> originalMovieList;
    private List<Movie> displayedMovieList;
    private static final String TAG = "MovieListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Inicializar la vista
        listViewMovies = findViewById(R.id.listViewMovies);
        searchView = findViewById(R.id.searchView);

        // Obtener la lista de películas desde los extras del intent
        originalMovieList = getIntent().getParcelableArrayListExtra("movieList");

        if (originalMovieList != null) {
            // Inicializar la lista que se mostrará (inicialmente igual a la original)
            displayedMovieList = new ArrayList<>(originalMovieList);

            // Mostrar la lista de películas en el ListView
            showMovieList(displayedMovieList);
        } else {
            // Manejar el caso cuando la lista de películas es nula
            // Puedes mostrar un mensaje o realizar alguna otra acción
            Log.e(TAG, "La lista de películas es nula");
        }

        // Configurar el listener para la barra de búsqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No es necesario realizar ninguna acción al enviar la consulta
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrar la lista de películas en función del texto de búsqueda
                filterMovieList(newText);
                return true;
            }
        });

        // Configurar el listener para hacer clic en un elemento de la lista
        listViewMovies.setOnItemClickListener((parent, view, position, id) -> {
            // Obtiene la película seleccionada
            Movie selectedMovie = displayedMovieList.get(position);

            // Inicia la actividad de detalles y pasa la película seleccionada
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
                    // Mostrar también el poster_path
                    String movieInfo = "ID: " + movie.getId() +
                            "\nTitle: " + movie.getTitle() +
                            "\nOverview: " + movie.getOverview() +
                            "\nRelease Date: " + movie.getReleaseDate() +
                            "\nPoster Path: " + movie.getPosterPath();


                    movieInfoList.add(movieInfo);
                }

                // Utilizar un ArrayAdapter para mostrar la lista de películas en el ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieInfoList);
                listViewMovies.setAdapter(adapter);
            } else {
                // Manejar el caso cuando la lista de películas es nula
                // Puedes mostrar un mensaje o realizar alguna otra acción
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
                // Si la consulta está vacía, mostrar la lista original completa
                displayedMovieList.addAll(originalMovieList);
            } else {
                // Filtrar la lista original en función de la consulta
                for (Movie movie : originalMovieList) {
                    // Verificar si la consulta coincide con el ID, nombre, overview, poster_path o release_date
                    if (String.valueOf(movie.getId()).contains(query) ||
                            movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getOverview().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getPosterPath().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getReleaseDate().toLowerCase().contains(query.toLowerCase())) {
                        displayedMovieList.add(movie);
                    }
                }
            }

            // Actualizar la lista mostrada en el ListVie
            showMovieList(displayedMovieList);
        }
    }
}
