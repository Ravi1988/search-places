package com.app.searchplaces.data.api.retrofit;

import com.app.searchplaces.data.models.places.Places;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * API client interface to provide API calls and response wrapped in Observable
 */
public interface MobileApi {

    /**
     * @param params query params
     * @param responseCache true to cache response
     * @return {@link Observable} of type {@link Places}
     */
    @GET("/v2/venues/search")
    Observable<Places> getVenues(@QueryMap Map<String, Object> params, @Header("ApplyResponseCache") boolean responseCache);

    /**
     * @param params query params
     * @param responseCache true to cache response
     * @return {@link Observable} of type {@link Places}
     */
    @GET("v2/venues/suggestcompletion")
    Observable<Places> getSearchSuggestion(@QueryMap Map<String, Object> params, @Header("ApplyResponseCache") boolean responseCache);
}


