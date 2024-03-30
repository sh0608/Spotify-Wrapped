package com.example.spotifywrapped.ui.wrap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrapped.Album;
import com.example.spotifywrapped.Song;

import java.util.ArrayList;
import java.util.List;

public class WrapViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private MutableLiveData<List<Song>> songsList = new MutableLiveData<>();

    private MutableLiveData<List<Album>> albumsList = new MutableLiveData<>();

    public WrapViewModel() {
        mText.setValue("This is home fragment");
        songsList.setValue(new ArrayList<>());
        albumsList.setValue(new ArrayList<>());
    }

    public void updateSongsList(List<Song> songs) {
        songsList.setValue(songs);
    }

    public LiveData<List<Song>> getSongsList() {
        return songsList;
    }

    public void updateAlbumsList(List<Album> albums) {
        albumsList.setValue(albums);
    }

    public LiveData<List<Album>> getAlbumsList() {
        return albumsList;
    }

    public void updateText(String text) {

        mText.setValue(text);
    }

    public LiveData<String> getText() {
        return mText;
    }
}