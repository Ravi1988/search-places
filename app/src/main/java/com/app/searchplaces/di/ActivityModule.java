package com.app.searchplaces.di;


import com.app.searchplaces.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Add all new activities here to enable injection
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivityInjector();


}
