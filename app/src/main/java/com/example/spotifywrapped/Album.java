package com.example.spotifywrapped;

import com.example.spotifywrapped.Song;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("artist", this.artist);
        map.put("imageUrl", this.imageUrl);
        map.put("songs", this.songs.stream().map(Song::toMap).collect(Collectors.toList()));
        return map;
    }

    public static Album fromMap(Map<String, Object> map) {
        String name = (String) map.get("name");
        String artist = (String) map.get("artist");
        String imageUrl = (String) map.get("imageUrl");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> songsListMap = (List<Map<String, Object>>) map.get("songs");
        List<Song> songs = songsListMap.stream()
                .map(Song::fromMap)
                .collect(Collectors.toList());

        return new Album(name, artist, imageUrl, songs);
    }
}
