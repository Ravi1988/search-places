package com.app.searchplaces.ui.venue;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.app.searchplaces.data.api.repository.places.VenueDataRepo;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.places.Venue;
import com.app.searchplaces.ui.venue.vm.VenueViewModel;

import java.util.Map;

/**
 * Responsible to pass data to paging adapter
 * Makes the API call via view model class
 */
public class VenueDataSource extends PageKeyedDataSource<Integer, Venue> {

    private final Map<String,Object> queryMap;
    private final VenueViewModel placesViewModel;

    /**
     *
     * @param queryString Query map for get request
     * @param placesViewModel View model of type {@link VenueViewModel} responsible to fetch data from n/w
     */
    public VenueDataSource( Map<String,Object> queryString, VenueViewModel placesViewModel) {
        this.queryMap = queryString;
        this.placesViewModel = placesViewModel;
    }

    @Override
    public void loadInitial(@NonNull final PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Venue> callback) {

        final int page = 1;
        placesViewModel.addDisposable(
                placesViewModel.initialSearchLoad(queryMap).
                subscribe(places -> {
                    if(places.getResponse() != null) {
                        callback.onResult(
                                places.getResponse().getVenues(),
                                0,
                                places.getResponse().getVenues().size(),
                                null,
                                page + 1
                        );
                    }
                }));

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Venue> callback) {
        // This is not necessary in our case as our data doesn't change. It's useful in cases where
        // the data changes and we need to fetch our list starting from the middle.
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Venue> callback) {
        //In current API pagination is not supported so commenting it
//        final int page = params.key;
//        queryMap.put("page",page+1);//Not supported in foresquare api
//        placesViewModel.addDisposable(
//                placesViewModel.afterSearchLoad(queryMap)
//                .subscribe(places -> {
//                    if(places.getResponse() != null) {
//                        callback.onResult(
//                                places.getResponse().getVenues(),
//                                page + 1);
//                    }
//                }));
    }

}