package com.example.spotifywrapped.ui.genre;

public class GenreModel {
    private String name;
    private String url;

    public GenreModel() {

    }
    public GenreModel(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
