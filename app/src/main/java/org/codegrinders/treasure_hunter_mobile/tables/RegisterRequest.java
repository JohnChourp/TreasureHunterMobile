package org.codegrinders.treasure_hunter_mobile.tables;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    private String id="as12";
    private String username;
    private String email;
    private String password;
    private int points=0;

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
