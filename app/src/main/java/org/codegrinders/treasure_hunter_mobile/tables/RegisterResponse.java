package org.codegrinders.treasure_hunter_mobile.tables;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    private String id;
    private String email;
    private String username;
    private String password;
    private int points;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
