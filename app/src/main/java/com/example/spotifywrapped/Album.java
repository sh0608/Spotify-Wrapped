package com.example.spotifywrapped;

import com.example.spotifywrapped.Song;

import java.util.List;

public class Album {
    private String name;
    private String artist;
    private String imageUrl;
    private List<Song> songs;

    public Album(String name, String artist, String imageUrl, List<Song> songs) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
        this.songs = songs;
    }

    // No setters needed/allowed
    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
