package com.app.searchplaces.data.api.repository.places;

import com.app.searchplaces.data.BuildConfig;
import com.app.searchplaces.data.api.Rx.AppRxSchedulers;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.places.Places;
import com.app.searchplaces.data.models.places.Venue;
import com.app.searchplaces.util.CommonUtil;

import java.util.Map;

import io.reactivex.Observable;

import static com.app.searchplaces.util.AppConstant.favMap;

/**
 * Responsible to fetch and alter venue data from server
 */
public class VenueDataRepoImpl implements VenueDataRepo {
    /**
     * @param mobileApi   : service client
     * @param query       : corresponding to which data is required
     * @param shouldCache : set to true if caching required
     * @return {@link Observable} of type {@link Places}
     */
    @Override
    public Observable<Places> getPlaces(MobileApi mobileApi,Map<String, Object> query, boolean shouldCache) {
        return mobileApi.getVenues(query,shouldCache).
                compose(new AppRxSchedulers().applySchedulers())
                .map(res -> {
                    for(Venue  venue : res.getResponse().getVenues()){
                        checkIfMarkedFav(venue);
                        calculateDistance(venue);
                    }
                    return res;
        });
    }

    /**
     * @param mobileApi   : service client
     * @param query       : corresponding to which data is required
     * @param shouldCache : set to true if caching required
     * @return {@link Observable} of type {@link Places}
     */
    @Override
    public Observable<Places> searchSuggestion(MobileApi mobileApi, Map<String, Object> query, boolean shouldCache) {
        return mobileApi.getSearchSuggestion(query,shouldCache).compose(new AppRxSchedulers().applySchedulers());
    }

    /**
     * calculate and sets the distance of venue from user specified location
     * @param venue venue object of {@class} {@link Venue}
     */
    private void calculateDistance(Venue venue) {
        if(venue.getLocation() != null) {
            float distance = CommonUtil.distanceBetween(BuildConfig.DEFAULT_LAT, BuildConfig.DEFAULT_LONG,
                    venue.getLocation().getLat(), venue.getLocation().getLng());
            if(distance > 100) {// if distance is far convert to kilometer
                distance =  distance / 1000;
                venue.setDistance(CommonUtil.roundOf(distance) +" km");
            }else{//else set in meter
                venue.setDistance(CommonUtil.roundOf(distance) +" m");
            }

        }
    }

    /**
     * Syncs local data with API data.
     * checks if user has marked any venue as favourite
     * and update the API response
     * @param venue venue object of {@class} {@link Venue}
     */
    private void checkIfMarkedFav(Venue venue) {
        if(favMap != null && favMap.contains(venue.getId())){
            venue.setMarkedFav(true);
        }
    }
}
