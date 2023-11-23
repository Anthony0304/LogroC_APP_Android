package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        Movie selectedMovie = getIntent().getParcelableExtra("selectedMovie");


        if (selectedMovie != null) {

            setTitle(selectedMovie.getTitle());


            TextView overviewTextView = findViewById(R.id.textViewOverview);
            overviewTextView.setText(selectedMovie.getOverview());


            ImageView posterImageView = findViewById(R.id.imageViewPoster);
            String posterPath = selectedMovie.getPosterPath();
            if (posterPath != null && !posterPath.isEmpty()) {
                String imageUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
                Picasso.get().load(imageUrl).into(posterImageView);
            }


            TextView releaseDateTextView = findViewById(R.id.textViewReleaseDate);
            releaseDateTextView.setText("Release Date: " + selectedMovie.getReleaseDate());


            TextView revenueTextView = findViewById(R.id.textViewRevenue);
            revenueTextView.setText("Revenue: $" + selectedMovie.getRevenue());


            TextView budgetTextView = findViewById(R.id.textViewBudget);
            budgetTextView.setText("Budget: $" + selectedMovie.getBudget());


            TextView genresTextView = findViewById(R.id.textViewGenres);
            List<String> genres = selectedMovie.getGenres();
            if (genres != null && !genres.isEmpty()) {
                String genresString = TextUtils.join(", ", genres);
                genresTextView.setText("Genres: " + genresString);
            }
        }
    }
}
