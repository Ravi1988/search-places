package com.app.searchplaces.di;

import com.app.searchplaces.ui.map.MapViewFragment;
import com.app.searchplaces.ui.venue.VenueDetailFragment;
import com.app.searchplaces.ui.venue.SearchVenueFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Add new fragments here to support injection
 */
@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract SearchVenueFragment bindSearchPlaceFragment();
    @ContributesAndroidInjector
    abstract VenueDetailFragment bindPlaceDetail();
    @ContributesAndroidInjector
    abstract MapViewFragment bindMapViewFragment();
}