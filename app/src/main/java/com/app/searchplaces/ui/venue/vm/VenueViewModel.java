package com.app.searchplaces.ui.venue.vm;


import android.database.MatrixCursor;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.app.searchplaces.data.BuildConfig;
import com.app.searchplaces.data.api.repository.places.VenueDataRepo;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.places.Places;
import com.app.searchplaces.data.models.places.Venue;
import com.app.searchplaces.ui.base.BaseViewModel;
import com.app.searchplaces.ui.venue.VenueDataSource;
import com.app.searchplaces.util.APIParams;
import com.app.searchplaces.util.CommonUtil;
import com.app.searchplaces.util.MainThreadExecutor;

import java.net.HttpRetryException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static com.app.searchplaces.util.AppConstant.favMap;
import static com.app.searchplaces.util.AppConstant.sAutocompleteColNames;

/**
 * Responsible to fetch data from {@link MobileApi} service client.
 * <p>Processes the data and provides the model to attached view/class.
 * <p>All core business logic goes here.
 *
 * <p>Depends on {@link VenueDataRepo} and {@link MobileApi}
 */
public class VenueViewModel extends BaseViewModel {
    private final MutableLiveData<PagedList<Venue>> venueList = new MutableLiveData<>();
    private final MutableLiveData<List<Venue>> searchSuggestionList = new MutableLiveData<>();
    private final BehaviorSubject<String> userInputSubject = BehaviorSubject.create();

    private final VenueDataRepo venueDataRepo;
    private final MobileApi mobileApi;
    private MainThreadExecutor executor;

    /**
     * @param venueDataRepo Venue repository of type {@link VenueDataRepo}
     * @param mobileApi Retrofit API interface of type {@link MobileApi}
     */
    @Inject
    public VenueViewModel(VenueDataRepo venueDataRepo, MobileApi mobileApi){
         executor = new MainThreadExecutor();
        this.venueDataRepo = venueDataRepo;
        this.mobileApi = mobileApi;
    }


    /**
     * Creates a paging list and sets to live data.
     * So whenever this list changes view class will
     * @param dataSource source
     */
    public void searchForVenues(VenueDataSource dataSource){
        // Build PagedList
        PagedList<Venue> list =
                new PagedList.Builder<>(dataSource, getConfig()) // Can pass `pageSize` directly instead of `config`
                        // Do fetch operations on the main thread. We'll instead be using Retrofit's
                        .setFetchExecutor(executor)
                        // Send updates on the main thread
                        .setNotifyExecutor(executor)
                        .build();
        venueList.setValue(list);
    }

    /**
     * Loads the first set of venues, currently pagination is not supported in foursquare API so
     * initial load is the only API call with limit of 30 results.
     * @param queryMap query map
     * @return {@link Observable} of type {@link Places}
     */
    public Observable<Places> initialSearchLoad(Map<String,Object> queryMap){
        showLoader();
        return loadSearch(queryMap);
    }

    /**
     * API call to fetch venues data
     * @param queryMap query map
     * @return {@link Observable} of type {@link Places}
     */
    private Observable<Places> loadSearch(Map<String,Object> queryMap) {
        return venueDataRepo.getPlaces(mobileApi, queryMap, true).
                doOnError(this::dispatchOnFailure). //Show error snackbar
                retryWhen(this::getRetrySubject)//Retry logic to call api again
                .filter(res -> {
                    hideLoader();
                    return res != null;// filter out null response
                })
                .map(res -> {
                    //check for valid response
                    if (res.getMeta() != null && res.getMeta().getCode() != 200) {
                        throw new HttpRetryException(res.getMeta().getErrorDetail(), res.getMeta().getCode());
                    }
                    //check if venue list is empty
                    if(res.getResponse() == null || res.getResponse().getVenues() == null
                            || res.getResponse().getVenues().isEmpty()){
                        showInfoMsg("No venues found");// if empty show user information msg
                    }
                    return res;// return valid response.
                }).map(res  ->{
                    for(Venue  venue : res.getResponse().getVenues()){
                                    setIfMarkedFav(venue);
                                    setDistance(venue);
                                }
                    return res;
                });
    }

    /**
     * @return {@link PagedList.Config}
     */
    private PagedList.Config getConfig() {
        // Configure paging
        return new PagedList.Config.Builder()
                // Number of items to fetch at once. [Required]
                .setPageSize(VenueDataRepo.DEFAULT_PER_PAGE)
                // Number of items to fetch on initial load.
                // Should be greater than Page size. [Optional]
                .setInitialLoadSizeHint(VenueDataRepo.DEFAULT_PER_PAGE * 2)
                // Show empty views until data is available
                .setEnablePlaceholders(true)
                .build();
    }

    /**
     * @return {@link MutableLiveData} of type {@link PagedList<Venue>}
     */
    public MutableLiveData<PagedList<Venue>> getVenueList() {
        return venueList;
    }

    /**
     * Handler that is notified when the user changes input
     * @param query query to be processed
     */
    public void onQueryChange(String query) {
        //submits the query to subject
        userInputSubject.onNext(query);
    }


    /**
     * Creates a subject for auto complete search.
     * Hits the API every 300 milliseconds when called.
     * Initiate search after query text length exceeds limit of 3 char.
     */
    public void configureAutoSearch() {
        // Subject holding the most recent user input
        if(!userInputSubject.hasObservers()) {
            addDisposable(userInputSubject
                    //To avoid frequent API calls
                    .debounce(300, TimeUnit.MILLISECONDS)
                    // filter null or empty response
                    .filter(CommonUtil::isNotNullOrEmpty)
                    // Foursquare api supports 3+ char search in search suggetion API
                    .filter(changes -> changes.toCharArray().length >= 3)
                    //switchMap to persist the all valid inputs
                    .switchMap(input -> venueDataRepo.searchSuggestion(mobileApi,
                            APIParams.getSearchSuggestionParam(input), true))
                    .doOnError(this::dispatchOnFailure)
                    .retryWhen(this::getRetrySubject)
                    //pass only valid response to next step
                    .filter(res ->{
                            hideLoader();
                           return res != null && res.getResponse() != null
                            && res.getResponse().getVenues() != null;
                    })
                    .map(res -> res.getResponse().getVenues())// return venue list
                    .subscribe(searchSuggestionList::setValue,this::dispatchOnFailure));
        }
    }

    /**
     * @return {@link MutableLiveData} of type {@link List<Venue>}
     */
    public MutableLiveData<List<Venue>> getSearchSuggestion() {
        return searchSuggestionList;
    }

    /**
     * Converts venue list to {@link MatrixCursor}.
     * This is needed for autocomplete adapter
     * @param venues input {@link List<Venue>}
     * @return cursor of type {@link MatrixCursor}
     */
    public MatrixCursor getCursor(List<Venue> venues) {
        MatrixCursor cursor = new MatrixCursor(sAutocompleteColNames);
        // parse your search terms into the MatrixCursor
        for (int index = 0; index < venues.size(); index++) {
            String term = venues.get(index).getName();

            Object[] row = new Object[] { index, term };
            cursor.addRow(row);
        }
        return cursor;
    }

    /**
     * calculate and sets the distance of venue from user specified location
     * @param venue venue object of {@class} {@link Venue}
     */
    private void setDistance(Venue venue) {
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
    private void setIfMarkedFav(Venue venue) {
        if(favMap != null && favMap.contains(venue.getId())){
            venue.setMarkedFav(true);
        }
    }
//Uncomment for paginated API
//    public Observable<Places> afterSearchLoad(Map<String, Object> map) {
//        return loadSearch(placesDataRepo,mobileApi,map);
//    }
}
