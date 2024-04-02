package com.example.spotifywrapped.ui.wrap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.Album;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Song;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TopAlbumAdapter extends RecyclerView.Adapter<TopAlbumAdapter.ViewHolder> {

    private List<Album> topAlbums;


    public TopAlbumAdapter(List<Album> topAlbums) {
        this.topAlbums = topAlbums;
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     * @param position position to query
     * @return the view type of the item
     */
    public int getItemViewType (final int position) {
        return R.layout.item_top_album;
    }

    /**
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
        View topAlbum = inflater.inflate(R.layout.item_top_album, parent, false);
        return new ViewHolder(topAlbum);
    }

    /**
     * Binds the data into the xml file.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = topAlbums.get(position);

        TextView topAlbumTitleTextView = holder.topAlbumTitleTextView;
        topAlbumTitleTextView.setText(album.getName());

        TextView topAlbumArtistNameTextView = holder.topAlbumArtistNameTextView;
        topAlbumArtistNameTextView.setText(album.getArtist());

        ImageView topAlbumImageView = holder.topAlbumImageView;
        // calls Picasso library to process the imageUrl into ImageView
        Picasso.get().load(album.getImageUrl()).into(topAlbumImageView);
    }

    /**
     * setter for topAlbums
     * @param newAlbums new list of top albums
     */
    public void setTopAlbums(List<Album> newAlbums) {
        topAlbums = newAlbums;
    }



    /**
     * returns the number of topAlbums
     * @return number of topAlbums
     */
    @Override
    public int getItemCount() {
        return topAlbums.size();
    }

    /**
     * inner ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topAlbumTitleTextView;
        TextView topAlbumArtistNameTextView;
        ImageView topAlbumImageView;


        public ViewHolder(View itemView) {
            super (itemView);
            topAlbumTitleTextView = itemView.findViewById(R.id.albumName);
            topAlbumArtistNameTextView = itemView.findViewById(R.id.albumArtist);
            topAlbumImageView = itemView.findViewById(R.id.albumImage);
        }
    }
}
