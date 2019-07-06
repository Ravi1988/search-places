package com.app.searchplaces;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.validateMockitoUsage;

/**
 */

public class BaseTest {

    @BeforeClass
    public static void rxSetup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.computation());
    }

    @AfterClass
    public static void rxTearDown() {
        RxAndroidPlugins.reset();
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
