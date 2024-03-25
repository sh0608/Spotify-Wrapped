package com.example.spotifywrapped;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

public class StartActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    protected static final String CLIENT_ID = "b0e28dce674d447987867d662ef7e1c9";
    private static final String REDIRECT_URI = "com.example.spotifywrapped://callback";
    private static final String SCOPES = "user-read-recently-played,user-read-private";
    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.loginButton.setOnClickListener(v -> {
            authorizeSpotify();
        });
    }

    public void authorizeSpotify() {
        editor.remove("token");
        editor.apply();
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