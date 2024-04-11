package com.example.spotifywrapped;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.spotifywrapped.databinding.ActivityStartBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import android.util.Log;
import java.util.List;
import java.util.Arrays;

public class StartActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    protected static final String CLIENT_ID = "b0e28dce674d447987867d662ef7e1c9";
    private static final String REDIRECT_URI = "com.example.spotifywrapped://callback";
    private static final String SCOPES = "user-read-recently-played,user-read-private, user-top-read, user-library-read, streaming";
    private ActivityStartBinding binding;

    private Engine engine;
    private static final String TAG = "LOG_TAG";

    protected void setUpFirebase() {
        engine = new Engine();

        engine.addUser("user1", "user1@g");
        engine.addUser("user2", "user2@g");
        engine.addUser("user3", "user3@g");
        engine.addUser("user4", "user4@g");

        engine.addConnection("user1","user2");
        engine.addConnection("user1","user3");
        engine.addConnection("user3","user2");
        engine.addConnection("user4","user1");
        engine.addConnection("user4","user2");

        engine.acceptConnection("user1","user2");

        engine.getConnections("user1").thenAccept(users -> {
            for (String user : users) {
                Log.d("LOG_TAG", user);
            }

        }).exceptionally(e -> {
            Log.e("LOG_TAG", e.getMessage());
            return null;
        });
//        test_1();
    }

    protected void test_1() {
        engine = new Engine();
//        engine.addUser("user1", "user1@g");
//        engine.addUser("user2", "user2@g");
        engine.deleteUser("user3");

        Song song1 = new Song("Song 1", new String[]{"Artist 1"}, "song1.jpg", "Album 1");
        Song song2 = new Song("Song 2", new String[]{"Artist 2"}, "song2.jpg", "Album 2");

        // Create some album objects with the songs
        List<Song> album1Songs = new ArrayList<>(Arrays.asList(song1));
        List<Song> album2Songs = new ArrayList<>(Arrays.asList(song2));
        Album album1 = new Album("Album 1", "Artist 1", "album1.jpg", album1Songs);
        Album album2 = new Album("Album 2", "Artist 2", "album2.jpg", album2Songs);

        // Create some artist objects
        Artist artist1 = new Artist("Artist 1", "1000", "artist1.jpg", new String[]{"Genre 1"});
        Artist artist2 = new Artist("Artist 2", "500", "artist2.jpg", new String[]{"Genre 2"});

        // Create the Wrapped object using the lists of songs, albums, and artists
        List<Song> songs = new ArrayList<>(Arrays.asList(song1, song2));
        List<Album> albums = new ArrayList<>(Arrays.asList(album1, album2));
        List<Artist> artists = new ArrayList<>(Arrays.asList(artist1, artist2));

        Wrapped wrapped = new Wrapped(songs, albums, artists);

//        engine.addWrapped("user3", wrapped);
//
//        engine.getWraps("user2").thenAccept(wraps -> {
//            for ( Wrapped w : wraps) {
//                Log.d("LOG_TAG", "Songs for user2: ");
//                for (Song s:w.getSongs()) {
//                    Log.d("LOG_TAG", s.getName());
//                }
//            }
//
//        }).exceptionally(e -> {
//            Log.e("LOG_TAG", e.getMessage());
//            return null;
//        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpFirebase();

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.loginButton.setOnClickListener(v -> {
            authorizeSpotify();
        });
    }

    public void authorizeSpotify() {
        final AuthorizationRequest request = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{SCOPES})
                .setShowDialog(true)
                .build();

        AuthorizationClient.openLoginInBrowser(this, request);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthorizationResponse response = AuthorizationResponse.fromUri(uri);
            switch (response.getType()) {
                case TOKEN:
                    String token = response.getAccessToken();
                    editor.putString("token", token);
                    editor.apply();
                    Log.d("STARTING", "GOT AUTH TOKEN" + token);
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    break;
                case ERROR:
                    Log.d("WE GOT AN ERROR", response.getError());
                    break;
                default:
                    Log.d("SOMETHING ELSE HAPPENED", response.getState() + " " + response.getCode() +
                            " " + response.describeContents());
            }
        }
    }
}