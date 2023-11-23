package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
