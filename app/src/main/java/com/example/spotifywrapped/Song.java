package com.example.spotifywrapped;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class Song {
    private String name;
    private String[] artists;
    private String imageUrl;
    private String albumName;

    private int minutesPlayed;
    private int timesPlayed;

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
        int numOfActualArtists = 0;
        for (int i = 0; i < artists.length; i++) {
            if (artists[i] != null) {
                numOfActualArtists++;
            }
        }
        String[] actualArtists = new String[numOfActualArtists];
        for (int i = 0; i < numOfActualArtists ; i++) {
            actualArtists[i] = artists[i];

        }
        return actualArtists;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAlbum() {
        return albumName;
    }
    public int getMinutesPlayed() {
        return minutesPlayed;
    }
    public int getTimesPlayed() {
        return timesPlayed;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("artists", Arrays.asList(this.artists)); // Convert array to list for easier handling in most DB systems
        map.put("imageUrl", this.imageUrl);
        map.put("albumName", this.albumName);
        return map;
    }
    public static Song fromMap(Map<String, Object> map) {
        String name = (String) map.get("name");
        @SuppressWarnings("unchecked")
        List<String> artistList = (List<String>) map.get("artists");
        String imageUrl = (String) map.get("imageUrl");
        String albumName = (String) map.get("albumName");

        String[] artists = artistList.toArray(new String[0]);

        return new Song(name, artists, imageUrl, albumName);
    }
}
