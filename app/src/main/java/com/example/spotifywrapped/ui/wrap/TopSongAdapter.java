package com.example.spotifywrapped.ui.wrap;

import android.content.Context;
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
import java.util.Arrays;
import java.util.List;

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.ViewHolder> {

    private List<Song> topSongs;


    public TopSongAdapter(List<Song> topSongs) {
        this.topSongs = topSongs;
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     * @param position position to query
     * @return the view type of the item
     */
    public int getItemViewType (final int position) {
        return R.layout.item_topsong;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return viewHolder of each topSong
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View topSong = inflater.inflate(R.layout.item_topsong, parent, false);
        return new ViewHolder(topSong);
    }

    /**
     * Binds the data into the xml file.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = topSongs.get(position);

        TextView topSongTitleTextView = holder.topSongTitleTextView;
        String topSongTitle =  (position + 1) + ". " + song.getName();
        topSongTitleTextView.setText(topSongTitle);

        TextView topSongArtistNameTextView = holder.topSongArtistNameTextView;
        String topArtistsNameFormatted = formatArray(song.getArtists());
        topSongArtistNameTextView.setText(topArtistsNameFormatted);

        TextView topSongAlbumTitleTextView = holder.topSongAlbumTitleTextView;
        topSongAlbumTitleTextView.setText(song.getAlbum());

        ImageView topSongImageView = holder.topSongImageView;
        // calls Picasso library to process the imageUrl into ImageView
        Picasso.get().load(song.getImageUrl()).into(topSongImageView);
    }

    /**
     * private method to process the artists array into a String.
     * @param arr String array
     * @return String of artists
     */
    private String formatArray (String[] arr) {
        String rtn = Arrays.toString(arr);
        rtn = rtn.substring(1, rtn.length() - 1);
        return rtn;
    }


    /**
     * returns the number of topSongs
     * @return number of topSongs
     */
    @Override
    public int getItemCount() {
        return topSongs.size();
    }

    /**
     * setter for topSongs
     * @param newSongs new list of top songs
     */
    public void setTopSongs(List<Song> newSongs) {
        topSongs = newSongs;
    }


    /**
     * inner VieHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topSongTitleTextView;
        TextView topSongArtistNameTextView;
        TextView topSongAlbumTitleTextView;
        ImageView topSongImageView;


        public ViewHolder(View itemView) {
            super (itemView);
            topSongTitleTextView = itemView.findViewById(R.id.topSongTitle);
            topSongArtistNameTextView = itemView.findViewById(R.id.topSongArtistName);
            topSongAlbumTitleTextView = itemView.findViewById(R.id.topSongAlbumTitle);
            topSongImageView = itemView.findViewById(R.id.topSongImage);
        }
    }
}
