package com.app.searchplaces.di;

import com.app.searchplaces.MyApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * App component extends {@link AndroidInjector} interface.
 * Auto injection is now supported in dagger for Android.
 */
@Component(modules =  {AndroidSupportInjectionModule.class, AppModule.class})
@Singleton
public interface AppComponent extends AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication> {}

}
