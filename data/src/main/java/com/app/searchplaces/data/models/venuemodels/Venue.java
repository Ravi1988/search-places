
package com.app.searchplaces.data.models.venuemodels;

import java.util.List;
import com.google.gson.annotations.Expose;

public class Venue {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private Location location;
    @Expose
    private List<Category> categories = null;
    //Client variable - used to display distance
    private String distance;
    //Client variable - used to maintain favourite
    private boolean isMarkedFav;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean isMarkedFav() {
        return isMarkedFav;
    }

    public void setMarkedFav(boolean markedFav) {
        isMarkedFav = markedFav;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
