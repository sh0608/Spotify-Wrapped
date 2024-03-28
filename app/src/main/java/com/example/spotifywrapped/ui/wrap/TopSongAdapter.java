package com.example.spotifywrapped.ui.wrap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.ViewHolder> {

    private List<Song> topSongs;

    public TopSongAdapter(List<Song> topSongs) {
        this.topSongs = topSongs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_topsong, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = topSongs.get(position);
        TextView topSongTitleTextView = holder.topSongTitleTextView;
        topSongTitleTextView.setText(song.getName());
        TextView topSongArtistNameTextView = holder.topSongArtistNameTextView;
        topSongArtistNameTextView.setText(Arrays.toString(song.getArtists()).replaceAll("\\[|\\]", ""));
        TextView topSongAlbumTitleTextView = holder.topSongAlbumTitleTextView;
        topSongAlbumTitleTextView.setText(song.getAlbum());

    }

    @Override
    public int getItemCount() {
        return topSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topSongTitleTextView;
        public TextView topSongArtistNameTextView;
        public TextView topSongAlbumTitleTextView;


        public ViewHolder(View itemView) {
            super (itemView);
            topSongTitleTextView = (TextView) itemView.findViewById(R.id.topSongTitle);
            topSongArtistNameTextView = (TextView) itemView.findViewById(R.id.topSongArtistName);
            topSongAlbumTitleTextView = (TextView) itemView.findViewById(R.id.topSongAlbumTitle);
        }
    }
}
