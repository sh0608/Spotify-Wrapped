package com.example.spotifywrapped.ui.wrap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.Album;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;
import java.util.ArrayList;
import java.util.List;

public class WrapFragment extends Fragment {

    private FragmentWrapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWrapBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WrapViewModel wrapViewModel =
                new ViewModelProvider(this).get(WrapViewModel.class);
        String token = TokenManager.getToken(requireContext());

        // Call the getUserTopSongs method and pass the access token and a listener
        SpotifyApiHelper.getUserTopSongs(token, new SpotifyApiHelper.OnSongsLoadedListener() {
            @Override
            public void onSongsLoaded(List<Song> songs) {
                // Handle the loaded songs here
                StringBuilder songList = new StringBuilder();
                for (Song song : songs) {
                    songList.append(song.getName()).append("\n");
                }
                wrapViewModel.updateText(songList.toString());
                wrapViewModel.updateSongsList(songs);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                wrapViewModel.updateText("Error loading top songs: " + errorMessage);
            }
        });


        // initialize Top Album adapter + recyclerview
        RecyclerView topAlbumsRecyclerView = view.findViewById(R.id.albumList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        topAlbumsRecyclerView.setLayoutManager(layoutManager);


        List<Album> topAlbumList = new ArrayList<>();
        TopAlbumAdapter topAlbumAdapter = new TopAlbumAdapter(topAlbumList);
        topAlbumsRecyclerView.setAdapter(topAlbumAdapter);
        wrapViewModel.getAlbumsList().observe(getViewLifecycleOwner(), new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                topAlbumAdapter.setTopAlbums(albums);
            }
        });

        // initialize Top Song adapter + recyclerview
        RecyclerView topSongsRecyclerView = view.findViewById(R.id.favoriteSongList);
        topSongsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Song> topSongList = new ArrayList<>();
        TopSongAdapter topSongAdapter = new TopSongAdapter(topSongList);
        topSongsRecyclerView.setAdapter(topSongAdapter);
        wrapViewModel.getSongsList().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                topSongAdapter.setTopSongs(songs);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}