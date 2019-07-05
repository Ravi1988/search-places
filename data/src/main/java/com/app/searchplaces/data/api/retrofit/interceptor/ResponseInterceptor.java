package com.app.searchplaces.data.api.retrofit.interceptor;


import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor to cache data and maintain it specified time.
 * <p>
 * If the same network request is sent within specified time,
 * the response is retrieved from cache.
 */
public class ResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response originalResponse = chain.proceed(chain.request());
        // add cache expire time if header has ApplyResponseCache value to true
        // 3 hours cache is added since app has no user auth. Foursquare tnc applies.
        if (Boolean.valueOf(request.header("ApplyResponseCache"))) {
            return originalResponse.newBuilder()
                    .removeHeader("ApplyResponseCache")
                    .header("Pragma", String.format("max-age=%d, only-if-cached, max-stale=%d", 10800, 0))//3 hours caching
                    .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 10800, 0))//3 hours caching
                    .build();
        }

        return originalResponse;
    }
}