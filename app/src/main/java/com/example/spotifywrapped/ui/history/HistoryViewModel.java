package com.example.spotifywrapped.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrapped.Song;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private MutableLiveData<List<Song>> songsList = new MutableLiveData<>();


    public HistoryViewModel() {
        mText.setValue("This is history fragment");
        songsList.setValue(new ArrayList<>());

    }

    public void updateSongsList(List<Song> songs) {
        songsList.setValue(songs);
    }
    public LiveData<List<Song>> getSongsList() {
        return songsList;
    }

    public void updateText(String text) {

        mText.setValue(text);
    }


    public LiveData<String> getText() {
        return mText;
    }
}