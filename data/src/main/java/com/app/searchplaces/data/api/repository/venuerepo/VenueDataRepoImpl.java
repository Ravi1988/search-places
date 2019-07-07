package com.app.searchplaces.data.api.repository.venuerepo;

import com.app.searchplaces.data.api.Rx.AppRxSchedulers;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.venuemodels.Places;

import java.util.Map;

import io.reactivex.Observable;

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
                compose(new AppRxSchedulers().applySchedulers());
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


}
