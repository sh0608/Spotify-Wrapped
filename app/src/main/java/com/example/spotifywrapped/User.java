package com.example.spotifywrapped;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class User {
    private String id;
    private String username;
    private String email;
    private List<User> friends;

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        friends = new ArrayList<User>();
    }

    public void updateFriends(CompletableFuture<List<User>> friends) throws ExecutionException, InterruptedException {
        this.friends = friends.get();
    }
    public boolean isFriend(User u) {
        return friends.contains(u);
    }

    public String getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
    public List<User> getFriends() { return this.friends; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }
}
