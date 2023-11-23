package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TokenData {
    @SerializedName("name")
    private String name;

    @SerializedName("abilities")
    private List<String> abilities;

    @SerializedName("expires_at")
    private String expires_at;

    @SerializedName("tokenable_id")
    private int tokenable_id;

    @SerializedName("tokenable_type")
    private String tokenable_type;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("id")
    private int id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
