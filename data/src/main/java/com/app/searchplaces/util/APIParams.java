package com.app.searchplaces.util;

import com.app.searchplaces.data.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for holding API parameter's
 */
public class APIParams {
    public static Map<String,Object> getPlacesRequestParams(String query){
        //TODO: Need to save client id and secret key in secure preference
        //Encryption can be provided by Android keystore.
        HashMap<String , Object> map = new HashMap<>();
        map.put("client_id","BPCAR0CKOH320NHRY03C2OAHZGNYY4GZMKY1AF3GO33Z5FUO");
        map.put("client_secret","HQGDRNPOFBPAKUB2V5V1I02EJOR3CLWIJ4WZZEH1ZLNP1LTT");
        map.put("near","Seattle,+WA");
        map.put("query",query);
        map.put("v",20180401);
        map.put("limit",30);
        return map;
    }

    public static Map<String,Object> getSearchSuggestionParam(String query) {
        HashMap<String , Object> map = new HashMap<>();
        map.put("client_id","BPCAR0CKOH320NHRY03C2OAHZGNYY4GZMKY1AF3GO33Z5FUO");
        map.put("client_secret","HQGDRNPOFBPAKUB2V5V1I02EJOR3CLWIJ4WZZEH1ZLNP1LTT");
        map.put("near","Seattle,+WA");
        map.put("v",20180401);
        map.put("ll", BuildConfig.DEFAULT_LAT+","+BuildConfig.DEFAULT_LONG);
        map.put("query",query);
        map.put("limit",5);
        return map;
    }
}
