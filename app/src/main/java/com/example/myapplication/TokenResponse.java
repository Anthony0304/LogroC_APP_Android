package com.example.myapplication;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TokenResponse {
    @SerializedName("token")
    private TokenData token;

    public TokenData getToken() {
        return token;
    }
}
