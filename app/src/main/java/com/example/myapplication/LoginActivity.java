package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        apiService = ApiManager.getApiService();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                LoginRequest loginRequest = new LoginRequest(email, password);
                loginUser(loginRequest);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, activity_register.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(LoginRequest loginRequest) {
        Call<TokenResponse> loginCall = apiService.loginUser(loginRequest);
        loginCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Object tokenObject = response.body().getToken();
                    if (tokenObject != null) {
                        String accessToken;
                        if (tokenObject instanceof String) {
                            // Si el token es una cadena, asume que es directamente el token
                            accessToken = (String) tokenObject;
                        } else {
                            // Si el token es un objeto, asume que es un TokenData y extrae el nombre
                            accessToken = ((TokenData) tokenObject).getName();
                        }

                        if (!accessToken.isEmpty()) {
                            saveToken(accessToken);
                            Log.d("LoginActivity", "Token guardado: " + accessToken);
                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();

                            // Resto del código...
                            TmdbApiService tmdbApiService = TmdbApiManager.getTmdbApiService();
                            Call<MovieResponse> tmdbCall = tmdbApiService.getNowPlayingMovies("43bb95cae941badc90476b9f10f04134", "es-ES", 1);

                            tmdbCall.enqueue(new Callback<MovieResponse>() {
                                @Override
                                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        List<Movie> movies = response.body().getMovies();
                                        showMoviesActivity(movies);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Error al obtener la lista de películas", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MovieResponse> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Error en la solicitud a TMDb", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Token vacío", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Token nulo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("LoginActivity", "Error en el inicio de sesión. Código: " + response.code());
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

    private void saveToken(String token) {
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
        Log.d(TAG, "Token guardado correctamente: " + token);
    }
}