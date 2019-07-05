package com.app.searchplaces.ui.venue.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.searchplaces.R;
import com.app.searchplaces.data.models.places.Venue;
import com.app.searchplaces.ui.base.BaseViewHolder;

import javax.inject.Inject;

/**
 * Responsible for inflating venu {@link Venue} items in {@link RecyclerView}
 * Uses a {@link PagedListAdapter} for infinite pagination
 */
public class VenueAdapter extends PagedListAdapter<Venue, BaseViewHolder> {

    @Inject
     public VenueAdapter() {
         super(DIFF_CALLBACK);
     }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseViewHolder.create(parent, R.layout.venue_item_view);
    }

    @Override
     public void onBindViewHolder(BaseViewHolder holder, int position) {
         Venue venue = getItem(position);
         holder.bindTo(venue);// bind to view
     }


    /**
     * This diff callback is needed to compare two objects list to detect any differences and update the
     * older list data.
     */
    private static final DiffUtil.ItemCallback<Venue> DIFF_CALLBACK = new DiffUtil.ItemCallback<Venue>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull Venue oldVenue, @NonNull Venue newVenue) {
            // Venue properties may have changed if reloaded from server, but ID is fixed
            return oldVenue.getId().equals(newVenue.getId());
        }
        @Override
        public boolean areContentsTheSame(
                @NonNull Venue oldVenue, @NonNull Venue newVenue) {
            //Check if content is same or not
            return oldVenue.getName().equals(newVenue.getName()) &&
                    oldVenue.isMarkedFav() == newVenue.isMarkedFav();
        }
    };



}