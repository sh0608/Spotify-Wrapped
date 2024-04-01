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
import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopArtistAdapter extends RecyclerView.Adapter<TopArtistAdapter.ViewHolder> {
    private List<Artist> topArtists;

    public TopArtistAdapter(List<Artist> topArtists) {
        this.topArtists = topArtists;
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     * @param position position to query
     * @return the view type of the item
     */
    public int getItemViewType (final int position) {
        return R.layout.item_top_artist;
    }

    @Override
    public int getItemCount() {
        return topArtists.size();
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
    public TopArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View topAlbum = inflater.inflate(R.layout.item_top_artist, parent, false);
        return new TopArtistAdapter.ViewHolder(topAlbum);
    }

    /**
     * Binds the data into the xml file.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TopArtistAdapter.ViewHolder holder, int position) {
        Artist artist = topArtists.get(position);

        TextView topArtistNameTextView = holder.topArtistNameTextView;
        topArtistNameTextView.setText(artist.getName());

        ImageView topArtistImageView = holder.topArtistImageView;
        // calls Picasso library to process the imageUrl into ImageView
        Picasso.get().load(artist.getImageUrl()).into(topArtistImageView);

    }

    /**
     * setter for top artists
     * @param newArtists new list of top artist
     */
    public void setTopArtists(List<Artist> newArtists) {
        topArtists = newArtists;
    }


    /**
     * inner ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topArtistNameTextView;
        ImageView topArtistImageView;


        public ViewHolder(View itemView) {
            super (itemView);
            topArtistNameTextView = itemView.findViewById(R.id.artistName);
            topArtistImageView = itemView.findViewById(R.id.artistImage);
        }
    }




}
