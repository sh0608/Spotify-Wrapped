package com.example.spotifywrapped.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {
    private List<Song> songsList;

    public SongsAdapter(List<Song> songsList) {
        this.songsList = songsList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songsList.get(position);
        holder.songNameTextView.setText(song.getName());
        // Set more attributes of your song here
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView songNameTextView;

        public SongViewHolder(View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.song_name_history);
        }
    }
}
