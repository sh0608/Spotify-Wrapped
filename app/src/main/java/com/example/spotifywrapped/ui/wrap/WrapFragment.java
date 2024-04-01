package com.example.spotifywrapped.ui.wrap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.Album;
import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;
import java.util.ArrayList;
import java.util.List;

public class WrapFragment extends Fragment {

    private FragmentWrapBinding  binding;
    private String topSongsList;
    private String topArtistsList;

    private TextView textHome;
    private List<Song> topSongs;
    private List<Artist> topArtists;
    private List<String> topGenres;

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

        // initialize Top Artist adapter + recyclerView
        RecyclerView topArtistsRecyclerView = view.findViewById(R.id.topArtistsList);
        topArtistsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        topArtists = new ArrayList<>();
        TopArtistAdapter topArtistAdapter = new TopArtistAdapter(topArtists);
        topArtistsRecyclerView.setAdapter(topArtistAdapter);

        // onChanged listener for Top Artists
        wrapViewModel.getArtistsList().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                topArtistAdapter.notifyDataSetChanged();
                topArtistAdapter.setTopArtists(artists);
            }
        });

        // Call the getUserTopArtists method and pass the access token and a listener

        SpotifyApiHelper.getUserTopArtists(token, new SpotifyApiHelper.OnArtistsLoadedListener() {
            @Override
            public void onArtistsLoaded(List<Artist> artists) {
                topArtistsList = "";
                for (Artist artist : artists) {
                    topArtistsList += artist.getName() + "\n";
                }

                wrapViewModel.updateText(topArtistsList.toString());
                wrapViewModel.updateArtistsList(artists);            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top artists: " + errorMessage);
                topArtistsList =  "Error loading top artists: " + errorMessage;
            }
        });

        wrapViewModel.updateText(topArtistsList);

        // initialize Top Song adapter + recyclerview for Song List
        RecyclerView topSongsRecyclerView = view.findViewById(R.id.favoriteSongList);
        topSongsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        topSongs = new ArrayList<>();
        TopSongAdapter topSongAdapter = new TopSongAdapter(topSongs);
        topSongsRecyclerView.setAdapter(topSongAdapter);

        //  onChanged listener for Top Songs
        wrapViewModel.getSongsList().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                topSongAdapter.notifyDataSetChanged();
                topSongAdapter.setTopSongs(songs);
            }
        });

        // Call the getUserTopSongs method and pass the access token and a listener
        SpotifyApiHelper.getUserTopSongs(token, new SpotifyApiHelper.OnSongsLoadedListener() {
            @Override
            public void onSongsLoaded(List<Song> songs) {
                topSongsList = "";
                for (Song song : songs) {
                    topSongsList += song.getName() + "\n";
                }
                wrapViewModel.updateText(topSongsList.toString());
                wrapViewModel.updateSongsList(songs);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                topSongsList = "Error loading top songs: " + errorMessage;
            }
        });

        // initialize Top genre adapter + recyclerview
        RecyclerView topGenreRecyclerView = view.findViewById(R.id.genreList);
        topGenreRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //topGenres = getTopGenres(topArtists);
        //TopGenreAdapter topGenreAdapter = new TopGenreAdapter(topGenres);
        //topGenreRecyclerView.setAdapter(topGenreAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}