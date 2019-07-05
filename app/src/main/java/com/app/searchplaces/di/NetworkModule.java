package com.app.searchplaces.di;

import android.app.Application;

import com.app.searchplaces.data.api.repository.places.VenueDataRepo;
import com.app.searchplaces.data.api.repository.places.VenueDataRepoImpl;
import com.app.searchplaces.data.api.retrofit.MobileApi;
import com.app.searchplaces.data.api.retrofit.interceptor.CacheInterceptor;
import com.app.searchplaces.data.api.retrofit.interceptor.ConnectivityInterceptor;
import com.app.searchplaces.data.api.retrofit.interceptor.ResponseInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.searchplaces.data.BuildConfig.BASE_URL;


/**
 * Responsible for all network client providers.
 * <p>Builds the retrofit and okhttp client
 */
@Module
public class NetworkModule {

    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;
    private final static int CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    @Provides
    @Singleton
    MobileApi providesMobileAPI(CallAdapter.Factory callFactory,
                                Converter.Factory converterFactory,
                                OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(callFactory)
                .addConverterFactory(converterFactory);
        return builder.build().create(MobileApi.class);
    }

    @Provides
    @Singleton
    CallAdapter.Factory providesCallAdapter() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Converter.Factory providesConverterFactory() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Application context, Cache cache) {
        return new OkHttpClient.Builder() // share clients to have same cache and other resources
                .cache(cache)
                .addInterceptor(new ConnectivityInterceptor(context))// needed for no internet
                .addInterceptor(new CacheInterceptor())// needed to set cache expire time and enabling caching
                .addNetworkInterceptor(new ResponseInterceptor())// Needed for overriding cache-control
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)// retry on failure
                .build();
    }

    @Provides
    @Singleton
    Cache providesCache(Application context) {
        return new Cache(context.getCacheDir(), CACHE_SIZE);
    }


    /**
     * @return instance of type {@link VenueDataRepo}
     */
    @Provides
    @Singleton
    VenueDataRepo placesDataRepo() {
        return new VenueDataRepoImpl();
    }
}
