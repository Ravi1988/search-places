package com.app.searchplaces.di;

import com.app.searchplaces.MyApplication;
import com.app.searchplaces.util.AppPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides preference instance
 */
@Module
public class StorageModule {

    @Provides
    @Singleton
    public AppPreferences provideAppPreference(MyApplication application){
        return AppPreferences.getInstance(application);
    }
}
