package com.app.searchplaces.data;

import com.app.searchplaces.data.models.places.Category;
import com.app.searchplaces.data.models.places.Icon;
import com.app.searchplaces.data.models.places.Meta;
import com.app.searchplaces.data.models.places.Places;
import com.app.searchplaces.data.models.places.Response;
import com.app.searchplaces.data.models.places.Venue;

import java.util.Arrays;
import java.util.List;

public class PlacesData {

    public static Places mockSearchAPIResponse(boolean success){
        Places places = new Places();
        places.setResponse(mockResponse());
        places.setMeta(mockMeta(success));
        return places;
    }

    private static Meta mockMeta(boolean success) {
        Meta meta = new Meta();
        if(success) {
            meta.setCode(200);
        }else{
            meta.setCode(500);
            meta.setErrorDetail("Api error");
            meta.setErrorType("Api error type");
        }
        meta.setRequestId("12345");

        return null;
    }

    private static Response mockResponse() {
        Response response = new Response();
        response.setVenues(mockVenues());
        return response;
    }

    private static List<Venue> mockVenues() {
        return Arrays.asList(mockVenue(),mockVenue());

    }

    private static Venue mockVenue() {
        Venue venue = new Venue();
        venue.setMarkedFav(false);
        venue.setName("Rogger Coffee");
        venue.setId("123");
        venue.setCategories(mockCatList());
        return venue;
    }

    private static List<Category> mockCatList() {

        return Arrays.asList(mockCat(),mockCat());
    }

    private static Category mockCat() {
        Category category = new Category();
        category.setIcon(mockIcon());
        category.setId("1234");
        category.setName("");
        return category;
    }

    private static Icon mockIcon() {
        Icon icon = new Icon();
        icon.setPrefix("https://ss3.4sqi.net/img/categories_v2/arts_entertainment/default_");
        icon.setSuffix(".png");
        return icon;
    }
}
