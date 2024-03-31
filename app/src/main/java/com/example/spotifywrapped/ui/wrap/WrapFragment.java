package com.example.spotifywrapped.ui.wrap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WrapFragment extends Fragment {

    private FragmentWrapBinding  binding;
    private String topSongsList;
    private String topArtistsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WrapViewModel wrapViewModel =
                new ViewModelProvider(this).get(WrapViewModel.class);

        binding = FragmentWrapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        String token = TokenManager.getToken(requireContext());

        // Call the getUserTopSongs method and pass the access token and a listener
        SpotifyApiHelper.getUserTopSongs(token, new SpotifyApiHelper.OnSongsLoadedListener() {
            @Override
            public void onSongsLoaded(List<Song> songs) {
                topSongsList = "";
                for (Song song : songs) {
                    topSongsList += song.getName() + "\n";
                }

                Log.d("Random", topSongsList);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                topSongsList = "Error loading top songs: " + errorMessage;
            }
        });

        SpotifyApiHelper.getUserTopArtists(token, new SpotifyApiHelper.OnArtistsLoadedListener() {
            @Override
            public void onArtistsLoaded(List<Artist> artists) {
                topArtistsList = "";
                for (Artist artist : artists) {
                    topArtistsList += artist.getName() + "\n";
                }

                Log.d("Random", topArtistsList);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top artists: " + errorMessage);
                topArtistsList =  "Error loading top artists: " + errorMessage;
            }
        });

        wrapViewModel.updateText(topArtistsList);

        wrapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}