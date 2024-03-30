package com.example.spotifywrapped.ui.wrap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.ViewHolder> {

    private List<Song> topSongs;
    private WrapViewModel model;


    public TopSongAdapter(List<Song> topSongs, WrapViewModel model) {
        this.topSongs = topSongs;
        this.model = model;
    }

    public int getItemViewType (final int position) {
        return R.layout.item_topsong;
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
        String topArtistsNameFormatted = formatArray(song.getArtists());
        topSongArtistNameTextView.setText(topArtistsNameFormatted);
        TextView topSongAlbumTitleTextView = holder.topSongAlbumTitleTextView;
        topSongAlbumTitleTextView.setText(song.getAlbum());
        ImageView topSongImageView = holder.topSongImageView;
        Picasso.get().load(song.getImageUrl()).into(topSongImageView);
    }

    private String formatArray (String[] arr) {
        String rtn = Arrays.toString(arr);
        rtn = rtn.substring(1, rtn.length() - 1);
        return rtn;
    }


    @Override
    public int getItemCount() {
        return topSongs.size();
    }

    public void setTopSongs(List<Song> newSongs) {
        topSongs = newSongs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topSongTitleTextView;
        TextView topSongArtistNameTextView;
        TextView topSongAlbumTitleTextView;
        ImageView topSongImageView;


        public ViewHolder(View itemView) {
            super (itemView);
            topSongTitleTextView = (TextView) itemView.findViewById(R.id.topSongTitle);
            topSongArtistNameTextView = (TextView) itemView.findViewById(R.id.topSongArtistName);
            topSongAlbumTitleTextView = (TextView) itemView.findViewById(R.id.topSongAlbumTitle);
            topSongImageView = (ImageView) itemView.findViewById(R.id.topSongImage);
        }
    }
}
