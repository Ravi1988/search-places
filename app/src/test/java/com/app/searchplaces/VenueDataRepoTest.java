package com.app.searchplaces;

import com.app.searchplaces.data.PlacesData;
import com.app.searchplaces.data.api.repository.venuerepo.VenueDataRepoImpl;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.venuemodels.Places;
import com.app.searchplaces.ui.venue.vm.VenueViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This will execute test against repo layer
 * Validated if retrofit client gets called and returns the response
 * Error case handling will be done in view model test
 */
public class VenueDataRepoTest extends BaseTest{
    @Mock
    MobileApi mobileApi;

    @InjectMocks
    VenueDataRepoImpl venueDataRepo;

    @Mock
    VenueViewModel venueViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getPlaces(){
        mockSearchResponse(true);
        Observable<Places> response = venueDataRepo.getPlaces(mobileApi,anyMap(),anyBoolean());
        response.toList().blockingGet();
        verify(mobileApi, only()).getVenues(anyMap(),anyBoolean());
    }

    @Test
    public void test_searchSuggestion(){
        mockSuggetionResponse(true);
        Observable<Places> response = venueDataRepo.searchSuggestion(mobileApi,anyMap(),anyBoolean());
        response.toList().blockingGet();
        verify(mobileApi, only()).getSearchSuggestion(anyMap(),anyBoolean());
    }

    private void mockSuggetionResponse(boolean success) {
        when(mobileApi.getSearchSuggestion(anyMap(),anyBoolean()))
                .thenReturn(Observable.just(PlacesData.mockSearchAPIResponse(success)));
    }

    private void mockSearchResponse(boolean success) {
        when(mobileApi.getVenues(anyMap(),anyBoolean()))
                .thenReturn(Observable.just(PlacesData.mockSearchAPIResponse(success)));
    }

}
