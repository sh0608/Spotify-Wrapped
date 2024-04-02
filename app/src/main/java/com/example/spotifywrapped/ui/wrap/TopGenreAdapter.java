package com.example.spotifywrapped.ui.wrap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spotifywrapped.R;
import java.util.List;

public class TopGenreAdapter extends RecyclerView.Adapter<TopGenreAdapter.ViewHolder> {
    private List<String> topGenres;

    public TopGenreAdapter(List<String> topGenres) {
        this.topGenres = topGenres;
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     * @param position position to query
     * @return the view type of the item
     */
    public int getItemViewType (final int position) {
        return R.layout.item_top_genre;
    }

    public int getItemCount() {
        return topGenres.size();
    }

    /**
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return viewHolder of each topSong
     */
    @NonNull
    public TopGenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View topGenres = inflater.inflate(R.layout.item_top_genre, parent, false);
        return new TopGenreAdapter.ViewHolder(topGenres);
    }

    /**
     * Binds the data into the xml file.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(@NonNull TopGenreAdapter.ViewHolder holder, int position) {
        String genre = (position + 1) + ". " + topGenres.get(position);
        TextView topGenreNameTextView = holder.topGenreNameTextView;
        topGenreNameTextView.setText(genre);
    }

    /**
     * setter for top genres
     * @param newTopGenres new list of top genres
     */
    public void setTopGenres(List<String> newTopGenres) {
        topGenres = newTopGenres;
    }


    /**
     * inner ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topGenreNameTextView;

        public ViewHolder(View itemView) {
            super (itemView);
            topGenreNameTextView = itemView.findViewById(R.id.genre);
        }
    }

}
