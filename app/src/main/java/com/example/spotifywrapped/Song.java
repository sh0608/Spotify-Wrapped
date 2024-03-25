package com.example.spotifywrapped;

public class Song {
    private String name;
    private String artist;
    private String imageUrl;
    private Album album;

    public Song(String name, String artist, String imageUrl, Album album) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
        this.album = album;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Album getAlbum() {
        return album;
    }
}
