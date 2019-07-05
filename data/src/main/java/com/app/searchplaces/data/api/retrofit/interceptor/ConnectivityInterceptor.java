package com.app.searchplaces.data.api.retrofit.interceptor;

import android.content.Context;

import com.app.searchplaces.data.R;
import com.app.searchplaces.data.api.retrofit.NoConnectivityException;
import com.app.searchplaces.util.ConnectivityUtils;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Intercept and provides proper error for no internet case
 */
public class ConnectivityInterceptor implements Interceptor {
 
    private Context mContext;
 
    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }
 
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
            throw new NoConnectivityException(mContext.getString(R.string.network_not_available));
        }
 
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
 
}