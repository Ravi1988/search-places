package com.app.searchplaces.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public class ConnectivityUtils {

    /**
     * param pContext
     * return true if network available else false.
     */
    public static boolean isNetworkEnabled(Context pContext) {
        NetworkInfo activeNetwork = getActiveNetwork(pContext);
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * param pContext
     * return
     */
    @SuppressLint("MissingPermission")
    public static NetworkInfo getActiveNetwork(Context pContext) {
        ConnectivityManager conMngr = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMngr == null ? null : conMngr.getActiveNetworkInfo();

    }


}