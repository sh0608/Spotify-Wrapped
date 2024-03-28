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
                String temp = "";
                for (Song song : songs) {
                    temp += song.getName() + "\n";
                }
                wrapViewModel.updateText(temp);
                Log.d("Random", temp);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                wrapViewModel.updateText("Error loading top songs: " + errorMessage);
            }
        });

//        try {
//            ArrayList<Song> topSongs = (ArrayList<Song>) SpotifyApiHelper.getUserTopSongs(token);
//            String songList = "";
//            for (Song song : topSongs) {
//
//                songList += song.getName() + "\n";
//            }
//            wrapViewModel.updateText(songList);
//
//        } catch (IOException ioe) {
//
//            wrapViewModel.updateText("Error: cannot access top songs.");
//        } catch (JSONException jsone) {
//
//            wrapViewModel.updateText("Error: cannot access top songs.");
//        }

        wrapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}