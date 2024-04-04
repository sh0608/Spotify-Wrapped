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

public class StartActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    //b0e28dce674d447987867d662ef7e1c9: group app client ID
    protected static final String CLIENT_ID = "b0e28dce674d447987867d662ef7e1c9";
    private static final String REDIRECT_URI = "com.example.spotifywrapped://callback";
    private static final String SCOPES = "user-read-recently-played,user-read-private, user-top-read, user-library-read, streaming";
    private ActivityStartBinding binding;

    private Engine engine;
    private static final String TAG = "LOG_TAG";

    protected void setUpFirebase() {
        engine = new Engine();
        User u1 = new User("1","user1","user1@g");
        User u2 = new User("2","user2","user2@g");
        User u3 = new User("3","user3","user3@g");
        User u4 = new User("4","user4","user4@g");
        engine.addUser(u1);
        engine.addUser(u2);
        engine.addUser(u3);
        engine.addUser(u4);

        engine.addConnection(u1,u2);
        engine.addConnection(u1,u3);
        engine.addConnection(u3,u2);
        engine.addConnection(u4,u1);
        engine.addConnection(u4,u2);

        engine.acceptConnection(u1, u2);

        engine.getConnections(u1).thenAccept(users -> {
            for (User user : users) {
                Log.d("LOG_TAG", user.getUsername());
            }

        }).exceptionally(e -> {
            Log.e("LOG_TAG", e.getMessage());
            return null;
        });
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