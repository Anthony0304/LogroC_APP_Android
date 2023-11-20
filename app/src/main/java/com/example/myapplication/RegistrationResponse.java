package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("access_token") // Asegúrate de que coincida con el nombre del campo en tu API
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
