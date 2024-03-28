package com.example.spotifywrapped.ui;
import android.os.Looper;

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

import okhttp3.Call;
import okhttp3.Callback;
import android.os.Handler;
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
//    public static List<Song> getUserTopSongs(String accessToken) throws IOException, JSONException {
//        List<Song> topSongs = new ArrayList<>();
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(API_BASE_URL + "me/top/tracks")
//                .addHeader("Authorization", "Bearer " + accessToken)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        String jsonData = response.body().string();
//
//        // Parse JSON response and extract song data
//        JSONObject jsonObject = new JSONObject(jsonData);
//        JSONArray itemsArray = jsonObject.getJSONArray("items");
//        for (int i = 0; i < itemsArray.length(); i++) {
//            JSONObject trackObject = itemsArray.getJSONObject(i);
//            Song song = parseTrackJson(trackObject);
//            topSongs.add(song);
//        }
//        return topSongs;
//    }

    public interface OnSongsLoadedListener {
        void onSongsLoaded(List<Song> songs);
        void onError(String errorMessage);
    }

    public static void getUserTopSongs(String accessToken, OnSongsLoadedListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_BASE_URL + "me/top/tracks") // ?time_range=short_term&limit=5&offset=0
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Execute the callback on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    listener.onError(e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // If response is not successful, execute the callback with an error message
                    String errorMessage = "Unexpected response code: " + response.code();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        listener.onError(errorMessage);
                    });
                    return;
                }

                try {
                    String jsonData = response.body().string();
                    List<Song> songs = parseTopSongsJson(jsonData);
                    // Execute the callback on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        listener.onSongsLoaded(songs);
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    // Execute the callback with an error message
                    new Handler(Looper.getMainLooper()).post(() -> {
                        listener.onError(e.getMessage());
                    });
                }
            }
        });
    }

    private static List<Song> parseTopSongsJson(String jsonData) throws JSONException {
        List<Song> topSongs = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject trackObject = itemsArray.getJSONObject(i);
            Song song = parseTrackJson(trackObject);
            topSongs.add(song);
        }

        return topSongs;
    }

    // top artists

    // top playlists
}
