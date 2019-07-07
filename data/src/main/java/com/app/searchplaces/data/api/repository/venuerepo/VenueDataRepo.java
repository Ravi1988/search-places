package com.app.searchplaces.data.api.repository.venuerepo;

import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.venuemodels.Places;

import java.util.Map;

import io.reactivex.Observable;

/**
 * VenueDataRepo interface returns with data related to the appliance details
 */
public interface VenueDataRepo {

    // Default page size to be requested
    int DEFAULT_PER_PAGE = 30;
    /**
     * Network call for fetching venue list
     *
     *@param mobileApi : service client
     * @param query : corresponding to which data is required
     * @param shouldCache : set to true if caching required
     * @return {@link Observable} of type {@link Places}
     */
    Observable<Places> getPlaces(MobileApi mobileApi, Map<String, Object> query, boolean shouldCache);

    /**
     * Network call for fetching venue suggestion list
     *
     *@param mobileApi : service client
     * @param query : corresponding to which data is required
     * @param shouldCache : set to true if caching required
     * @return {@link Observable} of type {@link Places}
     */
    Observable<Places> searchSuggestion(MobileApi mobileApi, Map<String, Object> query, boolean shouldCache);
}


