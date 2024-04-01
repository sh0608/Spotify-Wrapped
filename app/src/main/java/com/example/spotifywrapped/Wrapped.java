package com.example.spotifywrapped;


import java.util.ArrayList;
import java.util.List;

public class Wrapped {
    private List<Song> songs = new ArrayList<>();
    private List<Album> albums =  new ArrayList<>();
//    private List<Artists> artists = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    // commented out for now bc Artist class has not been merged yet and is giving errors
    // uncomment when Artist class has been merged
//    public Wrapped(List<Song> songs, List<Album> albums, List<Artists> artists) {
//        this.songs = songs;
//        this.albums = albums;
//        this.artists = artists;
//        for (Artists : artists) {
//            topGenres.add(artists.getGenres(0));
//        }
//    }

    public List<Song> getTopSongs() {
        return songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }
//    public List<Artists> getArtists() {
//        return artists;
//    }
    public List<String> getGenres() {
        return genres;
    }
}
