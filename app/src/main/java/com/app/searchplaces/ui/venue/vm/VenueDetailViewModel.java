package com.app.searchplaces.ui.venue.vm;

import androidx.lifecycle.MutableLiveData;

import com.app.searchplaces.data.models.places.Venue;
import com.app.searchplaces.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Responsible for holding and updating {@link Venue} data
 */
public class VenueDetailViewModel extends BaseViewModel {
    private MutableLiveData<Venue> liveDataVenue = new MutableLiveData<>();
   @Inject
    public VenueDetailViewModel(){
    }

    public MutableLiveData<Venue> getLiveDataVenue() {
        return liveDataVenue;
    }

    public void setLiveDataVenue(Venue venue) {
        this.liveDataVenue.setValue(venue);
    }
}
