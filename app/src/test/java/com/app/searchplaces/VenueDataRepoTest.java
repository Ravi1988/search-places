package com.app.searchplaces;

import com.app.searchplaces.data.PlacesData;
import com.app.searchplaces.data.api.repository.places.VenueDataRepo;
import com.app.searchplaces.data.api.repository.places.VenueDataRepoImpl;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.models.places.Places;
import com.app.searchplaces.ui.venue.vm.VenueViewModel;
import com.app.searchplaces.util.APIParams;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
