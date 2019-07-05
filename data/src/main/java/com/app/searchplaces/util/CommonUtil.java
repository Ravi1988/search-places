package com.app.searchplaces.util;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import java.text.DecimalFormat;

public class CommonUtil {
    //Hides keyboard from window
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //Shows toast on UI
    public static void showToast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * Utility method to calculate distance between two locations.
     * @param latA
     * @param lngA
     * @param latB
     * @param lngB
     * @return float approximate distance in meter
     */
    public static float distanceBetween(Double latA, Double lngA, Double latB, Double lngB) {
        Location primaryLoc=new Location("locationA");
        primaryLoc.setLatitude(latA);
        primaryLoc.setLongitude(lngA);
        Location nearLoc=new Location("locationB");
        nearLoc.setLatitude(latB);
        nearLoc.setLongitude(lngB);

        return primaryLoc.distanceTo(nearLoc);
    }

    /**
     * @param value input value to be formatted
     * @return returns rounded value to 1 decimal point
     */
    public static String roundOf(float value){
        DecimalFormat df = new DecimalFormat("#.#");
        return  df.format(value);
    }

    /**
     * @param string input string
     * @return returns true if input is not null or empty.
     */
    public static boolean isNotNullOrEmpty(String string) {
        return string != null && !string.trim().isEmpty();
    }


}
