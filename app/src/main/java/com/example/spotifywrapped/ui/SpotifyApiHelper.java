package com.example.spotifywrapped.ui;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.Album;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class SpotifyApiHelper {
    private static final String API_BASE_URL = "https://api.spotify.com/v1/";

    public static Song parseTrackJson(JSONObject trackJson) throws JSONException {
        String name = trackJson.getString("name");

        // Extracting artists
        JSONArray artistsArray = trackJson.getJSONArray("artists");
        String[] artists = new String[6];
        for (int i = 0; i < artistsArray.length(); i++) {
            JSONObject artistObject = artistsArray.getJSONObject(i);
            artists[i] = artistObject.getString("name");
        }

        // Extracting album
        JSONObject albumObject = trackJson.getJSONObject("album");
        String albumName = albumObject.getString("name");

        // Extracting image URL
        JSONArray imagesArray = albumObject.getJSONArray("images");
        String imageUrl = null;
        if (imagesArray.length() > 0) {
            JSONObject firstImage = imagesArray.getJSONObject(0);
            imageUrl = firstImage.getString("url");
        }

        // Constructing Song object
        return new Song(name, artists, imageUrl, albumName);
    }
    public static List<Song> getUserTopSongs(String accessToken) throws IOException, JSONException {
        List<Song> topSongs = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_BASE_URL + "me/top/tracks")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        // Parse JSON response and extract song data
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject trackObject = itemsArray.getJSONObject(i);
            Song song = parseTrackJson(trackObject);
            topSongs.add(song);
        }
        return topSongs;
    }
}
