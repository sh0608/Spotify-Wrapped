package com.example.spotifywrapped.ui.genre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;

import java.util.ArrayList;

// Adapter class for RecyclerView to display genre
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private ArrayList<GenreModel> genreList;
    GenreViewModel model;

    // Constructor for the adapter class
    public GenreAdapter(ArrayList<GenreModel> genreList, GenreViewModel model) {
        this.genreList = genreList;
        this.model = model;
    }

    public int getItemViewType(final int position) {
        return R.layout.genre;
    }

    // Method to create view holders
    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new GenreAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenreModel genre = genreList.get(position);
        holder.displayName.setText(genre.getName());
        holder.displayURL.setText(genre.getUrl());
    }

    // Method to get the count of items
    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public ArrayList<GenreModel> getGenres() {
        return genreList;
    }

    public void setClasses(ArrayList<GenreModel> genreList) {
        this.genreList = genreList;
    }

    // ViewHolder class for each item
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayName, displayURL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.genreName);
        }
    }
}