package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.ApiManager;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Inicializar Retrofit
        apiService = ApiManager.getApiService();

        // Configurar el evento clic del botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Código existente para iniciar sesión
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                LoginRequest loginRequest = new LoginRequest(email, password);
                loginUser(loginRequest);
            }
        });

        // Configurar el evento clic del botón de registro
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirigir a la actividad de registro al hacer clic en el botón de registro
                Intent intent = new Intent(LoginActivity.this, activity_register.class);

                startActivity(intent);
            }
        });
    }

    // Método para realizar el inicio de sesión
    private void loginUser(LoginRequest loginRequest) {
        Call<TokenResponse> loginCall = apiService.loginUser(loginRequest);
        loginCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getToken().getName();
                    saveToken(accessToken);

                    // Realizar la llamada a la API de TMDb después de un inicio de sesión exitoso
                    TmdbApiService tmdbApiService = TmdbApiManager.getTmdbApiService();
                    Call<MovieResponse> tmdbCall = tmdbApiService.getNowPlayingMovies("43bb95cae941badc90476b9f10f04134", "es-ES", 1);

                    tmdbCall.enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Movie> movies = response.body().getMovies();
                                // Haz algo con la lista de películas, por ejemplo, mostrarlas en una nueva actividad
                                showMoviesActivity(movies);
                            } else {
                                // Manejar errores de la respuesta de TMDb
                                Toast.makeText(LoginActivity.this, "Error al obtener la lista de películas", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {
                            // Manejar errores de la solicitud de TMDb
                            Toast.makeText(LoginActivity.this, "Error en la solicitud a TMDb", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("LoginActivity", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    // Método para redirigir a una nueva actividad y mostrar la lista de películas
    private void showMoviesActivity(List<Movie> movies) {
        for (Movie movie : movies) {
            Log.d("MovieList", "ID: " + movie.getId() +
                    ", Title: " + movie.getTitle() +
                    ", Overview: " + movie.getOverview() +
                    ", Release Date: " + movie.getReleaseDate());
        }

        Intent intent = new Intent(LoginActivity.this, MovieListActivity.class);
        intent.putParcelableArrayListExtra("movieList", new ArrayList<>(movies));
        startActivity(intent);
        finish();
    }

    // Método para guardar el token de acceso en SharedPreferences
    private void saveToken(String token) {
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("access_token", token);
        editor.apply();
    }
}
