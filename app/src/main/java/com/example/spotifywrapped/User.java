package com.example.spotifywrapped;

public class User {
    private String id;
    private String username;
    private String email;

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
}
