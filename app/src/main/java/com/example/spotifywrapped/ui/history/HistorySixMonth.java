package com.example.spotifywrapped.ui.history;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.databinding.FragmentHistoryOneMonthBinding;
import com.example.spotifywrapped.databinding.FragmentHistorySixMonthBinding;


import com.example.spotifywrapped.R;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class HistorySixMonth extends Fragment {

    private HistorySixMonthViewModel mViewModel;
    private FragmentHistorySixMonthBinding binding;
    private List<Song> songList = new ArrayList<>();


    public static HistorySixMonth newInstance() {
        return new HistorySixMonth();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistorySixMonthBinding.inflate(inflater, container, false);

        HistorySixMonthViewModel historySixMonthViewModel =
                new ViewModelProvider(this).get(HistorySixMonthViewModel.class);

        RecyclerView recyclerView = binding.songsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SongsAdapter adapter = new SongsAdapter(songList);
        recyclerView.setAdapter(adapter);


        String token = TokenManager.getToken(requireContext());

        SpotifyApiHelper.getUserTopSongs(token, new SpotifyApiHelper.OnSongsLoadedListener() {

            @Override
            public void onSongsLoaded(List<Song> songs) {

                if (songs.isEmpty()) {
                    Toast.makeText(getActivity(), "Wrapped for this time range is empty! Use Spotify more and check again later!", Toast.LENGTH_LONG).show();

                } else {

//                    StringBuilder songList = new StringBuilder();
//                    for (Song song : songs) {
//                        songList.append(song.getName()).append("\n");
                    songList.clear(); // Clear existing data if any
                    songList.addAll(songs); // Add all the loaded songs
                    adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed

                    Log.e("HistoryFragment", "Songs loaded: " + songs.size());
                    Log.e("HistoryFragment", "Songs loaded: " + songs.get(0).getName());
                    Toast.makeText(getActivity(), "Songs have been added", Toast.LENGTH_SHORT).show();
//                    }
//                    historyOneMonthViewModel.updateText(songList.toString());
                    historySixMonthViewModel.updateSongsList(songs);
                }

            }
            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                historySixMonthViewModel.updateText("Error loading top songs: " + errorMessage);
            }
        }, SpotifyApiHelper.TimeFrame.MEDIUM);
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HistorySixMonth.this)
                        .navigate(R.id.action_navigation_history_six_month_to_navigation_history);
            }
        });
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistorySixMonthViewModel.class);
        // TODO: Use the ViewModel
    }

}