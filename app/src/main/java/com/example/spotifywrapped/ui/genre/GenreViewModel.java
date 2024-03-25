package com.example.spotifywrapped.ui.genre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class GenreViewModel extends ViewModel {
    private MutableLiveData<ArrayList<GenreModel>> genreList = new MutableLiveData<>();

    public LiveData<ArrayList<GenreModel>> getExamsList() {
        return genreList;
    }

    public void setGenreList(ArrayList<GenreModel> genreList) {
        this.genreList.setValue(genreList);
    }

    // for edit
    private MutableLiveData<Integer> editGenrePosition = new MutableLiveData<>();

    public LiveData<Integer> getEditGenrePosition() {
        return editGenrePosition;
    }

    public void setEditGenrePosition(int position) {
        editGenrePosition.setValue(position);
    }
}
