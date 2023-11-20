package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils; // Agrega esta importación
import java.util.List; // Agrega esta importación
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Obtén la información de la película seleccionada desde el Intent
        Movie selectedMovie = getIntent().getParcelableExtra("selectedMovie");

        // Muestra la información en las vistas correspondientes
        if (selectedMovie != null) {
            // Configura las vistas con los detalles de la película
            setTitle(selectedMovie.getTitle());

            // Configura el TextView con la descripción
            TextView overviewTextView = findViewById(R.id.textViewOverview);
            overviewTextView.setText(selectedMovie.getOverview());

            // Carga la imagen usando Picasso en un ImageView
            ImageView posterImageView = findViewById(R.id.imageViewPoster);
            String posterPath = selectedMovie.getPosterPath();
            if (posterPath != null && !posterPath.isEmpty()) {
                String imageUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
                Picasso.get().load(imageUrl).into(posterImageView);
            }

            // Configura el TextView con la fecha de lanzamiento
            TextView releaseDateTextView = findViewById(R.id.textViewReleaseDate);
            releaseDateTextView.setText("Release Date: " + selectedMovie.getReleaseDate());

            // Configura el TextView con los ingresos
            TextView revenueTextView = findViewById(R.id.textViewRevenue);
            revenueTextView.setText("Revenue: $" + selectedMovie.getRevenue());

            // Configura el TextView con el presupuesto
            TextView budgetTextView = findViewById(R.id.textViewBudget);
            budgetTextView.setText("Budget: $" + selectedMovie.getBudget());

            // Configura el TextView con los géneros
            TextView genresTextView = findViewById(R.id.textViewGenres);
            List<String> genres = selectedMovie.getGenres();
            if (genres != null && !genres.isEmpty()) {
                String genresString = TextUtils.join(", ", genres);
                genresTextView.setText("Genres: " + genresString);
            }
        }
    }
}
