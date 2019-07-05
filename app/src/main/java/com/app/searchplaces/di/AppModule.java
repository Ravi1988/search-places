package com.app.searchplaces.di;

import android.app.Application;

import com.app.searchplaces.MyApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Provides the application context and includes other modules
 */
@Module(includes = {StorageModule.class, NetworkModule.class, ActivityModule.class, FragmentModule.class})
public abstract class AppModule {

    @Binds
    @Singleton
    abstract Application application(MyApplication app);
}
