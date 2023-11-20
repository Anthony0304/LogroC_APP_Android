package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<TokenResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegistrationResponse> registerUser(@Body RegistrationRequest registrationRequest);
}
