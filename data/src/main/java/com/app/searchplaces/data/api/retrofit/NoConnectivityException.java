package com.app.searchplaces.data.api.retrofit;

import java.io.IOException;

/**
 * Connectivity exception class to notify user with proper message
 * Used by {@class} {@link com.app.searchplaces.data.api.retrofit.interceptor.ConnectivityInterceptor} class
 */
public class NoConnectivityException extends IOException {

    private final String msg;

    public NoConnectivityException(String mContext) {
        this.msg = mContext;
    }

    @Override
    public String getMessage() {
        return msg;
    }
 
}