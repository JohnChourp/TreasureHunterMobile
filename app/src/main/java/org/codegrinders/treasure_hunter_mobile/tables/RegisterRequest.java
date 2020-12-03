package org.codegrinders.treasure_hunter_mobile.tables;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("id")

    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("points")
    private int points;

    public void setId(String id) {this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
