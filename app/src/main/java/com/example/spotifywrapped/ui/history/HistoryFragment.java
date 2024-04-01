package com.example.spotifywrapped.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.databinding.FragmentHistoryBinding;

import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;

import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    public HistoryFragment() {}
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HistoryFragment.this)
                        .navigate(R.id.action_fragment_history_to_fragment_history_one_month);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HistoryFragment.this)
                        .navigate(R.id.action_fragment_history_to_fragment_history_six_months);
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HistoryFragment.this)
                        .navigate(R.id.action_fragment_history_to_fragment_history_all_time);
            }
        });

        String token = TokenManager.getToken(requireContext());

        SpotifyApiHelper.getUserTopSongs(token, new SpotifyApiHelper.OnSongsLoadedListener() {
            @Override
            public void onSongsLoaded(List<Song> songs) {
                if (songs.isEmpty()) {
                    historyViewModel.updateText("Wrapped for this time range is empty! Check back later after using Spotify more!");
                } else {
                    // Handle the loaded songs here
                    StringBuilder songList = new StringBuilder();
                    for (Song song : songs) {
                        songList.append(song.getName()).append("\n");
                    }
                    historyViewModel.updateText(songList.toString());
                    historyViewModel.updateSongsList(songs);
                }

            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                historyViewModel.updateText("Error loading top songs: " + errorMessage);
            }
        }, SpotifyApiHelper.TimeFrame.SHORT);

        historyViewModel.getSongsList().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                // set adapter list to songs
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}