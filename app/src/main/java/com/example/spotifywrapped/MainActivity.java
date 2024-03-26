package com.example.spotifywrapped;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.wrap.TopSongAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wrap, R.id.navigation_history, R.id.navigation_update)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        // populating topSong RecyclerView
        RecyclerView topSongsRecyclerView = findViewById(R.id.favoriteSongList);
        List<Song> topSongs;
        try {
            topSongs = SpotifyApiHelper.getUserTopSongs("hello world");
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        TopSongAdapter adapter = new TopSongAdapter(topSongs);
        topSongsRecyclerView.setAdapter(adapter);
        topSongsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

}