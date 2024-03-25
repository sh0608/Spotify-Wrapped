package com.example.spotifywrapped;

public class Song {
    private String name;
    private String[] artists;
    private String imageUrl;
    private String albumName;

    public Song(String name, String[] artists, String imageUrl, String albumName) {
        this.name = name;
        this.artists = artists;
        this.imageUrl = imageUrl;
        this.albumName = albumName;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String[] getArtists() {
        return artists;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAlbum() {
        return albumName;
    }
}
