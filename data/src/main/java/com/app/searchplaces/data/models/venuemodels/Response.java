
package com.app.searchplaces.data.models.venuemodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName(value = "venues",alternate = {"minivenues"})
    @Expose
    private List<Venue> venues = null;

    public List<Venue> getVenues() {
        return venues;
    }
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
