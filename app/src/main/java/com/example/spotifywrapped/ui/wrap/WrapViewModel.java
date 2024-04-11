package com.example.spotifywrapped.ui.wrap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.Song;

import java.util.ArrayList;
import java.util.List;

public class WrapViewModel extends ViewModel {


    private final MutableLiveData<String> geminiResult = new MutableLiveData<>();
    private final MutableLiveData<String> geminiResultArtists = new MutableLiveData<>();
    private MutableLiveData<List<Song>> songsList = new MutableLiveData<>();
    private MutableLiveData<List<Artist>> artistsList = new MutableLiveData<>();

    private MutableLiveData<List<String>> genresList = new MutableLiveData<>();

    public WrapViewModel() {
        geminiResult.setValue("");
        geminiResultArtists.setValue("");
        songsList.setValue(new ArrayList<>());
        artistsList.setValue(new ArrayList<>());
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

    public void updateGeminiResult(String text) {

        geminiResult.postValue(text);
    }

    public void updateGeminiResultArtists(String text) {

        geminiResultArtists.postValue(text);
    }

    public LiveData<String> getGeminiResultArtists() {
        return geminiResultArtists;
    }

    public LiveData<String> getGeminiResult() {
        return geminiResult;
    }
}