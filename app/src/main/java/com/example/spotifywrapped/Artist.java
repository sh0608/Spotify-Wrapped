package com.example.spotifywrapped;

import com.example.spotifywrapped.Song;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Artist {
    private String name;
    private String followers;
    private String imageUrl;
    private String[] genres;

    public Artist(String name, String followers, String imageUrl, String[] genres) {
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

    public String[] getGenres() {
        return genres;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("followers", this.followers);
        map.put("imageUrl", this.imageUrl);
        map.put("genres", Arrays.asList(this.genres)); // Convert array to list
        return map;
    }

    public static Artist fromMap(Map<String, Object> map) {
        String name = (String) map.get("name");
        String followers = (String) map.get("followers");
        String imageUrl = (String) map.get("imageUrl");

        @SuppressWarnings("unchecked")
        List<String> genresList = (List<String>) map.get("genres");
        String[] genres = genresList.toArray(new String[0]);

        return new Artist(name, followers, imageUrl, genres);
    }


}
