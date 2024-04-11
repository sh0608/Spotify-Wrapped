package com.example.spotifywrapped.ui.wrap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.Engine;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;
import com.example.spotifywrapped.ui.GeminiApiHelper;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.TokenManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WrapFragment extends Fragment {

    private FragmentWrapBinding  binding;
    private String topSongsList;
    private String topArtistsList;
    private TextView geminiResult;
    private WrapViewModel wrapViewModel;
    private Button btnShortTerm;
    private Button btnMediumTerm;
    private Button btnLongTerm;
    private Button[] allButtons;
    private Engine engine;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private SpotifyApiHelper.TimeFrame selectedTimeFrame = SpotifyApiHelper.TimeFrame.SHORT; // default


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWrapBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wrapViewModel =
                new ViewModelProvider(this).get(WrapViewModel.class);
        String token = TokenManager.getToken(requireContext());

        // store the spotify id in firebase
        sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", "default");
        SpotifyApiHelper.getUserId(token, new SpotifyApiHelper.OnIdLoadedListener() {
            @Override
            public void onIdLoaded(String id) {
                engine = new Engine();
                engine.setSpotifyId(username,id);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("SpotifyApiHelperUSERID", "Error loading id: " + errorMessage);
            }
        });


        // initialize Top genre adapter + recyclerview
        RecyclerView topGenreRecyclerView = view.findViewById(R.id.genreList);
        topGenreRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // topGenres = getTopGenres(topArtists);
        TopGenreAdapter topGenreAdapter = new TopGenreAdapter(new ArrayList<>());
        topGenreRecyclerView.setAdapter(topGenreAdapter);

        // onChanged listener for Top Genres
        // does it work if get rid of this bc artist already have?
        wrapViewModel.getGenresList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> generes) {
                topGenreAdapter.notifyDataSetChanged();
                topGenreAdapter.setTopGenres(generes);
            }
        });

        // initialize Top Artist adapter + recyclerView
        RecyclerView topArtistsRecyclerView = view.findViewById(R.id.topArtistsList);
        topArtistsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        TopArtistAdapter topArtistAdapter = new TopArtistAdapter(new ArrayList<>());
        topArtistsRecyclerView.setAdapter(topArtistAdapter);

        // onChanged listener for Top Artists
        wrapViewModel.getArtistsList().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
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
                wrapViewModel.updateArtistsList(artists);
                List<String> newGenres = getTopGenres(artists);
                wrapViewModel.updateGenresList(newGenres);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top artists: " + errorMessage);
                topArtistsList =  "Error loading top artists: " + errorMessage;
            }
        }, SpotifyApiHelper.TimeFrame.SHORT);

        // initialize Top Song adapter + recyclerview for Song List
        RecyclerView topSongsRecyclerView = view.findViewById(R.id.favoriteSongList);
        topSongsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        TopSongAdapter topSongAdapter = new TopSongAdapter(new ArrayList<>());
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
                wrapViewModel.updateSongsList(songs);
                GeminiApiHelper.getResponseFromGemini(songs, wrapViewModel);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                topSongsList = "Error loading top songs: " + errorMessage;
            }
        });

        geminiResult = binding.geminiResultTextView;
        wrapViewModel.getGeminiResult().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                geminiResult.setText(s);
            }
        });



        btnShortTerm = view.findViewById(R.id.btnShortTerm);
        btnMediumTerm = view.findViewById(R.id.btnMediumTerm);
        btnLongTerm = view.findViewById(R.id.btnLongTerm);

        allButtons = new Button[]{btnShortTerm, btnMediumTerm, btnLongTerm};

        btnShortTerm.setOnClickListener(v -> updateButtonStates(btnShortTerm));
        btnMediumTerm.setOnClickListener(v -> updateButtonStates(btnMediumTerm));
        btnLongTerm.setOnClickListener(v -> updateButtonStates(btnLongTerm));

        // Set initial state
        updateButtonStates(btnShortTerm);


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

    private void updateButtonStates(Button selectedButton) {
        for (Button button : allButtons) {
            button.setSelected(button == selectedButton);
        }
        updateTimeFrame(selectedButton);
    }

    private void updateTimeFrame(Button selectedButton) {
        // Determine the time frame based on the button and update data
        if (selectedButton == btnShortTerm) {
            selectedTimeFrame = SpotifyApiHelper.TimeFrame.SHORT;
        } else if (selectedButton == btnMediumTerm) {
            selectedTimeFrame = SpotifyApiHelper.TimeFrame.MEDIUM;
        } else if (selectedButton == btnLongTerm) {
            selectedTimeFrame = SpotifyApiHelper.TimeFrame.LONG;
        }
        loadDataBasedOnTimeFrame();
    }
    private void loadDataBasedOnTimeFrame() {
        String token = TokenManager.getToken(requireContext());
        SpotifyApiHelper.getUserTopSongs(token, new SpotifyApiHelper.OnSongsLoadedListener() {
            @Override
            public void onSongsLoaded(List<Song> songs) {
                topSongsList = "";
                for (Song song : songs) {
                    topSongsList += song.getName() + "\n";
                }
                wrapViewModel.updateSongsList(songs);
                GeminiApiHelper.getResponseFromGemini(songs, wrapViewModel);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top songs: " + errorMessage);
                topSongsList = "Error loading top songs: " + errorMessage;
            }
        }, selectedTimeFrame);

        SpotifyApiHelper.getUserTopArtists(token, new SpotifyApiHelper.OnArtistsLoadedListener() {
            @Override
            public void onArtistsLoaded(List<Artist> artists) {
                topArtistsList = "";
                for (Artist artist : artists) {
                    topArtistsList += artist.getName() + "\n";
                }
                wrapViewModel.updateArtistsList(artists);
                List<String> newGenres = getTopGenres(artists);
                wrapViewModel.updateGenresList(newGenres);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error here
                Log.e("SpotifyApiHelper", "Error loading top artists: " + errorMessage);
                topArtistsList =  "Error loading top artists: " + errorMessage;
            }
        }, selectedTimeFrame);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}