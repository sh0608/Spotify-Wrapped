package com.example.spotifywrapped;

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
}
