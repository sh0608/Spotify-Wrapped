package com.example.spotifywrapped.ui.history;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.databinding.FragmentHistoryOneMonthBinding;
import com.example.spotifywrapped.databinding.FragmentHistorySixMonthBinding;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;
import com.example.spotifywrapped.ui.wrap.TopArtistAdapter;
import com.example.spotifywrapped.ui.wrap.TopGenreAdapter;
import com.example.spotifywrapped.ui.wrap.TopSongAdapter;
import com.example.spotifywrapped.ui.wrap.WrapViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HistorySixMonth extends Fragment {

    private FragmentHistorySixMonthBinding binding;
    private String topSongsList;
    private String topArtistsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistorySixMonthBinding.inflate(inflater, container, false);

        binding.imageButtonSixmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            public void onClick(View v) {
                NavHostFragment.findNavController(HistorySixMonth.this)
                        .navigate(R.id.action_navigation_history_six_month_to_navigation_history);
            }
        });

        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistorySixMonthViewModel viewModel =
                new ViewModelProvider(this).get(HistorySixMonthViewModel.class);
        String token = TokenManager.getToken(requireContext());

        // initialize Top genre adapter + recyclerview
        RecyclerView topGenreRecyclerView = view.findViewById(R.id.genreList_sixmonth);
        topGenreRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // topGenres = getTopGenres(topArtists);
        TopGenreAdapter topGenreAdapter = new TopGenreAdapter(new ArrayList<>());
        topGenreRecyclerView.setAdapter(topGenreAdapter);

        // onChanged listener for Top Genres
        // does it work if get rid of this bc artist already have?
        viewModel.getGenresList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> generes) {
                topGenreAdapter.notifyDataSetChanged();
                topGenreAdapter.setTopGenres(generes);
            }
        });

        // initialize Top Artist adapter + recyclerView
        RecyclerView topArtistsRecyclerView = view.findViewById(R.id.topArtistsList_sixmonth);
        topArtistsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        TopArtistAdapter topArtistAdapter = new TopArtistAdapter(new ArrayList<>());
        topArtistsRecyclerView.setAdapter(topArtistAdapter);

        // onChanged listener for Top Artists
        viewModel.getArtistsList().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                topArtistAdapter.notifyDataSetChanged();
                topArtistAdapter.setTopArtists(artists);
                topGenreAdapter.notifyDataSetChanged();
                topGenreAdapter.setTopGenres(getTopGenres(artists));
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
                viewModel.updateArtistsList(artists);
                List<String> newGenres = getTopGenres(artists);
                viewModel.updateGenresList(newGenres);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top artists: " + errorMessage);
                topArtistsList =  "Error loading top artists: " + errorMessage;
            }
        }, SpotifyApiHelper.TimeFrame.MEDIUM);

        // initialize Top Song adapter + recyclerview for Song List
        RecyclerView topSongsRecyclerView = view.findViewById(R.id.favoriteSongList_sixmonth);
        topSongsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        TopSongAdapter topSongAdapter = new TopSongAdapter(new ArrayList<>());
        topSongsRecyclerView.setAdapter(topSongAdapter);

        //  onChanged listener for Top Songs
        viewModel.getSongsList().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
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
                viewModel.updateSongsList(songs);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                topSongsList = "Error loading top songs: " + errorMessage;
            }
        }, SpotifyApiHelper.TimeFrame.MEDIUM);

    }


    private List<String> getTopGenres(List<Artist> topArtists) {
        HashMap<String, Integer> genreList = new HashMap<>();
        for (Artist artist : topArtists) {
            for (String genre : artist.getGenres()) {
                if (genre != null) {
                    if (genreList.containsKey(genre)) {
                        int numOccurence = genreList.get(genre);
                        genreList.put(genre, ++numOccurence);
                    } else {
                        genreList.put(genre, 0);
                    }
                }
            }
        }

        // Create a list from elements of HashMap
        List<HashMap.Entry<String, Integer>> list  = new LinkedList<>(genreList.entrySet());

        // sort by value
        list.sort(Map.Entry.comparingByValue());

        Collections.reverse(list);

        List<String> topGenreList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: list) {
            topGenreList.add(entry.getKey());
        }

        return topGenreList;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}