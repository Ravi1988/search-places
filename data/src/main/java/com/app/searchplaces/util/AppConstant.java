package com.app.searchplaces.util;

import android.app.SearchManager;
import android.provider.BaseColumns;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * All application constants defined here
 */
public class AppConstant {
    public static final String[] sAutocompleteColNames = new String[] {
            BaseColumns._ID,                         // necessary for adapter
            SearchManager.SUGGEST_COLUMN_TEXT_1      // the full search term
    };
    public static String KEY_SRC_LAT = "src_lat";
    public static String KEY_SRC_LNG = "src_lng";
    public static String KEY_DES_LAT = "des_lat";
    public static String KEY_DES_LNG = "des_lng";
    public static String KEY_SRC_TITLE = "srcTitle";
    public static String KEY_DES_TITLE = "desTitle";
    public static String KEY_FAV_IDS = "fav_ids_set";
    public static String KEY_POSITION = "position";

    public static HashSet<String> favMap;
}
