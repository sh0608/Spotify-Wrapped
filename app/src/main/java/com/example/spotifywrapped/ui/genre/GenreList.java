package com.example.spotifywrapped.ui.genre;

import java.util.ArrayList;

public class GenreList {
    private ArrayList<GenreModel> genreList;

    public GenreList() {

    }
    public GenreList(ArrayList<GenreModel> genreList) {
        this.genreList = genreList;
    }

    public ArrayList<GenreModel> getGenreList() {
        return genreList;
    }

    public void setGenreList(ArrayList<GenreModel> genreList) {
        this.genreList = genreList;
    }
}
