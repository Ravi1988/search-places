package com.app.searchplaces.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Singleton preference class
 * Shared between all the application components
 */
public class AppPreferences {
    private static final String SETTINGS_NAME = "app_search_shared_preferences";
    private static AppPreferences sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;


    /**
     * private constructor to avoid multiple instances
     * @param context Need application context to create/open private preference file
     */
    private AppPreferences(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    /**
     *
     * @param context Need application context to create/open private preference file
     * @return Returns the shred reference instance
     */
    public static AppPreferences getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new AppPreferences(context);
        }
        return sSharedPrefs;
    }


    @SuppressLint("CommitPrefEdits")
    private void doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    /**
     * @param key value key to be set in preference
     * @param setValue value to be saved in preference
     */
    public void putStringSet(String key, Set<String> setValue) {
        doEdit();
        mEditor.putStringSet(key, setValue);
        doCommit();
    }

    /**
     * @param key key against the saved value
     * @param defValue default value
     * @return returns the value against the key if present else default value
     */
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return mPref.getStringSet(key, defValue);
    }


}