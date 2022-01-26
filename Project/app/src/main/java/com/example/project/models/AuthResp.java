package com.example.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResp {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("role")
    @Expose
    private String role;

    public AuthResp() {
    }

    public AuthResp(boolean success, String token, String name, String email, String password, String role) {
        this.success = success;
        this.token = token;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
