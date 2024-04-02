package com.example.spotifywrapped.ui.wrap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrapped.Album;
import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WrapViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private MutableLiveData<List<Song>> songsList = new MutableLiveData<>();
    private MutableLiveData<List<Artist>> artistsList = new MutableLiveData<>();

    private MutableLiveData<List<String>> genresList = new MutableLiveData<>();

    public WrapViewModel() {
        mText.setValue("This is home fragment");
        songsList.setValue(new ArrayList<>());
        genresList.setValue(new ArrayList<>());
    }

    public void updateSongsList(List<Song> songs) {
        songsList.setValue(songs);
    }

    public void updateArtistsList(List<Artist> artists) {
        artistsList.setValue(artists);
    }

    public void updateGenresList(List<String> newGenres) {
        genresList.setValue(newGenres);
    }


    public LiveData<List<Song>> getSongsList() {
        return songsList;
    }

    public LiveData<List<Artist>> getArtistsList() {
        return artistsList;
    }

    public LiveData<List<String>> getGenresList() {
        return genresList;
    }

    public void updateText(String text) {

        mText.setValue(text);
    }




    public LiveData<String> getText() {
        return mText;
    }
}