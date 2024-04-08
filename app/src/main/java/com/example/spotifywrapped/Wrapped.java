package com.example.spotifywrapped;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Wrapped {
    private List<Song> songs = new ArrayList<>();
    private List<Album> albums =  new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private List<String> genres = new ArrayList<>();


    public Wrapped(List<Song> songs, List<Album> albums, List<Artist> artists) {
        this.songs = songs;
        this.albums = albums;
        this.artists = artists;
        for (Artist artist : artists) {
            genres.add(artist.getGenres()[0]);
        }
    }

    public List<Song> getTopSongs() {
        return songs;
    }
    public List<Song> getSongs() {
        return songs;
    }
    public List<Artist> getArtists() {
        return artists;
    }

    public List<Album> getAlbums() {
        return albums;
    }
//    public List<Artist> getArtists() {
//        return artists;
//    }
    public List<String> getGenres() {
        return genres;
    }
}
