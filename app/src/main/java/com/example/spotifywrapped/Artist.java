package com.example.spotifywrapped;

import com.example.spotifywrapped.Song;

import java.util.List;

public class Artist {
    private String name;
    private String followers;
    private String imageUrl;
    private String genres;

    public Artist(String name, String followers, String imageUrl, String genres) {
        this.name = name;
        this.followers = followers;
        this.imageUrl = imageUrl;
        this.genres = genres;
    }

    // No setters needed/allowed
    public String getName() {
        return name;
    }

    public String getFollowers() {
        return followers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGenres() {
        return genres;
    }


}
